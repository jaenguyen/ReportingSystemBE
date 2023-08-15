package com.vnpt.vn.vsr.mongo.model.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "OR_IND_GRANT")
@NoArgsConstructor
public class OrIndGrant {

    @Id
    private String id;
    @Field(value = "IND_ID")
    private long indId;
    @Field(value = "ORG_ID")
    private long orgId;
    @Field(value = "TENANT_ID")
    private String tenantId;
    @Field(value = "TIME_ID")
    private String timeId;
    @Field(value = "TYPE")
    private String type;
    @Field(value = "IND_CATE")
    private String indCate;
    @Field(value = "OBJ_ID")
    private String objId;
}

