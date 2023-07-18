package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.common.CummulativeCode;
import com.vnpt.vn.vsr.mongo.common.ResponseConstant;
import com.vnpt.vn.vsr.mongo.common.Utils;
import com.vnpt.vn.vsr.mongo.model.impl.*;
import com.vnpt.vn.vsr.mongo.model.impl.Object;
import com.vnpt.vn.vsr.mongo.model.payload.response.Response;
import com.vnpt.vn.vsr.mongo.repository.OrDataRowRepository;
import com.vnpt.vn.vsr.mongo.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class OrDataRowServiceImpl extends AbstractBaseServiceImpl<OrDataRow, String> {

    @Autowired
    private OrDataRowRepository orDataRowRepository;
    @Autowired
    private ObjectServiceImpl objectService;
    @Autowired
    private IndicatorServiceImpl indicatorService;
    @Autowired
    private OrAttributeServiceImpl orAttributeService;
    @Autowired
    private SequenceGeneratorService generateSequence;
    private final MongoTemplate mongoTemplate;

    public OrDataRowServiceImpl(OrDataRowRepository orDataRowRepository, MongoTemplate mongoTemplate) {
        super(orDataRowRepository);
        this.mongoTemplate = mongoTemplate;
    }

    public List<OrDataRow> findF5(String timeId, String indId) {
        int quarter = (Integer.parseInt(timeId.substring(4)) - 1) / 3 + 1;
        int year = Integer.parseInt(timeId.substring(0, 4));

        AggregationOperation matchOperation = Aggregation.match(
                Criteria.where("TIME_ID").regex("^" + year + "(0?" + (quarter*3-2) + "|0?" + (quarter*3-1) + "|0?" + (quarter*3) + ")")
                        .and("IND_ID").is(indId)
        );

        Aggregation aggregation = Aggregation.newAggregation(matchOperation);
        AggregationResults<OrDataRow> results = mongoTemplate.aggregate(aggregation, "OR_DATAROW", OrDataRow.class);

        return results.getMappedResults();
    }

    public Response test(String objId, String tenantID, String orgId, String timeId, int submitType, String timeIdPre, String subtimeId, String indId, String r_time) throws Exception {
        List<OrDataRow> data = findF5(timeId, indId);
        return new Response(0, ResponseConstant.MESSAGE_OK, data);
    }

    public Response getDataAndPreTime(String objId, String tenantId, String orgId, String timeId, int submitType, String timeIdPre) throws Exception {
        Object object = objectService.findObjectByObjId(Long.parseLong(objId));
        if (object == null) {
            return new Response(0, ResponseConstant.ID_NOT_AVAILABLE, null);
        }
        int flag = object.getNullTo0();
        String orgType = object.getOrgType();

        // lấy danh sách indicators
        List<Indicator> indicators = indicatorService.getIndicatorsIsExist(tenantId, objId, orgId, timeId);
        if (indicators == null) {
            return new Response(0, ResponseConstant.DATA_NOT_EXISTS, null);
        }
        // tạo mới bản ghi nếu null
        createDataRowIfNull(indicators, orgId, timeId, objId, tenantId);

        // lấy danh sách attribute có formula, cập nhập formula child vào biến formulaBasic
        List<OrAttribute> orAttributes = orAttributeService.findAttributesHasFormula(objId);
        orAttributeService.setFormulaChild(orAttributes);

        List<List<OrDataRow>> orDataRowUpdate = getOrDataRowListUpdate(orgId, objId, timeId, tenantId);
        updateFLD_CODE(orAttributes, orDataRowUpdate, flag, objId, orgId, timeId, submitType, timeIdPre);

        return new Response(0, ResponseConstant.MESSAGE_OK, object);
    }

    private void updateFLD_CODE(List<OrAttribute> orAttributes,List<List<OrDataRow>> orDataRowUpdate,
                                int flag, String objId, String orgId, String timeId, int submitType, String timeIdPre) throws Exception {
        List<OrDataRow> orDataRowListUpdateOp1 = orDataRowUpdate.get(0);
        List<OrDataRow> orDataRowListUpdateOp2 = orDataRowUpdate.get(1);
        for (OrAttribute orAttribute : orAttributes) {
            String fld_code = orAttribute.getFldCode();
            String r_form = orAttribute.getFormulaBasic();
            CummulativeCode cummulativeCode = CummulativeCode.getValue(r_form);
            if (cummulativeCode != null) {
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
                    updateFLD_CODE(orDataRowListUpdateOp1, flag, fld_code, r_form, objId, orgId, timeId, submitType, timeIdPre);
                } else {
                    updateFLD_CODE(orDataRowListUpdateOp2, flag, fld_code, r_form, objId, orgId, timeId, submitType, timeIdPre);
                }
            }
        }
    }
    private void updateFLD_CODE(List<OrDataRow> orDataRowListUpdate, int flag, String fld_code, String r_form, String objId, String orgId, String timeId, int submitType, String timeIdPre) throws Exception {
        for (OrDataRow orDataRow : orDataRowListUpdate) {
            try {
                orDataRow.setField(fld_code, getFLD_CODE_PRE(flag, orDataRow.getIndId(), r_form, objId, orgId, timeId, submitType, timeIdPre));
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
                OrAttribute orAttribute = orAttributeService.findByObjIdAndAndAttrCode(objId, attrCode);
                fld_code_pre = orAttribute.getFldCode() == null ? "" : orAttribute.getFldCode();

                if (cummulativeCode.equals(CummulativeCode.MN_INC) ||
                        cummulativeCode.equals(CummulativeCode.INCMN_PRE) ||
                        cummulativeCode.equals(CummulativeCode.INC_M) ||
                        cummulativeCode.equals(CummulativeCode.INC_Q) ||
                        cummulativeCode.equals(CummulativeCode.INC_D) || //
                        cummulativeCode.equals(CummulativeCode.INC_CD)) {//
                    subTimeId = Utils.substrSQL(timeId, 0 ,4);
                    orDataRowQuery = orDataRowRepository.findF1(timeId, subTimeId, ind_id);
                    if (cummulativeCode.equals(CummulativeCode.INC_D) || cummulativeCode.equals(CummulativeCode.INC_CD)) {
                        List<Indicator> indicators = indicatorService.findIndicatorsByCriteria3(objId);
                        Set<Long> indicatorIds = indicators.stream().map(Indicator::getIndId).collect(Collectors.toSet());
                        for (OrDataRow orDataRow : orDataRowQuery) {
                            if (indicatorIds.contains(Long.parseLong(orDataRow.getIndId()))) {
                                orDataRowQuery.remove(orDataRow);
                            }
                        }
                    }
                } else if (cummulativeCode.equals(CummulativeCode.MPRE)) {
                    subTimeId = String.valueOf(Long.parseLong(Utils.substrSQL(timeId, 0, 4))-1);
                    orDataRowQuery = orDataRowRepository.findF2(subTimeId, ind_id);
                } else if (cummulativeCode.equals(CummulativeCode.YPRE) ||
                        cummulativeCode.equals(CummulativeCode.EXTM) ||
                        cummulativeCode.equals(CummulativeCode.PQY)) {
                    if (cummulativeCode.equals(CummulativeCode.YPRE)) {
                        subTimeId = Utils.concat(Long.parseLong(Utils.substrSQL(timeId, 0, 4)) - 1,
                                Utils.substrSQL(timeId, 5, 6));
                    } else if (cummulativeCode.equals(CummulativeCode.PQY)) {
                        subTimeId = Utils.concat(Long.parseLong(Utils.substrSQL(timeId, 0, 4)) - 1,
                                Utils.substrSQL(timeId, 5, 5));
                    } else {
                        long year = Long.parseLong(timeId.substring(0,4)) - 1;
                        String month = Utils.substrSQL(timeId, 5, 6);
                        subTimeId = Utils.dateFormat(Utils.concat(year, month), 99);
                    }
                    orDataRowQuery = orDataRowRepository.findF3(subTimeId, ind_id);
                } else if (cummulativeCode.equals(CummulativeCode.EXM)) {
                    String year = Utils.substrSQL(timeId, 0,4);
                    String month = Utils.substrSQL(timeId, 5,6);
                    String date = Utils.concat(year, month);
                    if (month.equals("01")) {
                        orDataRowQuery = orDataRowRepository.findF4(timeId, date, year, ind_id);
                    } else {
                        orDataRowQuery = orDataRowRepository.findF4(timeId, year, year, ind_id);
                    }
                } else if (cummulativeCode.equals(CummulativeCode.INC_CQ)) {
                    orDataRowQuery = findF5(timeId, ind_id);
                } else {
                    String prefix;
                    if (cummulativeCode.equals(CummulativeCode.PRE)) {
                        attrCode = r_tmp.substring(r_tmp.indexOf("#") + 1);
                        prefix = r_tmp.substring(0, r_tmp.indexOf("#"));
                    } else {
                        attrCode = r_tmp;
                        prefix = "";
                    }
                    OrAttribute attribute = orAttributeService.findByObjIdAndAndAttrCode(objId, attrCode);
                    fld_code_pre = attribute.getFldCode();
                    subTimeId = timeId;
                    if (prefix.equalsIgnoreCase("PRE")) {
                        if (submitType == 6) {
                            subTimeId = timeIdPre;
                        } else {
                            subTimeId = Utils.dateFormat(timeId, submitType);
                        }
                    }
                    orDataRowQuery = orDataRowRepository.findF3(subTimeId, ind_id);
                }
                for (OrDataRow orDataRow : orDataRowQuery) {
                    String value = orDataRow.getField(fld_code_pre);
                    if (value != null) {
                        sum += Long.parseLong(value);
                    }
                }
                if (cummulativeCode.equals(CummulativeCode.MN_INC) || flag != 1) {
                    if (sum == 0) {
                        r_sql = null;
                    } else {
                        r_sql = String.valueOf(sum);
                    }
                } else {
                    r_sql = String.valueOf(sum);
                }
                r_form = r_form.replace(match, r_sql);
            } else {
                break;
            }
        }
        return r_form;
    }

    private List<List<OrDataRow>> getOrDataRowListUpdate(String orgId, String objId, String timeId, String tenantId) {
        List<OrDataRow> orDataRowListUpdateOp1 = new ArrayList<>();
        List<OrDataRow> orDataRowListUpdateOp2 = orDataRowRepository.findByOrgIdAndObjIdAndTimeId(orgId, objId, timeId);
        List<Indicator> indicators = indicatorService.findIndicatorsByCriteria2(tenantId, objId);
        Set<Long> indicatorIds = indicators.stream().map(Indicator::getIndId).collect(Collectors.toSet());
        for (OrDataRow orDataRow : orDataRowListUpdateOp2) {
            if (!indicatorIds.contains(Long.parseLong(orDataRow.getIndId()))) {
                orDataRowListUpdateOp1.add(orDataRow);
            }
        }
        List<List<OrDataRow>> list = new ArrayList<>();
        list.add(orDataRowListUpdateOp1);
        list.add(orDataRowListUpdateOp2);
        return list;
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
}
