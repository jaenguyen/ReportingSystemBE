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
    @Query("{'$and': [{'TIME_ID': {$lte: ?0}}, {'TIME_ID': {$regex: '^?1'}}], 'IND_ID': ?2}")
    List<OrDataRow> findF1(String timeId, String subTimeId, String indId);

    // 12mpre
    @Query("{'TIME_ID': {$regex: '^?0'}, 'IND_ID': ?1}")
    List<OrDataRow> findF2(String subTimeId, String indId);

    // ypre, extm, pqy, pre
    @Query("{'TIME_ID': ?0, 'IND_ID': ?1}")
    List<OrDataRow> findF3(String subTimeId, String indId);

    // exm
    @Query("{'$and': [{'TIME_ID': {$lte: ?0}}, {'TIME_ID': { '$ne': ?1}}, {'TIME_ID': {$regex: '^?2'}}], 'IND_ID': ?3}")
    List<OrDataRow> findF4(String time_id, String r_time, String subTimeId, String indId);

    // inc_cq
}
