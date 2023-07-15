package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.common.CummulativeCode;
import com.vnpt.vn.vsr.mongo.model.impl.OrAttribute;
import com.vnpt.vn.vsr.mongo.repository.OrAttributeRepository;
import com.vnpt.vn.vsr.mongo.service.SequenceGeneratorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OrAttributeServiceImpl extends AbstractBaseServiceImpl<OrAttribute, String> {

    private final OrAttributeRepository orAttributeRepository;
    private final SequenceGeneratorService generateSequence;

    public OrAttributeServiceImpl(OrAttributeRepository orAttributeRepository,
                                  SequenceGeneratorService generateSequence) {
        super(orAttributeRepository);
        this.orAttributeRepository = orAttributeRepository;
        this.generateSequence = generateSequence;
    }

    public List<OrAttribute> findAttributesHasFormula(String objId) {
        return orAttributeRepository.findAttributesHasFormula(objId);
    }

    public List<OrAttribute> findOrAttributesByObjIdOrderByAttrId(String objId) {
        return orAttributeRepository.findOrAttributesByObjIdOrderByAttrId(objId);
    }

    public String getFormulaChild(String formulaInput, List<OrAttribute> orAttributeList) {
        String formula = formulaInput;
        String formula1 = formulaInput;
        while (true) {
            Matcher matcher = Pattern.compile("\\{(.*?)\\}").matcher(formula1);
            if (matcher.find()) {
                String match = matcher.group(0);
                int isCummulativeCode = CummulativeCode.isCummulativeCode(match);
                if (isCummulativeCode == 0) {
                    String attributeCode = match.replace("{", "").replace("}", "");
                    for (OrAttribute orAttribute : orAttributeList) {
                        if (orAttribute.getAttrCode().equals(attributeCode)) {
                            formula = formula.replace(match, getFormulaChild(orAttribute.getFormula(), orAttributeList));
                        }
                    }
                }
                formula1.replace(match, "");
            } else {
                break;
            }
        }
        return "(" + formula + ")";

    }
}