package com.vnpt.vn.vsr.mongo.repository;

import com.vnpt.vn.vsr.mongo.model.impl.OrAttribute;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrAttributeRepository extends AbstractBaseRepository<OrAttribute, String> {

    @Query("{" +
            "'FLD_CODE': { $ne: null }, " +
            "'FORMULA': { $ne: null }, " +
            "'OBJ_ID': ?0," +
            "'$or': [{'IS_PREF': null}, {'IS_PREF': { $ne: 1 }}]" +
            "}")
    List<OrAttribute> findAttributesHasFormula(String objId);

    List<OrAttribute> findOrAttributesByObjIdOrderByAttrId(String objId);
}
