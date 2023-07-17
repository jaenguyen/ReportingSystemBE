package com.vnpt.vn.vsr.mongo.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vnpt.vn.vsr.mongo.model.AbstractBaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "OR_OBJECT")
@NoArgsConstructor
public class Object extends AbstractBaseEntity {

    @Transient
    public static final String SEQUENCE_NAME = "OR_OBJECT_SEQ";

    @Field(value = "OBJ_ID")
    @JsonProperty(value = "OBJ_ID")
    private long objId;

    @Field(value = "OBJ_NAME")
    @JsonProperty(value = "OBJ_NAME")
    private String objName;

    @Field(value = "ORG_NAME")
    @JsonProperty(value = "ORG_NAME")
    private String orgName;

    @Field(value = "ORG_TYPE")
    @JsonProperty(value = "ORG_TYPE")
    private String orgType;

    @Field(value = "ORG_TYPE_NAME")
    @JsonProperty(value = "ORG_TYPE_NAME")
    private String orgTypeName;

    @Field(value = "PARENT_ID")
    @JsonProperty(value = "PARENT_ID")
    private long parentId;

    @Field(value = "IS_GROUP")
    @JsonProperty(value = "IS_GROUP")
    private int isGroup;

    @Field(value = "IS_AUTO")
    @JsonProperty(value = "IS_AUTO")
    private int isAuto;

    @Field(value = "ORG_ID")
    @JsonProperty(value = "ORG_ID")
    private long orgId;

    @Field(value = "STATUS")
    @JsonProperty(value = "STATUS")
    private int status;

    @Field(value = "OBJ_TYPE")
    @JsonProperty(value = "OBJ_TYPE")
    private String objType;

    @Field(value = "SUBMIT_TYPE")
    @JsonProperty(value = "SUBMIT_TYPE")
    private String submitType;

    @Field(value = "DUE_DATE")
    @JsonProperty(value = "DUE_DATE")
    private Date dueDate;

    @Field(value = "PROGRAM_ID")
    @JsonProperty(value = "PROGRAM_ID")
    private String programId;

    @Field(value = "TPL_PATH")
    @JsonProperty(value = "TPL_PATH")
    private String tplPath;

    @Field(value = "INDEX_WIDTH")
    @JsonProperty(value = "INDEX_WIDTH")
    private int indexWidth;

    @Field(value = "INDEX_LABEL")
    @JsonProperty(value = "INDEX_LABEL")
    private String indexLabel;

    @Field(value = "INDEX_SHOW")
    @JsonProperty(value = "INDEX_SHOW")
    private int indexShow;

    @Field(value = "IND_CODE_WIDTH")
    @JsonProperty(value = "IND_CODE_WIDTH")
    private int indCodeWidth;

    @Field(value = "IND_CODE_LABEL")
    @JsonProperty(value = "IND_CODE_LABEL")
    private String indCodeLabel;

    @Field(value = "IND_CODE_SHOW")
    @JsonProperty(value = "IND_CODE_SHOW")
    private int indCodeShow;

    @Field(value = "IND_NAME_WIDTH")
    @JsonProperty(value = "IND_NAME_WIDTH")
    private int indNameWidth;

    @Field(value = "IND_NAME_LABEL")
    @JsonProperty(value = "IND_NAME_LABEL")
    private String indNameLabel;

    @Field(value = "IND_NAME_SHOW")
    @JsonProperty(value = "IND_NAME_SHOW")
    private int indNameShow;

    @Field(value = "IND_UNIT_WIDTH")
    @JsonProperty(value = "IND_UNIT_WIDTH")
    private int indUnitWidth;

    @Field(value = "IND_UNIT_LABEL")
    @JsonProperty(value = "IND_UNIT_LABEL")
    private String indUnitLabel;

    @Field(value = "IND_UNIT_SHOW")
    @JsonProperty(value = "IND_UNIT_SHOW")
    private int indUnitShow;

    @Field(value = "DOC_ID")
    @JsonProperty(value = "DOC_ID")
    private int docId;

    @Field(value = "IS_PUBLIC")
    @JsonProperty(value = "IS_PUBLIC")
    private int isPublic;

    @Field(value = "COL_INDEX_SHOW")
    @JsonProperty(value = "COL_INDEX_SHOW")
    private int colIndexShow;

    @Field(value = "PUBLIC_DATE")
    @JsonProperty(value = "PUBLIC_DATE")
    private Date publicDate;

    @Field(value = "TENANT_ID")
    @JsonProperty(value = "TENANT_ID")
    private long tenantId;

