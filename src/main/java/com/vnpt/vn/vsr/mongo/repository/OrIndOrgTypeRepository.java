package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.OrIndOrgType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrIndOrgTypeRepository extends MongoRepository<OrIndOrgType, String> {

}

