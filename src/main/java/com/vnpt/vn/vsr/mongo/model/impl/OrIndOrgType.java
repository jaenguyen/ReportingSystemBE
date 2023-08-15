package com.vnpt.vn.vsr.mongo.model.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "OR_IND_ORGTYPE")
@NoArgsConstructor
public class OrIndOrgType {

    @Id
    private String id;
    @Field(value = "IND_ID")
    private long indId;
    @Field(value = "ORG_TYPE")
    private String orgType;
    @Field(value = "TENANT_ID")
    private String tenantId;
}

