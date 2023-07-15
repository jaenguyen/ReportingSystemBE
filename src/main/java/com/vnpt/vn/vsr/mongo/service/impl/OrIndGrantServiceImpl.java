package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.model.impl.OrIndGrant;
import com.vnpt.vn.vsr.mongo.repository.OrIndGrantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrIndGrantServiceImpl {

    @Autowired
    private OrIndGrantRepository orIndGrantRepository;

    List<OrIndGrant> findAllByCriteria(String tenantId, String orgId, String timeId, String indId) {
        return orIndGrantRepository.findAllByCriteria(tenantId, orgId, timeId, indId);
    }
}
