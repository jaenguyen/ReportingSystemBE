package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.Indicator;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndicatorRepository extends AbstractBaseRepository<Indicator, String> {
    List<Indicator> findByObjIdAndTenantIdOrderByIndId(long objId, long tenantId);

    @Query("{'TENANT_ID': ?0, 'OBJ_ID': ?1, 'PARENT_ID': {'$ne': null}}")
    List<Indicator> findByTenantIdAndObjIdAndParentIdNotNull(String tenantId, String objId);

    @Query("{'TENANT_ID': ?0, 'OBJ_ID': ?1, 'ATTR_FORMULA_FORBIT': 1}")
    List<Indicator> findByTenantIdAndObjIdAndAttrFormulaForbit(String tenantId, String objId);

    @Query("{'OBJ_ID': ?0, 'ATTR_FORMULA_FORBIT': 1}")
    List<Indicator> findByObjIdAndAttrFormulaForbit(String objId);

    Indicator findByTenantIdAndObjIdAndParentIdNull(String tenantId, String objId);

    Indicator findByObjIdAndParentIdNull(String objId);

    List<Indicator> findByTenantIdAndObjId(String tenantId, String objId);

    List<Indicator> findByObjId(String objId);

}
