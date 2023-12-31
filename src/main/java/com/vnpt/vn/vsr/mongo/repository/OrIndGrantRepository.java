package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.OrIndGrant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrIndGrantRepository extends MongoRepository<OrIndGrant, String> {

    @Query("{'TENANT_ID': ?0, 'ORG_ID': ?1, '$or': [{'TIME_ID': ?2}, {'TIME_ID': null}], 'IND_ID': ?3}")
    OrIndGrant findByCriteria(String tenantId, long orgId, String timeId, long indId);
}

