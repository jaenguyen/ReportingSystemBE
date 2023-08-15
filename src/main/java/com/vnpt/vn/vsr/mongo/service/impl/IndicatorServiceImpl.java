package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.model.impl.Indicator;
import com.vnpt.vn.vsr.mongo.model.payload.response.Response;
import com.vnpt.vn.vsr.mongo.repository.IndicatorRepository;
import com.vnpt.vn.vsr.mongo.service.SequenceGeneratorService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndicatorServiceImpl extends AbstractBaseServiceImpl<Indicator, String> {
    private final IndicatorRepository indicatorRepository;

    private final SequenceGeneratorService generateSequence;

    public IndicatorServiceImpl(IndicatorRepository indicatorRepository,
                                 SequenceGeneratorService generateSequence) {
        super(indicatorRepository);
        this.indicatorRepository = indicatorRepository;
        this.generateSequence = generateSequence;
    }
public Response findIndicator(String objId,String tenantId){
    return new Response(0, "ok", indicatorRepository.findByObjIdAndTenantIdOrderByIndId( Long.parseLong(objId), Long.parseLong(tenantId)));
}

public Response createIndicator(List<Indicator> indicator){
    for(Indicator ind :indicator){
        if(ind !=null){
            ind.setIndId(generateSequence.generateSequence(Indicator.SEQUENCE_NAME));

        }
    }

//        Indicator result = this.save(indicator);
    return new Response(0, "OK", indicatorRepository.saveAll(indicator));
}

}
