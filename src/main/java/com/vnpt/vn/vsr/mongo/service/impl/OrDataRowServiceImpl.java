package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.common.ResponseConstant;
import com.vnpt.vn.vsr.mongo.model.impl.*;
import com.vnpt.vn.vsr.mongo.model.impl.Object;
import com.vnpt.vn.vsr.mongo.model.payload.response.Response;
import com.vnpt.vn.vsr.mongo.repository.OrDataRowRepository;
import com.vnpt.vn.vsr.mongo.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Response getDataAndPreTime(String objId, String tenantId, String orgId, String timeId) {
        Object object = objectService.findObjectByObjId(Long.parseLong(objId));
        if (object == null) {
            return new Response(0, ResponseConstant.ID_NOT_AVAILABLE, null);
        }
        int flag = object.getNullTo0();
        int orgType = object.getOrgType();

        List<Indicator> indicators = indicatorService.getIndicatorsIsExist(tenantId, objId, orgId, timeId);
        if (indicators == null) {
            return new Response(0, ResponseConstant.DATA_NOT_EXISTS, null);
        }
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

//                orDataRowRepository.save(orDataRow);
            }
        }

        // danh sách attribute has formula
//        List<OrAttribute> orAttributeList = orAttributeService.findOrAttributesByObjIdOrderByAttrId(objId);
        List<OrAttribute> orAttributeList = orAttributeService.findAttributesHasFormula(objId);
        for (OrAttribute attribute : orAttributeList) {
            String formula = attribute.getFormula();
            String formulaBasic = "";
            if (formula != null && !formula.isEmpty()) {
                formulaBasic = orAttributeService.getFormulaChild(formula, orAttributeList);
            }
            attribute.setFormulaBasic(formulaBasic);

        }
        return new Response(0, ResponseConstant.MESSAGE_OK, object);
    }
}
