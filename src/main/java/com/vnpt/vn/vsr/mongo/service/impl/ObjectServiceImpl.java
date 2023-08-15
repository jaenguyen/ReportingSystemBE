package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.common.Consts;
import com.vnpt.vn.vsr.mongo.model.impl.Object;
import com.vnpt.vn.vsr.mongo.model.payload.response.DataResponse;
import com.vnpt.vn.vsr.mongo.repository.ObjectRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectServiceImpl extends AbstractBaseServiceImpl<Object, String> {

    private final ObjectRepository objectRepository;

    public ObjectServiceImpl(ObjectRepository objectRepository) {
        super(objectRepository);
        this.objectRepository = objectRepository;
    }

    public ResponseEntity<?> getObjectByProgram(String programId, String key, String orgId, String tenantId, String objType) {
        return new ResponseEntity<>(new DataResponse(0, Consts.MESSAGE_OK, objectRepository.findByProgramIdAndOrgIdAndTenantIdAndObjTypeAndObjNameContainsIgnoreCase(programId, orgId, tenantId, objType, key)), HttpStatusCode.valueOf(200));
    }

    public List<Object> findByTenantIdAndObjCodeIgnoreCaseAndObjType(String tenantId, String objCode, String objType) {
        return objectRepository.findByTenantIdAndObjCodeIgnoreCaseAndObjType(tenantId, objCode, objType);
    }

}
