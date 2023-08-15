package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.OrAttribute;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeRepository extends AbstractBaseRepository<OrAttribute,String>{
    List<OrAttribute> findAllByObjIdAndTenantIdOrderByAttrId(String objId, String tenantId);

    @Query("{'FLD_CODE': {$ne: null}, 'FORMULA': {$ne: null}, 'OBJ_ID': ?0, '$or': [{'IS_PREF': null}, {'IS_PREF': {$ne: 1}}]}")
    List<OrAttribute> findAttributesHasFormula(String objId);

    OrAttribute findByObjIdAndAttrCode(String objId, String attrCode);
}
