package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.OrDataRow;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrDataRowRepository extends AbstractBaseRepository<OrDataRow, String> {

    OrDataRow findByOrgIdAndTimeIdAndIndId(String orgId, String timeId, String indId);

    List<OrDataRow> findByOrgIdAndObjIdAndTimeId(String orgId, String objId, String timeId);
}
