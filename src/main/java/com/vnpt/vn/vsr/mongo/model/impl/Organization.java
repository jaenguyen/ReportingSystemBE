package com.vnpt.vn.vsr.mongo.model.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("ORG_ORGANIZATION")
@NoArgsConstructor
public class Organization {

    @Id
    private String id;
    @Field(value = "ORG_ID")
    private long orgId;
    @Field(value = "PARENT_ID")
    private String parentId;
    @Field(value = "ORG_TYPE")
    private String orgType;
    @Field(value = "ORG_CODE")
    private String orgCode;
    @Field(value = "ORG_NAME")
    private String orgName;
    @Field(value = "ORG_LEVEL")
    private String orgLevel;
    @Field(value = "NOTE")
    private String note;
    @Field(value = "STATUS")
    private int status;
    @Field(value = "ROOT_ID")
    private String rootId;
    @Field(value = "TENANT_ID")
    private String tenantId;
    @Field(value = "FULL_NAME")
    private String fullName;

}
