package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.OrDataRow;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrDataRowRepository extends AbstractBaseRepository<OrDataRow, String> {

    OrDataRow findByOrgIdAndTimeIdAndIndId(String orgId, String timeId, String indId);

    List<OrDataRow> findByOrgIdAndObjIdAndTimeId(String orgId, String objId, String timeId);

    // mn_inc, incmn_pre, inc_m, inc_q
    // inc_d, inc_cd
    @Query("{'ORG_ID': ?0, '$and': [{'TIME_ID': {$lte: ?1}}, {'TIME_ID': {$regex: '^?2'}}], 'IND_ID': ?3}")
    List<OrDataRow> findF1(String orgId, String timeId, String subTimeId, String indId);

    // 12mpre
    @Query("{'ORG_ID': ?0, 'TIME_ID': {$regex: '^?1'}, 'IND_ID': ?2}")
    List<OrDataRow> findF2(String orgId, String subTimeId, String indId);

    // ypre, extm, pqy, pre
    @Query("{'ORG_ID': ?0, 'TIME_ID': ?1, 'IND_ID': ?2}")
    List<OrDataRow> findF3(String orgId, String subTimeId, String indId);

    // exm
    @Query("{'ORG_ID': ?0, '$and': [{'TIME_ID': {$lte: ?1}}, {'TIME_ID': { '$ne': ?2}}, {'TIME_ID': {$regex: '^?3'}}], 'IND_ID': ?4}")
    List<OrDataRow> findF4(String orgId, String time_id, String r_time, String subTimeId, String indId);

    // inc_cq
}
