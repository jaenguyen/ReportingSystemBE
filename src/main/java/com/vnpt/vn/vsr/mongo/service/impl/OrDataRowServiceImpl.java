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
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vnpt.vn.vsr.mongo.common.CummulativeCode.MN_INC;

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

    public List<OrDataRow> findF5(String orgId, String timeId) {
        int searchTimeId = Integer.parseInt(timeId);

        // Aggregation pipeline để so sánh TIME_ID với thời gian cụ thể và trả về kết quả
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("ORG_ID").is(orgId)),
                Aggregation.match(Criteria.where("TIME_ID").lte(searchTimeId)),
                Aggregation.project("ORG_ID", "TIME_ID")
                        .andExpression("substr(TIME_ID, 0, 4)").as("YEAR")
                        .andExpression("substr(TIME_ID, 5, 1)").as("QUARTER"),
                Aggregation.match(Criteria.where("YEAR").is(searchTimeId / 100)),
                Aggregation.match(Criteria.where("QUARTER").is((searchTimeId % 100 + 2) / 3))
        );

        // Thực hiện truy vấn aggregation và trả về kết quả
        AggregationResults<OrDataRow> results = mongoTemplate.aggregate(aggregation, "OR_DATAROW", OrDataRow.class);
        return results.getMappedResults();
    }

    public Response test(String objId, String tenantID, String orgId, String timeId, int submitType, String timeIdPre, String subtimeId, String indId, String r_time) throws Exception {
//        Object object = objectService.findObjectByObjId(Long.parseLong(objId));

//        List<Indicator> indicators = indicatorService.findIndicatorsByCriteria(tenantID, objId);
//        List<Indicator> indicators1 = indicatorService.findIndicatorsByCriteria2(tenantID, objId);

//        List<OrAttribute> orAttributes = orAttributeService.findAttributesHasFormula(objId);
//        OrAttribute orAttribute = orAttributeService.findByObjIdAndAndAttrCode(objId, "TT_KTXH_THANG_10");
//        OrAttribute orAttribute1 = orAttributeService.findByObjIdAndAndAttrCode(objId, "TT_KTXH_THANG_11");

//        OrDataRow orDataRow1 = orDataRowRepository.findByOrgIdAndTimeIdAndIndId(orgId, timeId, "435");
//        OrDataRow orDataRow2 = orDataRowRepository.findByOrgIdAndTimeIdAndIndId(orgId, timeId, "436");
//        OrDataRow orDataRow3 = orDataRowRepository.findByOrgIdAndTimeIdAndIndId(orgId, timeId, "437");
//
//        orDataRow1.setFN01("231");
//        orDataRow2.setFN02("321");
//        orDataRow3.setField("FN03", "100");
//        String tmp = orDataRow1.getField("FN01");
//        List<OrDataRow> orDataRows = new ArrayList<>();
//        orDataRows.add(orDataRow1);
//        orDataRows.add(orDataRow2);
//        orDataRowRepository.save(orDataRow3);
//        orDataRowRepository.saveAll(orDataRows);
//        List<OrDataRow> orDataRows = orDataRowRepository.findByOrgIdAndObjIdAndTimeId(orgId, objId, timeId);
//        List<OrDataRow> orDataRows1= orDataRowRepository.findF1(orgId, timeId, subtimeId, indId);
//        List<OrDataRow> orDataRows2 = orDataRowRepository.findF2(orgId, subtimeId, indId);
//        List<OrDataRow> orDataRows3 = orDataRowRepository.findF3(orgId, timeId, indId);
//        List<OrDataRow> orDataRows4 = orDataRowRepository.findF4(orgId, timeId, r_time, subtimeId, indId);

            return null;
    }

    public Response getDataAndPreTime(String objId, String tenantId, String orgId, String timeId, int submitType, String timeIdPre) throws Exception {
        Object object = objectService.findObjectByObjId(Long.parseLong(objId));
        if (object == null) {
            return new Response(0, ResponseConstant.ID_NOT_AVAILABLE, null);
        }
        int flag = object.getNullTo0();
        int orgType = object.getOrgType();

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

        for (OrAttribute orAttribute : orAttributes) {
            String fld_code = orAttribute.getFldCode();
            String r_form = orAttribute.getFormulaBasic();
            List<OrDataRow> orDataRowListUpdate = null;
            CummulativeCode cummulativeCode = CummulativeCode.getValue(r_form);
            if (cummulativeCode != null) {
                if (cummulativeCode.equals(CummulativeCode.MPRE) ||
                        cummulativeCode.equals(CummulativeCode.INC_M) ||
                        cummulativeCode.equals(CummulativeCode.INC_Q) ||
                        cummulativeCode.equals(CummulativeCode.INC_CQ) ||
                        cummulativeCode.equals(MN_INC) ||
                        cummulativeCode.equals(CummulativeCode.INCMN_PRE) ||
                        cummulativeCode.equals(CummulativeCode.INC_CD) ||
                        cummulativeCode.equals(CummulativeCode.INC_D) ||
                        cummulativeCode.equals(CummulativeCode.EXTM) ||
                        cummulativeCode.equals(CummulativeCode.EXM)) {
                    orDataRowListUpdate = orDataRowUpdate.get(0);
                } else {
                    orDataRowListUpdate = orDataRowUpdate.get(1);
                }
                updateFLD_CODE(orDataRowListUpdate, flag, fld_code, r_form, objId, orgId, timeId, submitType, timeIdPre);
            }
        }
        return new Response(0, ResponseConstant.MESSAGE_OK, object);
    }

    private void updateFLD_CODE(List<OrDataRow> orDataRowListUpdate, int flag, String fld_code, String r_form, String objId, String orgId, String timeId, int submitType, String timeIdPre) throws Exception {
        String ind_id = "";
        String fld_code_pre = "";
        for (OrDataRow orDataRow : orDataRowListUpdate) {
            ind_id = orDataRow.getIndId();
            fld_code_pre = getFLD_CODE_PRE(flag, ind_id, r_form, objId, orgId, timeId, submitType, timeIdPre);
            try {
                orDataRow.setField(fld_code, fld_code_pre);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        saveAll(orDataRowListUpdate);
    }

    private String getFLD_CODE_PRE(int flag, String ind_id, String r_form, String objId, String orgId, String timeId, int submitType, String timeIdPre) throws Exception {
        String attrCode = "";
        String fld_code_pre = "";
        CummulativeCode cummulativeCode = CummulativeCode.ANY;
        String subTimeId = "";
        long sum = 0;
        String r_sql = "";
        List<OrDataRow> orDataRowQuery = new ArrayList<>();
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
                attrCode = r_tmp.substring(r_tmp.indexOf("#") + 1, r_tmp.length());
                OrAttribute orAttribute = orAttributeService.findByObjIdAndAndAttrCode(objId, attrCode);
                fld_code_pre = orAttribute.getFldCode() == null ? "" : orAttribute.getFldCode();

                if (cummulativeCode.equals(MN_INC) ||
                        cummulativeCode.equals(CummulativeCode.INCMN_PRE) ||
                        cummulativeCode.equals(CummulativeCode.INC_M) ||
                        cummulativeCode.equals(CummulativeCode.INC_Q) ||
                        cummulativeCode.equals(CummulativeCode.INC_D) || //
                        cummulativeCode.equals(CummulativeCode.INC_CD)) {//
                    subTimeId = timeId.substring(0, 3);
                    orDataRowQuery = orDataRowRepository.findF1(orgId, timeId, subTimeId, ind_id);
                } else if (cummulativeCode.equals(CummulativeCode.MPRE)) {
                    subTimeId = String.valueOf(Long.parseLong(timeId.substring(0, 3)) - 1);
                    orDataRowQuery = orDataRowRepository.findF2(orgId, subTimeId, ind_id);
                } else if (cummulativeCode.equals(CummulativeCode.YPRE) ||
                        cummulativeCode.equals(CummulativeCode.EXTM) ||
                        cummulativeCode.equals(CummulativeCode.PQY)) {
                    if (cummulativeCode.equals(CummulativeCode.YPRE)) {
                        subTimeId = String.valueOf(Long.parseLong(timeId.substring(0, 3)) - 1) +
                                String.valueOf(Long.parseLong(timeId.substring(4, 5)));
                    } else if (cummulativeCode.equals(CummulativeCode.EXTM)) {
                        String year = String.valueOf(Long.parseLong(timeId.substring(0, 3)));
                        String month = String.valueOf(Long.parseLong(timeId.substring(4, 5)));
                        String date = year + "-" + month + "-01";
                        subTimeId = Utils.dateToYearMonth(date);
                    } else {
                        subTimeId = String.valueOf(Long.parseLong(timeId.substring(0, 3)) - 1) +
                                String.valueOf(Long.parseLong(timeId.substring(4, 4)));
                    }
                    orDataRowQuery = orDataRowRepository.findF3(orgId, subTimeId, ind_id);
                } else if (cummulativeCode.equals(CummulativeCode.EXM)) {
                    String year = timeId.substring(0,3);
                    String month = timeId.substring(4,5);
                    String date = year + month;
                    if (month.equals("01")) {
                        orDataRowQuery = orDataRowRepository.findF4(orgId, timeId, date, year, ind_id);
                    } else {
                        orDataRowQuery = orDataRowRepository.findF4(orgId, timeId, year, year, ind_id);
                    }
                } else if (cummulativeCode.equals(CummulativeCode.INC_CQ)) {
                    orDataRowQuery = findF5(orgId, timeId);
                } else {
                    String prefix = "";
                    if (cummulativeCode.equals(CummulativeCode.PRE)) {
                        attrCode = r_tmp.substring(r_tmp.indexOf("#") + 1, r_tmp.length());
                        prefix = r_tmp.substring(0, r_tmp.indexOf("#")-1);
                    } else {
                        attrCode = r_tmp;
                        prefix = "";
                    }
                    OrAttribute attribute = orAttributeService.findByObjIdAndAndAttrCode(objId, attrCode);
                    fld_code_pre = attribute.getFldCode();
                    String r_time_id = timeId;
                    if (prefix.equalsIgnoreCase("PRE")) {
                        switch (submitType) {
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                break;
                            case 8:
                                break;
                        }
                    }
                    orDataRowQuery = orDataRowRepository.findF3(orgId, r_time_id, ind_id);
                }
                for (OrDataRow orDataRow : orDataRowQuery) {
                    String value = orDataRow.getField(fld_code_pre);
                    if (value != null) {
                        sum += Long.parseLong(value);
                    }
                }
                if (cummulativeCode.equals(MN_INC) || flag != 1) {
                    if (sum == 0) {
                        r_sql = null;
                    } else {
                        r_sql = String.valueOf(sum);
                    }
                } else {
                    r_sql = String.valueOf(sum);
                }
                r_form.replace(match, r_sql);
            } else {
                break;
            }
        }
        return r_form;
    }

    private List<List<OrDataRow>> getOrDataRowListUpdate(String orgId, String objId, String timeId, String tenantId) {
        List<OrDataRow> orDataRowListUpdateOp1 = new ArrayList<>();
        List<OrDataRow> orDataRowListUpdateOp2 = orDataRowRepository.findByOrgIdAndObjIdAndTimeId(orgId, objId, timeId);
        List<Indicator> indicatorList = indicatorService.findIndicatorsByCriteria2(tenantId, objId);
        Set<String> indIds = new HashSet<>();
        for (Indicator indicator : indicatorList) {
            indIds.add(String.valueOf(indicator.getIndId()));
        }
        for (OrDataRow orDataRow : orDataRowListUpdateOp2) {
            if (!indIds.contains(orDataRow.getIndId())) {
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
