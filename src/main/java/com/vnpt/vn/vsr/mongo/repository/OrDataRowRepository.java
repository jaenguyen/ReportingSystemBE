package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.OrDataRow;
import org.springframework.stereotype.Repository;

@Repository
public interface OrDataRowRepository extends AbstractBaseRepository<OrDataRow, String> {

    OrDataRow findByOrgIdAndTimeIdAndIndId(String orgId, String timeId, String indId);
}
