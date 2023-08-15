package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.common.Consts;
import com.vnpt.vn.vsr.mongo.common.CummulativeCode;
import com.vnpt.vn.vsr.mongo.common.Util;
import com.vnpt.vn.vsr.mongo.model.impl.*;
import com.vnpt.vn.vsr.mongo.model.impl.Object;
import com.vnpt.vn.vsr.mongo.model.payload.response.Response;
import com.vnpt.vn.vsr.mongo.output.Packet;
import com.vnpt.vn.vsr.mongo.repository.*;
import com.vnpt.vn.vsr.mongo.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class OrDataRowServiceImpl extends AbstractBaseServiceImpl<OrDataRow, String> {
    @Autowired
    private OrDataRowRepository orDataRowRepository;
    private final OrganizationRepository organizationRepository;
    private final AttributeRepository attributeRepository;
    private final IndicatorRepository indicatorRepository;
    private final ObjectRepository objectRepository;
    private final OrIndGrantRepository orIndGrantRepository;
    private final MongoTemplateService mongoTemplateService;
    private final SequenceGeneratorService generateSequence;

    public OrDataRowServiceImpl(AbstractBaseRepository<OrDataRow, String> abstractBaseRepository,
                                OrganizationRepository organizationRepository,
                                AttributeRepository attributeRepository,
                                IndicatorRepository indicatorRepository,
                                ObjectRepository objectRepository,
                                OrIndGrantRepository orIndGrantRepository,
                                OrIndOrgTypeRepository orIndOrgTypeRepository,
                                MongoTemplateService mongoTemplateService,
                                SequenceGeneratorService generateSequence) {
        super(abstractBaseRepository);
        this.organizationRepository = organizationRepository;
        this.attributeRepository = attributeRepository;
        this.indicatorRepository = indicatorRepository;
        this.objectRepository = objectRepository;
        this.orIndGrantRepository = orIndGrantRepository;
        this.mongoTemplateService = mongoTemplateService;
        this.generateSequence = generateSequence;
    }


    public Response findDataRow(String objId,String tenantId,String orgId){
    return new Response(0, Consts.MESSAGE_OK, orDataRowRepository.findByObjIdAndTenantIdAndOrgIdOrderByDataId(Long.parseLong(objId), Long.parseLong(tenantId),Long.parseLong(orgId)));

}

public Response createDataRow(List<OrDataRow> orDataRows){
    for(OrDataRow ordata :orDataRows){
        if(ordata !=null){
            ordata.setDataId(generateSequence.generateSequence(OrDataRow.SEQUENCE_NAME));
        }
    }
    return new Response(0, Consts.MESSAGE_OK, orDataRowRepository.saveAll(orDataRows));
}

public Response insertDataRowInput(String objId,String tenantId,String orgId,String timeId){
    List<OrDataRow> ordataRow =  new ArrayList<>();
    //lấy dánh sách chỉ tiêu
    List<OrAttribute> attr = attributeRepository.findAllByObjIdAndTenantIdOrderByAttrId(objId, tenantId);
    List<Indicator> indicatorList= indicatorRepository.findByObjIdAndTenantIdOrderByIndId(Long.parseLong(objId), Long.parseLong(tenantId));
    List<OrDataRow> orDataRowList = new ArrayList<>();
    Map<Integer, String> mapIndType = new HashMap<Integer, String>();
    mapIndType.put(1, "");
    mapIndType.put(2, "");
    mapIndType.put(3, "sum");
    mapIndType.put(4, "avg");
    mapIndType.put(5, "max");
    mapIndType.put(6, "min");

    Map<Integer, String> mapIndGroup = new HashMap<Integer, String>();
    mapIndGroup.put(1, "1");
    mapIndGroup.put(2, "");
    mapIndGroup.put(3, "3");
    mapIndGroup.put(4, "3");
    mapIndGroup.put(5, "3");
    mapIndGroup.put(6, "3");


    for(Indicator ind: indicatorList){
        OrDataRow orDataRow = new OrDataRow();
        orDataRow.setOrgId(orgId);
        orDataRow.setObjId(objId);
        orDataRow.setTimeId(timeId);
        orDataRow.setIndId((ind.getIndId()+""));
        orDataRow.setFieldId(ind.getFieldId()+"");
        orDataRow.setIndUnit(ind.getIndUnit());
        orDataRow.setGroupId(mapIndGroup.get(ind.getIndType()));
        orDataRow.setCommand(mapIndType.get(ind.getIndType()));
        orDataRow.setIdx(ind.getIdx()+"");
        orDataRow.setIsSumary(ind.getIsSumary()+"");
        orDataRow.setParentFieldId(ind.getParentFieldId()+"");
        orDataRow.setIndIndex(ind.getIndIndex());
        orDataRow.setParentIndId(ind.getParentId()+"");
        orDataRow.setIndType(ind.getIndType()+"");

       // orDataRow.setIsSubInd(ind.gets); thêm sau
        orDataRow.setDataId(generateSequence.generateSequence(OrDataRow.SEQUENCE_NAME));
        orDataRowList.add(orDataRow);
    }
    List<OrDataRow> orDataRows = orDataRowRepository.saveAll(orDataRowList);
//    JSONArray arr_attr_rt = new JSONArray();
    for (int i = 0; i < attr.size(); i++) {
        OrAttribute obj = attr.get(i);
        String formula = obj.getFormula();
        String formual_basic = "";
        if (formula !=null && !formula.isEmpty()) {
            formual_basic = InputCal.convertFormulaAttr(formula, attr);
        }
        obj.setFormulaBasic(formual_basic.toUpperCase());
    }

    return new Response(0, Consts.MESSAGE_OK, orDataRowRepository.saveAll(ordataRow));
}

    public Response getData(String tenantId, String objId, String i_attrId, String i_indId, String orgId, String timeId, int submitType) throws Exception {
        Organization organization = organizationRepository.findOrganizationByOrgIdAndTenantId(Long.parseLong(orgId), tenantId);
        Object object = objectRepository.findObjectByObjId(Long.parseLong(objId));
        if (object == null) {
            return new Response(0, Consts.ID_NOT_AVAILABLE, null);
        }
        int flag = object.getNullTo0();
        String orgType = organization.getOrgType();

        List<Indicator> indicators = mongoTemplateService.findOrIndGrants(objId, tenantId, Long.parseLong(orgId), timeId);

        createDataRowIfNull(indicators, orgId, timeId, objId, tenantId);

        List<OrAttribute> orAttributes = attributeRepository.findAttributesHasFormula(objId);
        List<OrAttribute> allAttrs = attributeRepository.findAllByObjIdAndTenantIdOrderByAttrId(objId, tenantId);
        List<OrDataRow> orDataRowsUpdate;
        for (OrAttribute attribute : orAttributes) {
            String formula = attribute.getFormula();
            String formulaBasic = "";
            if (formula != null && !formula.isEmpty()) {
                formulaBasic = InputCal.getFormulaChild(formula, allAttrs);
            }
            String fld_code = attribute.getFldCode();
            CummulativeCode cummulativeCode = CummulativeCode.getValue(formulaBasic);
            int updateOption = 0;
            if (!cummulativeCode.equals(CummulativeCode.ANY)) {
                if (cummulativeCode.equals(CummulativeCode.INC_M) ||
                        cummulativeCode.equals(CummulativeCode.INC_Q) ||
                        cummulativeCode.equals(CummulativeCode.INC_CQ) ||
                        cummulativeCode.equals(CummulativeCode.MN_INC) ||
                        cummulativeCode.equals(CummulativeCode.INCMN_PRE) ||
                        cummulativeCode.equals(CummulativeCode.INC_CD) ||
                        cummulativeCode.equals(CummulativeCode.INC_D)) {
                    updateOption = 1;
                } else {
                    updateOption = 2;
                }
                orDataRowsUpdate = getOrDataRowListUpdate(orgId, objId, timeId, tenantId, updateOption);
                updateFLD_CODE(orDataRowsUpdate, flag, fld_code, formulaBasic, objId, orgId, timeId, submitType, null);
            }
        }

        List<Indicator> indicatorsA = getListIndicatorA(true, objId, orgId, timeId, orgType, tenantId, i_indId);
        List<OrDataRow> orDataRowsB = getListOrDataRowB(false, orgId, timeId, tenantId);
        List<Indicator> indicatorsC = getListIndicatorC(false, objId, tenantId);
        Packet result = getResult(objId, tenantId, i_attrId, indicatorsA, orDataRowsB, indicatorsC);
        return new Response(0, Consts.MESSAGE_OK, result.getData());
    }

    public Response getDataAndPreTime(String tenantId, String objId, String i_attrId, String i_indId, String orgId, String timeId, int submitType, String timeIdPre) throws Exception {
        Organization organization = organizationRepository.findOrganizationByOrgIdAndTenantId(Long.parseLong(orgId), tenantId);
        Object object = objectRepository.findObjectByObjId(Long.parseLong(objId));
        if (object == null) {
            return new Response(0, Consts.ID_NOT_AVAILABLE, null);
        }
        int flag = object.getNullTo0();
        String orgType = organization.getOrgType();

        // lấy danh sách indicators
        List<Indicator> indicators = mongoTemplateService.findOrIndGrants(objId, tenantId, Long.parseLong(orgId), timeId);

        // tạo mới bản ghi nếu null
        createDataRowIfNull(indicators, orgId, timeId, objId, tenantId);

        // lấy danh sách attribute có formula, cập nhập formula child vào biến formulaBasic
        List<OrAttribute> orAttributes = attributeRepository.findAttributesHasFormula(objId);
        List<OrAttribute> allAttrs = attributeRepository.findAllByObjIdAndTenantIdOrderByAttrId(objId, tenantId);
        List<OrDataRow> orDataRowsUpdate;
        for (OrAttribute attribute : orAttributes) {
            String formula = attribute.getFormula();
            String formulaBasic = "";
            if (formula != null && !formula.isEmpty()) {
                formulaBasic = InputCal.getFormulaChild(formula, allAttrs);
            }
            String fld_code = attribute.getFldCode();
            CummulativeCode cummulativeCode = CummulativeCode.getValue(formulaBasic);
            int updateOption = 0;
            if (!cummulativeCode.equals(CummulativeCode.ANY)) {
                if (cummulativeCode.equals(CummulativeCode.MPRE) ||
                        cummulativeCode.equals(CummulativeCode.INC_M) ||
                        cummulativeCode.equals(CummulativeCode.INC_Q) ||
                        cummulativeCode.equals(CummulativeCode.INC_CQ) ||
                        cummulativeCode.equals(CummulativeCode.MN_INC) ||
                        cummulativeCode.equals(CummulativeCode.INCMN_PRE) ||
                        cummulativeCode.equals(CummulativeCode.INC_CD) ||
                        cummulativeCode.equals(CummulativeCode.INC_D) ||
                        cummulativeCode.equals(CummulativeCode.EXTM) ||
                        cummulativeCode.equals(CummulativeCode.EXM)) {
                    updateOption = 1;
                } else {
                    updateOption = 2;
                }
                orDataRowsUpdate = getOrDataRowListUpdate(orgId, objId, timeId, tenantId, updateOption);
                updateFLD_CODE(orDataRowsUpdate, flag, fld_code, formulaBasic, objId, orgId, timeId, submitType, timeIdPre);
            }
        }

        boolean getByTenantId = true;
        if (tenantId.equals("10") && (Long.parseLong(orgType) >=2 && Long.parseLong(orgType) <= 4)) {
            getByTenantId = false;
        }
        List<Indicator> indicatorsA = getListIndicatorA(getByTenantId, objId, orgId, timeId, orgType, tenantId, i_indId);
        List<OrDataRow> orDataRowsB = getListOrDataRowB(getByTenantId, orgId, timeId, tenantId);
        List<Indicator> indicatorsC = getListIndicatorC(getByTenantId, objId, tenantId);
        Packet result = getResult(objId, tenantId, i_attrId, indicatorsA, orDataRowsB, indicatorsC);
        return new Response(0, Consts.MESSAGE_OK, result.getData());
    }

    private void createDataRowIfNull(List<Indicator> indicators, String orgId, String timeId, String objId, String tenantId) {
        for (Indicator indicator : indicators) {
            String indId = String.valueOf(indicator.getIndId());
            String fieldId = String.valueOf(indicator.getFieldId());
            // kiểm tra data có dữ liệu chưa, nếu chưa thêm mới 1 bản ghi
            OrDataRow orDataRow = orDataRowRepository.findByOrgIdAndTimeIdAndIndId(orgId, timeId, indId);
            if (orDataRow == null) {
                orDataRow = new OrDataRow();
                orDataRow.setDataId(generateSequence.generateSequence(OrDataRow.SEQUENCE_NAME));
                orDataRow.setOrgId(orgId);
                orDataRow.setTimeId(timeId);
                orDataRow.setIndId(indId);
                orDataRow.setObjId(objId);
                orDataRow.setTenantId(tenantId);
                orDataRow.setFieldId(fieldId);

                orDataRowRepository.save(orDataRow);
            }
        }
    }

    public List<OrDataRow> getOrDataRowListUpdate(String orgId, String objId, String timeId, String tenantId, int option) {
        List<OrDataRow> orDataRowListUpdateOp1 = new ArrayList<>();
        List<OrDataRow> orDataRowListUpdateOp2 = orDataRowRepository.findByOrgIdAndObjIdAndTimeId(orgId, objId, timeId);
        List<Indicator> indicators1 = indicatorRepository.findByTenantIdAndObjIdAndAttrFormulaForbit(tenantId, objId);
        Set<Long> indicatorIds = indicators1.stream().map(Indicator::getIndId).collect(Collectors.toSet());
        for (OrDataRow orDataRow : orDataRowListUpdateOp2) {
            if (!indicatorIds.contains(Long.parseLong(orDataRow.getIndId()))) {
                orDataRowListUpdateOp1.add(orDataRow);
            }
        }
        if (option == 1) return orDataRowListUpdateOp1;
        if (option == 2) return orDataRowListUpdateOp2;
        return null;
    }

    private void updateFLD_CODE(List<OrDataRow> orDataRowListUpdate, int flag, String fld_code, String r_form, String objId, String orgId, String timeId, int submitType, String timeIdPre) throws Exception {
        for (OrDataRow orDataRow : orDataRowListUpdate) {
            try {
                String fld_code_pre = getFLD_CODE_PRE(flag, orDataRow.getIndId(), r_form, objId, orgId, timeId, submitType, timeIdPre);
                orDataRow.setField(fld_code, fld_code_pre);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        saveAll(orDataRowListUpdate);
    }

    private String getFLD_CODE_PRE(int flag, String ind_id, String r_form, String objId, String orgId, String timeId,
                                   int submitType, String timeIdPre) throws Exception {
        String attrCode, fld_code_pre, subTimeId, r_sql;
        CummulativeCode cummulativeCode;
        long sum;
        List<OrDataRow> orDataRowQuery;
        while (true) {
            Matcher matcher = Pattern.compile("\\{(.*?)\\}").matcher(r_form);
            if (matcher.find()) {
                sum = 0;
                String match = matcher.group(0);
                String r_tmp = match.replace("{", "").replace("}", "");
                cummulativeCode = CummulativeCode.getValue(r_tmp);
                if (r_tmp == null || r_tmp.isEmpty()) {
                    break;
                }
                attrCode = r_tmp.substring(r_tmp.indexOf("#") + 1);
                OrAttribute orAttribute = attributeRepository.findByObjIdAndAttrCode(objId, attrCode);
                fld_code_pre = orAttribute.getFldCode() == null ? "" : orAttribute.getFldCode();

                if (cummulativeCode.equals(CummulativeCode.MN_INC) ||
                        cummulativeCode.equals(CummulativeCode.INCMN_PRE) ||
                        cummulativeCode.equals(CummulativeCode.INC_M) ||
                        cummulativeCode.equals(CummulativeCode.INC_Q) ||
                        cummulativeCode.equals(CummulativeCode.INC_D) ||
                        cummulativeCode.equals(CummulativeCode.INC_CD)) {
                    subTimeId = Util.substrSQL(timeId, 0 ,4);
                    orDataRowQuery = orDataRowRepository.findF1(timeId, subTimeId, ind_id, orgId);
                    if (cummulativeCode.equals(CummulativeCode.INC_D) || cummulativeCode.equals(CummulativeCode.INC_CD)) {
                        List<Indicator> indicators = indicatorRepository.findByObjIdAndAttrFormulaForbit(objId);
                        Set<Long> indicatorIds = indicators.stream().map(Indicator::getIndId).collect(Collectors.toSet());
                        for (OrDataRow orDataRow : orDataRowQuery) {
                            if (indicatorIds.contains(Long.parseLong(orDataRow.getIndId()))) {
                                orDataRowQuery.remove(orDataRow);
                            }
                        }
                    }
                } else if (cummulativeCode.equals(CummulativeCode.MPRE)) {
                    subTimeId = String.valueOf(Long.parseLong(Util.substrSQL(timeId, 0, 4))-1);
                    orDataRowQuery = orDataRowRepository.findF2(subTimeId, ind_id, orgId);
                } else if (cummulativeCode.equals(CummulativeCode.YPRE) ||
                        cummulativeCode.equals(CummulativeCode.EXTM) ||
                        cummulativeCode.equals(CummulativeCode.PQY)) {
                    if (cummulativeCode.equals(CummulativeCode.YPRE)) {
                        subTimeId = Util.concat(Long.parseLong(Util.substrSQL(timeId, 0, 4)) - 1,
                                Util.substrSQL(timeId, 5, 6));
                    } else if (cummulativeCode.equals(CummulativeCode.PQY)) {
                        subTimeId = Util.concat(Long.parseLong(Util.substrSQL(timeId, 0, 4)) - 1,
                                Util.substrSQL(timeId, 5, 5));
                    } else {
                        long year = Long.parseLong(timeId.substring(0,4)) - 1;
                        String month = Util.substrSQL(timeId, 5, 6);
                        subTimeId = Util.dateFormat(Util.concat(year, month), 99);
                    }
                    orDataRowQuery = orDataRowRepository.findF3(subTimeId, ind_id, orgId);
                } else if (cummulativeCode.equals(CummulativeCode.EXM)) {
                    String year = Util.substrSQL(timeId, 0,4);
                    String month = Util.substrSQL(timeId, 5,6);
                    String date = Util.concat(year, month);
                    if (month.equals("01")) {
                        orDataRowQuery = orDataRowRepository.findF4(timeId, date, year, ind_id, orgId);
                    } else {
                        orDataRowQuery = orDataRowRepository.findF4(timeId, year, year, ind_id, orgId);
                    }
                } else if (cummulativeCode.equals(CummulativeCode.INC_CQ)) {
                    orDataRowQuery = mongoTemplateService.findF5(timeId, ind_id, orgId);
                } else {
                    String prefix;
                    if (cummulativeCode.equals(CummulativeCode.PRE)) {
                        attrCode = r_tmp.substring(r_tmp.indexOf("#") + 1);
                        prefix = r_tmp.substring(0, r_tmp.indexOf("#"));
                    } else {
                        attrCode = r_tmp;
                        prefix = "";
                    }
                    OrAttribute attribute = attributeRepository.findByObjIdAndAttrCode(objId, attrCode);
                    fld_code_pre = attribute.getFldCode() == null ? "" : attribute.getFldCode();
                    subTimeId = timeId;
                    if (prefix.equalsIgnoreCase("PRE")) {
                        if (submitType == 6) {
                            if (timeIdPre != null) {
                                subTimeId = timeIdPre;
                            } else {
                                int year = Integer.parseInt(Util.substrSQL(timeId, 1, 4)) - 1;
                                int week = Integer.parseInt(Util.substrSQL(timeId, 6, 7)) - 1;
                                subTimeId = Util.concat(year, week);
                            }
                        } else {
                            subTimeId = Util.dateFormat(timeId, submitType);
                        }
                    }
                    orDataRowQuery = orDataRowRepository.findF3(subTimeId, ind_id, orgId);
                }
                for (OrDataRow orDataRow : orDataRowQuery) {
                    String value = orDataRow.getField(fld_code_pre);
                    if (value != null && !value.isEmpty()) {
                        sum += Long.parseLong(value);
                    }
                }
                if (sum != 0 || flag == 1) {
                    r_sql = String.valueOf(sum);
                } else {
                    r_sql = "null";
                }
                r_form = r_form.replace(match, r_sql);
            } else {
                break;
            }
        }
        return r_form;
    }

    private List<Indicator> getListIndicatorA(boolean getByTenantId, String objId, String orgId, String timeId, String orgType, String tenantId, String i_indId) {
        List<Indicator> indicatorsA;
        if (getByTenantId) {
            indicatorsA = new ArrayList<>(mongoTemplateService.findByTenantId(objId, Long.parseLong(orgId), timeId, orgType, tenantId));
            Collections.sort(indicatorsA, new Comparator<Indicator>() {
                @Override
                public int compare(Indicator ind1, Indicator ind2) {
                    int idxComparison = Long.compare(ind1.getIdx(), ind2.getIdx());
                    if (idxComparison != 0) {
                        return idxComparison;
                    }
                    return Long.compare(ind1.getIndId(), ind2.getIndId());
                }
            });
        } else {
            indicatorsA = new ArrayList<>(mongoTemplateService.findNotByTenantId(objId, Long.parseLong(orgId), timeId, orgType));
            indicatorsA = InputCal.getTree(indicatorsA, i_indId);
            Collections.sort(indicatorsA, new Comparator<Indicator>() {
                @Override
                public int compare(Indicator ind1, Indicator ind2) {
                    // Sắp xếp theo idx trước
                    int idxComparison = Long.compare(ind1.getIdx(), ind2.getIdx());
                    return idxComparison;
                }
            });
        }

        for (Indicator indicator : indicatorsA) {
            OrIndGrant orIndGrant = orIndGrantRepository.findByCriteria(tenantId, Long.parseLong(orgId), timeId, indicator.getIndId());
            indicator.setOrIndGrantTmp(orIndGrant);
        }
        return indicatorsA;
    }

    private List<OrDataRow> getListOrDataRowB(boolean getByTenantId, String orgId, String timeId, String tenantId) {
        List<OrDataRow> orDataRowsB;
        if (getByTenantId) {
            orDataRowsB = orDataRowRepository.findByOrgIdAndTimeIdAndTenantId(orgId, timeId, tenantId);
        } else {
            orDataRowsB = orDataRowRepository.findByOrgIdAndTimeId(orgId, timeId);
        }
        return orDataRowsB;
    }

    private List<Indicator> getListIndicatorC(boolean getByTenantId, String objId, String tenantId) {
        Indicator tmp;
        List<Indicator> indicatorsC;
        if (getByTenantId) {
            tmp = indicatorRepository.findByTenantIdAndObjIdAndParentIdNull(tenantId, objId);
            indicatorsC = indicatorRepository.findByTenantIdAndObjId(tenantId, objId);
        } else {
            tmp = indicatorRepository.findByObjIdAndParentIdNull(objId);
            indicatorsC = indicatorRepository.findByObjId(objId);
        }
        String id = tmp.getParentId();
        indicatorsC = InputCal.getTree(indicatorsC, id);
        return indicatorsC;
    }


    private Packet getResult(String objId, String tenantId, String i_attrId, List<Indicator> indicatorsA, List<OrDataRow> orDataRowsB,
                             List<Indicator> indicatorsC) throws Exception {

        // pack data
        // lấy ds fld, attr_id
        List<OrAttribute> attributes = new ArrayList<>();
        List<OrAttribute> attributesGraph = attributeRepository.findAllByObjIdAndTenantIdOrderByAttrId(objId, tenantId);
        attributesGraph = InputCal.getTree(attributesGraph, i_attrId);
        for (OrAttribute orAttribute : attributesGraph) {
            if (orAttribute.getParentId() != null && orAttribute.getFldCode() != null) {
                attributes.add(orAttribute);
            }
        }

        return Packet.createInstance(attributes, indicatorsA, orDataRowsB, indicatorsC);
    }
}
