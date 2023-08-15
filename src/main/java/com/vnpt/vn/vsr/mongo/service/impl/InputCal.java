package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.common.CummulativeCode;
import com.vnpt.vn.vsr.mongo.model.impl.Indicator;
import com.vnpt.vn.vsr.mongo.model.impl.OrAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputCal {


    public static String convertFormulaAttr(String formula, List<OrAttribute> orAttributeList) {
        String formula_tmp1 = formula;
        String formula_return = formula;
        while (true) {
            Matcher matcher1 = Pattern.compile("\\{(.*?)\\}").matcher(formula_tmp1);
            boolean exist_child = false;
            while (matcher1.find()) {
                String match = matcher1.group(0).replace("{", "").replace("}", "");
                //kiểm tra xem thuộc tính này có chưa công thức không
                for (int k = 0; k < orAttributeList.size(); k++) {
                    try {
                        OrAttribute attr = orAttributeList.get(k);
                        if ((attr.getAttrCode()).equals(match)) {
                            String formula_child = attr.getFormula();
                            if (formula_child !=null) {
                                exist_child = true;
                                String formula_child_tmp = convertFormulaAttr(formula_child, orAttributeList);
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

    public static String getFormulaChild(String formulaInput, List<OrAttribute> orAttributeList) {
        String formula = formulaInput;
        String formula1 = formulaInput;
        while (true) {
            if (!formula.isEmpty() && formula != null) {
                Matcher matcher = Pattern.compile("\\{(.*?)\\}").matcher(formula1);
                if (matcher.find()) {
                    String match = matcher.group(0).replace("{", "").replace("}", "");
                    CummulativeCode cummulativeCode = CummulativeCode.getValue(match);
                    if (cummulativeCode.equals(CummulativeCode.ANY)) {
                        String attrCode = match;
                        for (OrAttribute orAttribute : orAttributeList) {
                            if (orAttribute.getAttrCode().equals(attrCode)) {
                                String fml = orAttribute.getFormula() == null ? "" : orAttribute.getFormula();
                                if (!fml.isEmpty()) {
                                    formula = formula.replace("{"+match+"}", getFormulaChild(fml, orAttributeList));
                                }
                            }
                        }
                    }
                    formula1 = formula1.replace("{" + match + "}", "");
                } else {
                    break;
                }
            }
        }
        return formula;
    }

    // tạo ds the tree
    public static <T>List<T> getTree(List<T> elements, String parentId) {
        Map<String, List<T>> map = getMap(elements);
        List<T> rs = new ArrayList<>();
        List<T> childRoot = map.get(parentId);
        if (parentId == null && childRoot.size() == 1) {
            T obj = childRoot.get(0);
            rs.add(obj);
            parentId = String.valueOf(getId(obj));
        }
        for (T root : map.get(parentId)) {
            long rootId = getId(root);
            rs = getChild(rs, map, root, rootId);
        }
        return rs;
    }

    public static <T>Map<String, List<T>> getMap(List<T> elements) {
        Map<String, List<T>> map = new HashMap<>();
        for (T element : elements) {
            List<T> elementList = new ArrayList<>();
            String parentIdTmp = getParentId(element);
            if (map.containsKey(parentIdTmp)) {
                elementList = map.get(parentIdTmp);
            }
            elementList.add(element);
            map.put(parentIdTmp, elementList);
        }
        return map;
    }

    public static <T> List<T> getChild(List<T> rs, Map<String, List<T>> map, T root, long rootId) {
        setRootId(root, rootId);
        rs.add(root);
        String id_node = String.valueOf(getId(root));
        if (map.containsKey(id_node)) {
            List<T> children = map.get(id_node);
            for (T child : children) {
                rs = getChild(rs, map, child, rootId);
            }
        }
        return rs;
    }

    private static void setRootId(Object obj, long rootId) {
        // Kiểm tra kiểu của đối tượng và trả về giá trị id tương ứng
        if (obj instanceof OrAttribute) {
            ((OrAttribute) obj).setRootId(rootId);
        } else if (obj instanceof Indicator) {
            ((Indicator) obj).setRootId(rootId);
        } else {
            throw new IllegalArgumentException("Unsupported type.");
        }
    }

    private static String getParentId(Object obj) {
        // Kiểm tra kiểu của đối tượng và trả về giá trị id tương ứng
        if (obj instanceof OrAttribute) {
            return ((OrAttribute) obj).getParentId();
        } else if (obj instanceof Indicator) {
            return ((Indicator) obj).getParentId();
        } else {
            throw new IllegalArgumentException("Unsupported type.");
        }
    }

    private static Long getId(Object obj) {
        // Kiểm tra kiểu của đối tượng và trả về giá trị id tương ứng
        if (obj instanceof OrAttribute) {
            return ((OrAttribute) obj).getAttrId();
        } else if (obj instanceof Indicator) {
            return ((Indicator) obj).getIndId();
        } else {
            throw new IllegalArgumentException("Unsupported type.");
        }
    }
}
