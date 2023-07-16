package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.common.CummulativeCode;
import com.vnpt.vn.vsr.mongo.common.ResponseConstant;
import com.vnpt.vn.vsr.mongo.model.impl.*;
import com.vnpt.vn.vsr.mongo.model.impl.Object;
import com.vnpt.vn.vsr.mongo.model.payload.response.Response;
import com.vnpt.vn.vsr.mongo.repository.OrDataRowRepository;
import com.vnpt.vn.vsr.mongo.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    public OrDataRowServiceImpl(OrDataRowRepository orDataRowRepository) {
        super(orDataRowRepository);
    }

    public OrDataRow findByOrgIdAndTimeIdAndIndId(String orgId, String timeId, String indId) {
        return orDataRowRepository.findByOrgIdAndTimeIdAndIndId(orgId, timeId, indId);
    }

    public List<OrDataRow> findByOrgIdAndObjIdAndTimeId(String orgId, String objId, String timeId) {
        return orDataRowRepository.findByOrgIdAndObjIdAndTimeId(orgId, objId, timeId);
    }

    public Response getDataAndPreTime(String objId, String tenantId, String orgId, String timeId) {
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
            String r_form1 = r_form;
            if (CummulativeCode.getValue(r_form) != null) {

            }
        }
        return new Response(0, ResponseConstant.MESSAGE_OK, object);
    }

    private void updateFLD_CODE(String fldCode, String r_form, String objId, String tenantId, String orgId, String timeId, int flag) {
        JSONArray jsonArray = new JSONArray();
        while (true) {
            Matcher matcher = Pattern.compile("\\{(.*?)\\}").matcher(r_form);
            if (matcher.find()) {
                String match = matcher.group(0);
                String r_tmp = match.replace("{", "").replace("}", "");



            } else {
                break;
            }
        }
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
            OrDataRow orDataRow = findByOrgIdAndTimeIdAndIndId(orgId, timeId, indId);
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
