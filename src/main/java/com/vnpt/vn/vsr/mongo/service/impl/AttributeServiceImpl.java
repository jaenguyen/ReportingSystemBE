package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.common.Consts;
import com.vnpt.vn.vsr.mongo.model.impl.OrAttribute;
import com.vnpt.vn.vsr.mongo.model.payload.response.Response;
import com.vnpt.vn.vsr.mongo.repository.AbstractBaseRepository;
import com.vnpt.vn.vsr.mongo.repository.AttributeRepository;
import com.vnpt.vn.vsr.mongo.service.SequenceGeneratorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeServiceImpl extends AbstractBaseServiceImpl<OrAttribute, String>{
    private final SequenceGeneratorService generateSequence;
    private final AttributeRepository attributeRepository;

    public AttributeServiceImpl(AbstractBaseRepository<OrAttribute, String> abstractBaseRepository, SequenceGeneratorService generateSequence, AttributeRepository attributeRepository) {
        super(abstractBaseRepository);
        this.generateSequence = generateSequence;
        this.attributeRepository = attributeRepository;
    }


    public Response createAttribute(List<OrAttribute> orAttributes){
        for(OrAttribute attr :orAttributes){
            if(attr !=null){
                attr.setAttrId(generateSequence.generateSequence(OrAttribute.SEQUENCE_NAME));

            }
        }

//        Indicator result = this.save(indicator);
        return new Response(0, Consts.MESSAGE_OK, attributeRepository.saveAll(orAttributes));
    }

    public Response findAttribute(String objId,String tenantId){
        return new Response(0, Consts.MESSAGE_OK, attributeRepository.findAllByObjIdAndTenantIdOrderByAttrId(objId, tenantId));

    }

}
