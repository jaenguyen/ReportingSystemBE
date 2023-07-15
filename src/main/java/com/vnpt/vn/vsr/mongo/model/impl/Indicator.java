package com.vnpt.vn.vsr.mongo.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vnpt.vn.vsr.mongo.model.AbstractBaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "OR_INDICATOR")
@NoArgsConstructor
public class Indicator extends AbstractBaseEntity {

    @Transient
    public static final String SEQUENCE_NAME = "OR_INDICATOR_SEQ";

    @Field(value = "IND_ID")
    private long indId;
    @Field(value = "IND_CODE")
    private String indCode;
    @Field(value = "IND_TYPE")
    private long indType;
    @Field(value = "IND_UNIT")
    private String indUnit;
    @Field(value = "IS_SUMARY")
    private long isSumary;
    @Field(value = "IS_SHOWCOUNT")
    private long isShowcount;
    @Field(value = "IS_MULTI")
    private long isMulti;
    @Field(value = "PARENT_ID")
    private long parentId;
    @Field(value = "FORMULA")
    private String formula;
    @Field(value = "NOTE")
    private String note;
    @Field(value = "OBJ_ID")
    private String objId;
    @Field(value = "WIDTH")
    private long width;
    @Field(value = "IND_INDEX")
    private String indIndex;
    @Field(value = "IS_FOOTER")
    private long isFooter;
    @Field(value = "IDX")
    private long idx;
    @Field(value = "TENANT_ID")
    private String tenantId;
    @Field(value = "FIELD_ID")
    private long fieldId;
    @Field(value = "STATUS")
    private long status;
    @Field(value = "ORG_ID")
    private long orgId;
    @Field(value = "IND_NAME")
    private String indName;
    @Field(value = "ALLOW_ADD_ROW")
    private long allowAddRow;
    @Field(value = "PARENT_FIELD_ID")
    private long parentFieldId;
    @Field(value = "CUMMULATIVE")
    private long cummulative;
    @Field(value = "CUMM_IND_TYPE")
    private long cummIndType;
    @Field(value = "PARENT_FIELD_IDS")
    private String parentFieldIds;
    @Field(value = "ATTR_FORMULA_FORBIT")
    private long attrFormulaForbit;
    @Field(value = "IS_TPL")
    private long isTpl;
    @Field(value = "IS_OFFICAL")
    private long isOffical;


//    @Field(value = "ORG_TYPE")
//    private long orgType;
//    @Field(value = "ORG_CODE")
//    private String orgCode;
//    @Field(value = "ORG_NAME")
//    private String orgName;
//    @Field(value = "ORG_TYPE_NAME")
//    private String orgTypeName;
//    @Field(value = "TENANT_NAME")
//    @JsonProperty(value = "TENANT_NAME")
//    private String tenantName;
}
