package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.Object;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ObjectRepository extends AbstractBaseRepository<Object, String>{
    List<Object> findByProgramIdAndOrgIdAndTenantIdAndObjTypeAndObjNameContainsIgnoreCase(String programId, String orgId, String tenantId, String objType, String key);

    List<Object>  findByTenantIdAndObjCodeIgnoreCaseAndObjType(String tenantId, String objCode, String objType);

    Object findObjectByObjId(long objId);
}
