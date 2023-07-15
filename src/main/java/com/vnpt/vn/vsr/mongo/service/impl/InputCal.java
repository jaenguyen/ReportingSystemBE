package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.model.impl.OrAttribute;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputCal {

    public static String convertFormulaAttr(String formula, List<OrAttribute> orOrAttributeList) {
        String formula_tmp1 = formula;
        String formula_return = formula;
        while (true) {
            Matcher matcher1 = Pattern.compile("\\{(.*?)\\}").matcher(formula_tmp1);
            boolean exist_child = false;
            while (matcher1.find()) {
                String match = matcher1.group(0).replace("{", "").replace("}", "");
                //kiểm tra xem thuộc tính này có chưa công thức không
                for (int k = 0; k < orOrAttributeList.size(); k++) {
                    try {
                        OrAttribute attr = orOrAttributeList.get(k);
                        if ((attr.getAttrCode()).equals(match)) {
                            String formula_child = attr.getFormula();
                            if (formula_child !=null) {
                                exist_child = true;
                                String formula_child_tmp = convertFormulaAttr(formula_child, orOrAttributeList);
                                return formula_return.replace("{" + match + "}", "(" + formula_child_tmp + ")");
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
            if (exist_child == false) {
                break;
            }
        }
        return formula_return;
    }
}
