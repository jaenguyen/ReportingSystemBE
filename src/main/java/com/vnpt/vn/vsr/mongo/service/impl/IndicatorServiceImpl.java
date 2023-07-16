package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.model.impl.Indicator;
import com.vnpt.vn.vsr.mongo.model.impl.OrIndGrant;
import com.vnpt.vn.vsr.mongo.repository.IndicatorRepository;
import com.vnpt.vn.vsr.mongo.service.SequenceGeneratorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndicatorServiceImpl extends AbstractBaseServiceImpl<Indicator, String> {

    private final IndicatorRepository indicatorRepository;
    private final OrIndGrantServiceImpl orIndGrantService;
    private final SequenceGeneratorService generateSequence;

    public IndicatorServiceImpl(IndicatorRepository indicatorRepository,
                                OrIndGrantServiceImpl orIndGrantService,
                                SequenceGeneratorService generateSequence) {
        super(indicatorRepository);
        this.indicatorRepository = indicatorRepository;
        this.orIndGrantService = orIndGrantService;
        this.generateSequence = generateSequence;
    }

    List<Indicator> findIndicatorsByCriteria(String tenantId, String objId) {
        return indicatorRepository.findIndicatorsByCriteria(tenantId, objId);
    }

    List<Indicator> findIndicatorsByCriteria2(String tenantId, String objId) {
        return indicatorRepository.findIndicatorsByCriteria2(tenantId, objId);
    }

    public List<Indicator> getIndicatorsIsExist(String tenantId, String objId, String orgId, String timeId) {
        List<Indicator> indicators = findIndicatorsByCriteria(tenantId, objId);
        for (Indicator indicator : indicators) {
            long status = indicator.getStatus();
            if (status != 1) {
                long indId = indicator.getIndId();
                List<OrIndGrant> orIndGrants = orIndGrantService.findAllByCriteria(tenantId, orgId, timeId, String.valueOf(indId));
                if (orIndGrants.isEmpty() || orIndGrants == null) {
                    indicators.remove(indicator);
                }
            }
        }
        return indicators;
    }
}
