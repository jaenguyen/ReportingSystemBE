package com.vnpt.vn.vsr.mongo.service.impl;

import com.vnpt.vn.vsr.mongo.model.impl.Indicator;
import com.vnpt.vn.vsr.mongo.model.impl.OrDataRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoTemplateService {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Indicator> findOrIndGrants(String objId, String tenantId, long orgId, String timeId) {
        LookupOperation lookupOrIndGrants = LookupOperation.newLookup()
                .from("OR_IND_GRANT")
                .localField("IND_ID")
                .foreignField("IND_ID")
                .as("orIndGrants");

        MatchOperation matchOperation = Aggregation.match(
                Criteria.where("TENANT_ID").is(tenantId).
                        and("OBJ_ID").is(objId).
                        and("PARENT_ID").ne(null).
                        andOperator(
                                new Criteria().orOperator(
                                        Criteria.where("STATUS").is(1),
                                        Criteria.where("orIndGrants.TENANT_ID").is(tenantId).
                                                and("orIndGrants.ORG_ID").is(orgId).
                                                andOperator(
                                                        new Criteria().orOperator(
                                                                Criteria.where("orIndGrants.TIME_ID").is(timeId),
                                                                Criteria.where("orIndGrants.TIME_ID").is(null)
                                                        )
                                                )
                                        )

                                )
                        );

        Aggregation aggregation = Aggregation.newAggregation(lookupOrIndGrants, matchOperation);
        AggregationResults<Indicator> results = mongoTemplate.aggregate(aggregation, "OR_INDICATOR", Indicator.class);

        return results.getMappedResults();
    }

    // cq
    public List<OrDataRow> findF5(String timeId, String indId, String orgId) {
        int quarter = (Integer.parseInt(timeId.substring(4)) - 1) / 3 + 1;
        int year = Integer.parseInt(timeId.substring(0, 4));

        AggregationOperation matchOperation = Aggregation.match(
                Criteria.where("TIME_ID").regex("^" + year + "(0?" + (quarter*3-2) + "|0?" + (quarter*3-1) + "|0?" + (quarter*3) + ")")
                        .and("IND_ID").is(indId).and("ORG_ID").is(orgId)
        );

        Aggregation aggregation = Aggregation.newAggregation(matchOperation);
        AggregationResults<OrDataRow> results = mongoTemplate.aggregate(aggregation, "OR_DATAROW", OrDataRow.class);

        return results.getMappedResults();
    }

    public List<Indicator> findNotByTenantId(String objId, long orgId, String timeId, String orgType) {
        LookupOperation lookupOrIndGrants = LookupOperation.newLookup()
                .from("OR_IND_GRANT")
                .localField("IND_ID")
                .foreignField("IND_ID")
                .as("orIndGrants");

        LookupOperation lookupOrIndOrgTypes = LookupOperation.newLookup()
                .from("OR_IND_ORGTYPE")
                .localField("IND_ID")
                .foreignField("IND_ID")
                .as("orIndOrgTypes");

        MatchOperation matchOperation = Aggregation.match(
                Criteria.where("OBJ_ID").is(objId).
                        and("PARENT_ID").ne(null).
                        andOperator(
                                new Criteria().orOperator(
                                        Criteria.where("STATUS").is(1),
                                        Criteria.where("orIndGrants.ORG_ID").is(orgId).
                                                andOperator(
                                                        new Criteria().orOperator(
                                                                Criteria.where("orIndGrants.TIME_ID").is(timeId),
                                                                Criteria.where("orIndGrants.TIME_ID").is(null)
                                                        )
                                                ),
                                        Criteria.where("orIndOrgTypes.ORG_TYPE").is("3")
                                )

                        )
        );

        Aggregation aggregation = Aggregation.newAggregation(lookupOrIndGrants, lookupOrIndOrgTypes, matchOperation);
        AggregationResults<Indicator> results = mongoTemplate.aggregate(aggregation, "OR_INDICATOR", Indicator.class);

        return results.getMappedResults();
    }

    public List<Indicator> findByTenantId(String objId, long orgId, String timeId, String orgType, String tenantId) {
        LookupOperation lookupOrIndGrants = LookupOperation.newLookup()
                .from("OR_IND_GRANT")
                .localField("IND_ID")
                .foreignField("IND_ID")
                .as("orIndGrants");

        LookupOperation lookupOrIndOrgTypes = LookupOperation.newLookup()
                .from("OR_IND_ORGTYPE")
                .localField("IND_ID")
                .foreignField("IND_ID")
                .as("orIndOrgTypes");

        MatchOperation matchOperation = Aggregation.match(
                Criteria.where("OBJ_ID").is(objId).
                        and("PARENT_ID").ne(null).
                        andOperator(
                                new Criteria().orOperator(
                                        Criteria.where("STATUS").is(1),
                                        Criteria.where("orIndGrants.ORG_ID").is(orgId).and
                                                ("orIndGrants.TENANT_ID").is(tenantId).
                                                andOperator(
                                                        new Criteria().orOperator(
                                                                Criteria.where("orIndGrants.TIME_ID").is(timeId),
                                                                Criteria.where("orIndGrants.TIME_ID").is(null)
                                                        )
                                                ),
                                        Criteria.where
                                                ("orIndOrgTypes.ORG_TYPE").is(orgType).and
                                                ("orIndGrants.TENANT_ID").is(tenantId)
                                )

                        )
        );

        Aggregation aggregation = Aggregation.newAggregation(lookupOrIndGrants, lookupOrIndOrgTypes, matchOperation);
        AggregationResults<Indicator> results = mongoTemplate.aggregate(aggregation, "OR_INDICATOR", Indicator.class);

        return results.getMappedResults();
    }
}
