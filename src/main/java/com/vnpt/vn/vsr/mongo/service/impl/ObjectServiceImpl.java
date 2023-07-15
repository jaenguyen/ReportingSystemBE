package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.model.impl.Object;
import com.vnpt.vn.vsr.mongo.repository.ObjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectServiceImpl extends AbstractBaseServiceImpl<Object, String> {

    private final ObjectRepository objectRepository;

    public ObjectServiceImpl(ObjectRepository objectRepository) {
        super(objectRepository);
        this.objectRepository = objectRepository;
    }

    public Object findObjectByObjId(long objId) {
        return objectRepository.findObjectByObjId(objId);
    }

    public List<Object> findAll() {
        return objectRepository.findAll();
    }
}
