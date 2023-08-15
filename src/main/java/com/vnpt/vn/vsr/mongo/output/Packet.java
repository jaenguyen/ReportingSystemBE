package com.vnpt.vn.vsr.mongo.output;

import com.vnpt.vn.vsr.mongo.common.ComIndType;
import com.vnpt.vn.vsr.mongo.model.impl.Indicator;
import com.vnpt.vn.vsr.mongo.model.impl.OrAttribute;
import com.vnpt.vn.vsr.mongo.model.impl.OrDataRow;
import com.vnpt.vn.vsr.mongo.model.impl.OrIndGrant;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Packet implements Cloneable, Serializable {
    private static final long serialVersionUID = -6246253646683036310L;
    public List<Map<String, Object>> data;
    public static final String RN = "RN";
    public static final String IND_ID = "IND_ID";
    public static final String IND_INDEX = "IND_INDEX";
    public static final String IND_CODE = "IND_CODE";
    public static final String IND_NAME = "IND_NAME";
    public static final String IND_UNIT = "IND_UNIT";
    public static final String IS_SUMARY = "IS_SUMARY";
    public static final String DATA_ID = "DATA_ID";
    public static final String GROUP_ID = "GROUP_ID";
    public static final String IND_TYPE = "IND_TYPE";
    public static final String PARENT_ID = "PARENT_ID";
    public static final String COMMAND = "COMMAND";
    public static final String FORMULA = "FORMULA";
    public static final String IS_SUB_IND = "IS_SUB_IND";
    public static final String FIELD_ID = "FIELD_ID";
    public static final String PARENT_FIELD_ID = "PARENT_FIELD_ID";
    public static final String ALLOW_ADD_ROW = "ALLOW_ADD_ROW";
    public static final String IDX = "IDX";
    public static final String ATTR_FORMULA_FORBIT = "ATTR_FORMULA_FORBIT";
    public static final String ROOT_ID = "ROOT_ID";
    public static final String ORG_ID = "ORG_ID";

    public Packet() {
        this.data = new ArrayList<>();
    }
    public static Map<String, Object> packet(List<OrAttribute> data, Indicator indicator, Indicator indicatorC, OrDataRow orDataRowB) throws Exception {
        Map<String, Object> element = new LinkedHashMap<>();
        element.put(RN, indicator.getIndIndex());
        element.put(IND_ID, indicator.getIndId());
        element.put(IND_INDEX, indicator.getIndIndex());
        element.put(IND_CODE, indicator.getIndCode());
        element.put(IND_NAME, indicator.getIndName());
        element.put(IND_UNIT, indicator.getIndUnit());
        element.put(IS_SUMARY, indicator.getIsSumary());
        element.put(DATA_ID, orDataRowB.getDataId() == 0 ? null : orDataRowB.getDataId()); //
        ComIndType comIndType = ComIndType.get(String.valueOf(indicator.getIndType()));
        element.put(GROUP_ID, comIndType.getGroupId().isEmpty() ? "" : Integer.parseInt(comIndType.getGroupId()));
        element.put(IND_TYPE, indicator.getIndType());
        element.put(PARENT_ID, Long.parseLong(indicator.getParentId()));
        element.put(COMMAND, comIndType.getCommand());
        element.put(FORMULA, indicator.getFormula());
        int is_sub_ind = 0;
        if (indicator.getOrIndGrantTmp()!= null) {
            String type = indicator.getOrIndGrantTmp().getType();
            is_sub_ind = (type==null || type.isEmpty()) ? 0 : 1;
        }
        element.put(IS_SUB_IND, is_sub_ind);
        element.put(FIELD_ID, indicator.getFieldId());
        element.put(PARENT_FIELD_ID, indicator.getParentFieldId());
        element.put(ALLOW_ADD_ROW, indicator.getAllowAddRow());
        element.put(IDX, indicator.getIdx());
        element.put(ATTR_FORMULA_FORBIT, indicator.getAttrFormulaForbit());
        element.put(ROOT_ID, indicatorC.getRootId());
        element.put(ORG_ID, indicatorC.getOrgId());

        for (OrAttribute orAttribute : data) {
            long atrId = orAttribute.getAttrId();
            String fldCode = orAttribute.getFldCode();
            element.put("C"+atrId, orDataRowB.getField(fldCode));
        }
        return element;
    }
    public static Packet createInstance(List<OrAttribute> data, List<Indicator> indicatorsA, List<OrDataRow> orDataRowsB,
                                        List<Indicator> indicatorsC) throws Exception {
        Packet packet = new Packet();
        JSONObject jsonObjectC = new JSONObject();
        for (Indicator indicator : indicatorsC) {
            if (indicator.getParentId() != null) {
                jsonObjectC.put(String.valueOf(indicator.getIndId()), indicator);
            }
        }
        JSONObject jsonObjectB = new JSONObject();
        for (OrDataRow orDataRow : orDataRowsB) {
            jsonObjectB.put(orDataRow.getIndId(), orDataRow);
        }

        for (Indicator indicator : indicatorsA) {
            String indId = String.valueOf(indicator.getIndId());
            if (jsonObjectB.has(indId) && jsonObjectC.has(indId)) {
                packet.addData(packet(data, indicator, (Indicator) jsonObjectC.get(indId), (OrDataRow) jsonObjectB.get(indId)));
            }
        }

        return packet;
    }

    private void addData(Map<String, Object> element) {
        this.data.add(element);
    }

    public List<Map<String, Object>> getData() {
        return data;
    }
}
