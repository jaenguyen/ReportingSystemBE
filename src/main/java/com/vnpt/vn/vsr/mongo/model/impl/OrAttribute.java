package com.vnpt.vn.vsr.mongo.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vnpt.vn.vsr.mongo.model.AbstractBaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


//shifl+alt+u => camelcase
// ctr+alt+l => format
@Data
@Document(collection = "OR_ATTRIBUTE")
@NoArgsConstructor
public class OrAttribute extends AbstractBaseEntity {

    @Transient
    public static final String SEQUENCE_NAME = "OR_ATTRIBUTE_SEQ";
    
    @Field(value = "ATTR_ID")
    private long attrId;
    @Field(value = "ATTR_CODE")
    private String attrCode;
    @Field(value = "ATTR_NAME")
    private String attrName;
    @Field(value = "ATTR_TYPE")
    private long attrType;
    @Field(value = "SUM_TYPE")
    private long sumType;
    @Field(value = "ATTR_CONSTRAINT")
    private String attrConstraint;
    @Field(value = "CTL_WIDTH")
    private long ctlWidth;
    @Field(value = "CTL_MAXLENGTH")
    private long ctlMaxlength;
    @Field(value = "CTL_ALIGN")
    private long ctlAlign;
    @Field(value = "CTL_DIRECTION")
    private long ctlDirection;
    @Field(value = "NOTE")
    private String note;
    @Field(value = "PARENT_ID")
    private String parentId;
    @Field(value = "FORMULA")
    private String formula;
    @Field(value = "PRIORITY")
    private long priority;
    @Field(value = "ROW_PRIORITY")
    private long rowPriority;
    @Field(value = "PERCENT_TYPE")
    private long percentType;
    @Field(value = "ATTR_WARNING")
    private String attrWarning;
    @Field(value = "CTL_ID")
    private long ctlId;
    @Field(value = "OBJ_ID")
    private String objId;
    @Field(value = "FLD_CODE")
    private String fldCode;
    @Field(value = "ATTR_INDEX2")
    private long attrIndex2;
    @Field(value = "ATTR_INDEX")
    private String attrIndex;
    @Field(value = "TENANT_ID")
    private String tenantId;
    @Field(value = "ALLOW_GET_IND")
    private long allowGetInd;
    @Field(value = "FIELD_TYPE_ID")
    private long fieldTypeId;
    @Field(value = "COL_WIDTH")
    private long colWidth;
    @Field(value = "SHORT_NAME")
    private String shortName;
    @Field(value = "FONT_SIZE")
    private long fontSize;
    @Field(value = "ALIGN")
    private long align;
    @Field(value = "FONT_TYPE")
    private long fontType;
    @Field(value = "IS_DEFAULT")
    private long isDefault;
    @Field(value = "IS_HIDDEN")
    private long isHidden;
    @Field(value = "IDX")
    private long idx;
    @Field(value = "ATTR_CATEGORY")
    private long attrCategory;

    @Field(value = "FORMULA_BASIC")
    private String formulaBasic;

    @Field(value = "COMMAND")
    private String command;
    @Field(value = "TENANT_NAME")
    @JsonProperty(value = "TENANT_NAME")
    private String tenantName;
    private long rootId;

}
