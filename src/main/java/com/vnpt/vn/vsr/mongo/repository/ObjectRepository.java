package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.Object;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepository extends AbstractBaseRepository<Object, String> {

    Object findObjectByObjId(long objId);
}