    @Field(value = "OBJ_CODE")
    @JsonProperty(value = "OBJ_CODE")
    private String objCode;

    @Field(value = "ORG_LEVEL")
    @JsonProperty(value = "ORG_LEVEL")
    private String orgLevel;

    @Field(value = "ALLOW_ADD_IND")
    @JsonProperty(value = "ALLOW_ADD_IND")
    private int allowAddInd;

    @Field(value = "ORIGIN_TENANT")
    @JsonProperty(value = "ORIGIN_TENANT")
    private int originTenant;

    @Field(value = "EFORM_ID")
    @JsonProperty(value = "EFORM_ID")
    private int eFormId;

    @Field(value = "SYNC_TYPE")
    @JsonProperty(value = "SYNC_TYPE")
    private int syncType;

    @Field(value = "FORM_CODE")
    @JsonProperty(value = "FORM_CODE")
    private String formCode;

    @Field(value = "SUM_ORG_TYPE")
    @JsonProperty(value = "SUM_ORG_TYPE")
    private String sumOrgType;

    @Field(value = "REF_ID")
    @JsonProperty(value = "REF_ID")
    private int refId;

    @Field(value = "AUTOSUM_ORG_TYPE")
    @JsonProperty(value = "AUTOSUM_ORG_TYPE")
    private String autoSumOrgType;

    @Field(value = "REF_OBJ_ID")
    @JsonProperty(value = "REF_OBJ_ID")
    private int refObjId;

    @Field(value = "SYNC_DATE")
    @JsonProperty(value = "SYNC_DATE")
    private Date syncDate;

    @Field(value = "ORG_CODE")
    @JsonProperty(value = "ORG_CODE")
    private String orgCode;

    @Field(value = "SHOW_IN_ASSIGN_RPT")
    @JsonProperty(value = "SHOW_IN_ASSIGN_RPT")
    private int showInAssignRpt;

    @Field(value = "VERSION")
    @JsonProperty(value = "VERSION")
    private String version;

    @Field(value = "NULL_TO_0")
    @JsonProperty(value = "NULL_TO_0")
    private int nullTo0;

    @Field(value = "IS_DVC")
    @JsonProperty(value = "IS_DVC")
    private int isDvc;

    @Field(value = "INPUT_AND_SUM_ORG_TYPE")
    @JsonProperty(value = "INPUT_AND_SUM_ORG_TYPE")
    private String inputAndSumOrgType;

    @Field(value = "OWN_ORG")
    @JsonProperty(value = "OWN_ORG")
    private String ownOrg;

    @Field(value = "OWN_TENANT_ID")
    @JsonProperty(value = "OWN_TENANT_ID")
    private String ownTenantId;

    @Field(value = "HTML_HEADER")
    @JsonProperty(value = "HTML_HEADER")
    private String htmlHeader;

    @Field(value = "IS_LINK_OBJ")
    @JsonProperty(value = "IS_LINK_OBJ")
    private int isLinkObj;

    @Field(value = "REPORT_TYPE")
    @JsonProperty(value = "REPORT_TYPE")
    private String reportType;

    @Field(value = "FOR_QBH")
    @JsonProperty(value = "FOR_QBH")
    private String forQBH;

    @Field(value = "FOR_V3")
    @JsonProperty(value = "FOR_V3")
    private String forV3;

    @Field(value = "ORG_HOST_ID")
    @JsonProperty(value = "ORG_HOST_ID")
    private String orgHostId;

    @Field(value = "NOTE")
    @JsonProperty(value = "NOTE")
    private int note;

    @Field(value = "IS_CONNECTED")
    @JsonProperty(value = "IS_CONNECTED")
    private String isConnected;


//    @Field(value = "PARENT_NAME")
//    @JsonProperty(value = "PARENT_NAME")
//    private String parentName;
//
//    @Field(value = "OBJ_TYPE_NAME")
//    @JsonProperty(value = "OBJ_TYPE_NAME")
//    private String objTypeName;
//
//    @Field(value = "SUBMIT_TYPE_NAME")
//    @JsonProperty(value = "SUBMIT_TYPE_NAME")
//    private String submitTypeName;
//
//    @Field(value = "PROGRAM_CODE")
//    @JsonProperty(value = "PROGRAM_CODE")
//    private String programCode;
//
//    @Field(value = "PROGRAM_NAME")
//    @JsonProperty(value = "PROGRAM_NAME")
//    private String programName;
//
//    @Field(value = "TENANT_NAME")
//    @JsonProperty(value = "TENANT_NAME")
//    private String tenantName;
}
