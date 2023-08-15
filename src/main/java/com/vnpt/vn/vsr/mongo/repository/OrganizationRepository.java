package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.OrIndGrant;
import com.vnpt.vn.vsr.mongo.model.impl.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {

    Organization findOrganizationByOrgIdAndTenantId(long orgId, String tenantId);
}
