package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.Indicator;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndicatorRepository extends AbstractBaseRepository<Indicator, String> {

    @Query("{" +
            "'TENANT_ID': ?0, " +
            "'OBJ_ID': ?1, " +
            "'PARENT_ID': { '$ne': null }" +
            "}")
    List<Indicator> findIndicatorsByCriteria(String tenantId, String objId);
}