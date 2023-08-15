package com.vnpt.vn.vsr.mongo.model.impl;

import com.vnpt.vn.vsr.mongo.model.AbstractBaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Date;


//shifl+alt+u => camelcase
// ctr+alt+l => format
@Data
@Document(collection = "OR_DATAROW")
@NoArgsConstructor
public class OrDataRow extends AbstractBaseEntity {
    @Transient
    public static final String SEQUENCE_NAME = "OR_DATAROW_SEQ";

    @Field(value = "DATA_ID")
    private long dataId;
    @Field(value = "ORG_ID")
    private String orgId;

    @Field(value = "IND_CODE")
    private String indCode;

    @Field(value = "IND_NAME")
    private String indName;

    @Field(value = "COMMAND")
    private String command;

    @Field(value = "IND_UNIT")
    private String indUnit;
    @Field(value = "ATTR_FORMULA_FORBIT")
    private String attrFormulaForbit;

    @Field(value = "GROUP_ID")
    private String groupId;

    @Field(value = "IDX")
    private String idx;

    @Field(value = "IND_INDEX")
    private String indIndex;

    @Field(value = "IND_TYPE")
    private String indType;

    @Field(value = "IS_SUB_IND")
    private String isSubInd;

    @Field(value = "IS_SUMARY")
    private String isSumary;

    @Field(value = "PARENT_FIELD_ID")
    private String parentFieldId;

    @Field(value = "PARENT_IND_ID")
    private String parentIndId;

    @Field(value = "TIME_ID")
    private String timeId;
    @Field(value = "IND_ID")
    private String indId;
    @Field(value = "FD01")
    private String FD01;
    @Field(value = "FD02")
    private String FD02;
    @Field(value = "FD03")
    private String FD03;
    @Field(value = "FD04")
    private String FD04;
    @Field(value = "FD05")
    private String FD05;
    @Field(value = "OBJ_ID")
    private String objId;
    @Field(value = "FN21")
    private String FN21;
    @Field(value = "FN22")
    private String FN22;
    @Field(value = "FN23")
    private String FN23;
    @Field(value = "FN24")
    private String FN24;
    @Field(value = "FN25")
    private String FN25;
    @Field(value = "FN26")
    private String FN26;
    @Field(value = "FN27")
    private String FN27;
    @Field(value = "FN28")
    private String FN28;
    @Field(value = "FN29")
    private String FN29;
    @Field(value = "FN30")
    private String FN30;
    @Field(value = "FN31")
    private String FN31;
    @Field(value = "FN32")
    private String FN32;
    @Field(value = "FN33")
    private String FN33;
    @Field(value = "FN34")
    private String FN34;
    @Field(value = "FN35")
    private String FN35;
    @Field(value = "FN36")
    private String FN36;
    @Field(value = "FN37")
    private String FN37;
    @Field(value = "FN38")
    private String FN38;
    @Field(value = "FN39")
    private String FN39;
    @Field(value = "FN40")
    private String FN40;
    @Field(value = "TENANT_ID")
    private String tenantId;
    @Field(value = "FIELD_ID")
    private String fieldId;
    @Field(value = "FIELD_UNIT")
    private String fieldUnit;
    @Field(value = "FN01")
    private String FN01;
    @Field(value = "FN02")
    private String FN02;
    @Field(value = "FN03")
    private String FN03;
    @Field(value = "FN04")
    private String FN04;
    @Field(value = "FN05")
    private String FN05;
    @Field(value = "FN06")
    private String FN06;
    @Field(value = "FN07")
    private String FN07;
    @Field(value = "FN08")
    private String FN08;
    @Field(value = "FN10")
    private String FN10;
    @Field(value = "FN11")
    private String FN11;
    @Field(value = "FN12")
    private String FN12;
    @Field(value = "FN13")
    private String FN13;
    @Field(value = "FN14")
    private String FN14;
    @Field(value = "FN15")
    private String FN15;
    @Field(value = "FN16")
    private String FN16;
    @Field(value = "FN17")
    private String FN17;
    @Field(value = "FN18")
    private String FN18;
    @Field(value = "FN19")
    private String FN19;
    @Field(value = "FN20")
    private String FN20;
    @Field(value = "FN09")
    private String FN09;
    @Field(value = "FN41")
    private String FN41;
    @Field(value = "FN42")
    private String FN42;
    @Field(value = "FN43")
    private String FN43;
    @Field(value = "FN44")
    private String FN44;
    @Field(value = "FN45")
    private String FN45;
    @Field(value = "FN46")
    private String FN46;
    @Field(value = "FN47")
    private String FN47;
    @Field(value = "FN48")
    private String FN48;
    @Field(value = "FN49")
    private String FN49;
    @Field(value = "FN50")
    private String FN50;
    @Field(value = "FN51")
    private String FN51;
    @Field(value = "FN52")
    private String FN52;
    @Field(value = "FN53")
    private String FN53;
    @Field(value = "FN54")
    private String FN54;
    @Field(value = "FN55")
    private String FN55;
    @Field(value = "FN56")
    private String FN56;
    @Field(value = "FN57")
    private String FN57;
    @Field(value = "FN58")
    private String FN58;
    @Field(value = "FN59")
    private String FN59;
    @Field(value = "FN60")
    private String FN60;
    @Field(value = "FN61")
    private String FN61;
    @Field(value = "FN62")
    private String FN62;
    @Field(value = "FN63")
    private String FN63;
    @Field(value = "FN64")
    private String FN64;
    @Field(value = "FN65")
    private String FN65;
    @Field(value = "FN66")
    private String FN66;
    @Field(value = "FN67")
    private String FN67;
    @Field(value = "FN68")
    private String FN68;
    @Field(value = "FN69")
    private String FN69;
    @Field(value = "FN70")
    private String FN70;
    @Field(value = "FN71")
    private String FN71;
    @Field(value = "FN72")
    private String FN72;
    @Field(value = "FN73")
    private String FN73;
    @Field(value = "FN74")
    private String FN74;
    @Field(value = "FN75")
    private String FN75;
    @Field(value = "FN76")
    private String FN76;
    @Field(value = "FN77")
    private String FN77;
    @Field(value = "FN78")
    private String FN78;
    @Field(value = "FN79")
    private String FN79;
    @Field(value = "FN80")
    private String FN80;
    @Field(value = "FN81")
    private String FN81;
    @Field(value = "FN82")
    private String FN82;
    @Field(value = "FN83")
    private String FN83;
    @Field(value = "FN84")
    private String FN84;
    @Field(value = "FN85")
    private String FN85;
    @Field(value = "FN86")
    private String FN86;
    @Field(value = "FN87")
    private String FN87;
    @Field(value = "FN88")
    private String FN88;
    @Field(value = "FN89")
    private String FN89;
    @Field(value = "FN90")
    private String FN90;
    @Field(value = "FN91")
    private String FN91;
    @Field(value = "FN92")
    private String FN92;
    @Field(value = "FN93")
    private String FN93;
    @Field(value = "FN94")
    private String FN94;
    @Field(value = "FN95")
    private String FN95;
    @Field(value = "FN96")
    private String FN96;
    @Field(value = "FN97")
    private String FN97;
    @Field(value = "FN98")
    private String FN98;
    @Field(value = "FN99")
    private String FN99;
    @Field(value = "FN100")
    private String FN100;
    @Field(value = "FC43")
    private String FC43;
    @Field(value = "FC44")
    private String FC44;
    @Field(value = "FC45")
    private String FC45;
    @Field(value = "FC46")
    private String FC46;
    @Field(value = "FC47")
    private String FC47;
    @Field(value = "FC48")
    private String FC48;
    @Field(value = "FC49")
    private String FC49;
    @Field(value = "FC50")
    private String FC50;
    @Field(value = "FC51")
    private String FC51;
    @Field(value = "FC52")
    private String FC52;
    @Field(value = "FC53")
    private String FC53;
    @Field(value = "FC54")
    private String FC54;
    @Field(value = "FC55")
    private String FC55;
    @Field(value = "FC56")
    private String FC56;
    @Field(value = "FC57")
    private String FC57;
    @Field(value = "FC58")
    private String FC58;
    @Field(value = "FC59")
    private String FC59;
    @Field(value = "FC60")
    private String FC60;
    @Field(value = "FC61")
    private String FC61;
    @Field(value = "FC62")
    private String FC62;
    @Field(value = "FC63")
    private String FC63;
    @Field(value = "FC64")
    private String FC64;
    @Field(value = "FC65")
    private String FC65;
    @Field(value = "FC66")
    private String FC66;
    @Field(value = "FC67")
    private String FC67;
    @Field(value = "FC68")
    private String FC68;
    @Field(value = "FC69")
    private String FC69;
    @Field(value = "FC70")
    private String FC70;
    @Field(value = "FC71")
    private String FC71;
    @Field(value = "FC72")
    private String FC72;
    @Field(value = "FC73")
    private String FC73;
    @Field(value = "FC74")
    private String FC74;
    @Field(value = "FC75")
    private String FC75;
    @Field(value = "FC76")
    private String FC76;
    @Field(value = "FC77")
    private String FC77;
    @Field(value = "FC78")
    private String FC78;
    @Field(value = "FC79")
    private String FC79;
    @Field(value = "FC80")
    private String FC80;
    @Field(value = "FC81")
    private String FC81;
    @Field(value = "FC82")
    private String FC82;
    @Field(value = "FC83")
    private String FC83;
    @Field(value = "FC84")
    private String FC84;
    @Field(value = "FC85")
    private String FC85;
    @Field(value = "FC86")
    private String FC86;
    @Field(value = "FC87")
    private String FC87;
    @Field(value = "FC88")
    private String FC88;
    @Field(value = "FC89")
    private String FC89;
    @Field(value = "FC90")
    private String FC90;
    @Field(value = "FC91")
    private String FC91;
    @Field(value = "FC92")
    private String FC92;
    @Field(value = "FC93")
    private String FC93;
    @Field(value = "FC94")
    private String FC94;
    @Field(value = "FC95")
    private String FC95;
    @Field(value = "FC96")
    private String FC96;
    @Field(value = "FC97")
    private String FC97;
    @Field(value = "FC98")
    private String FC98;
    @Field(value = "FC99")
    private String FC99;
    @Field(value = "FC100")
    private String FC100;
    @Field(value = "FC101")
    private String FC101;
    @Field(value = "FC102")
    private String FC102;
    @Field(value = "FC103")
    private String FC103;
    @Field(value = "FC104")
    private String FC104;
    @Field(value = "FC105")
    private String FC105;
    @Field(value = "FC106")
    private String FC106;
    @Field(value = "FC107")
    private String FC107;
    @Field(value = "FC108")
    private String FC108;
    @Field(value = "FC109")
    private String FC109;
    @Field(value = "FC110")
    private String FC110;
    @Field(value = "FC111")
    private String FC111;
    @Field(value = "FC112")
    private String FC112;
    @Field(value = "FC113")
    private String FC113;
    @Field(value = "FC114")
    private String FC114;
    @Field(value = "FC115")
    private String FC115;
    @Field(value = "FC116")
    private String FC116;
    @Field(value = "FC117")
    private String FC117;
    @Field(value = "FC118")
    private String FC118;
    @Field(value = "FC119")
    private String FC119;
    @Field(value = "FC120")
    private String FC120;
    @Field(value = "FN101")
    private String FN101;
    @Field(value = "FN102")
    private String FN102;
    @Field(value = "FN103")
    private String FN103;
    @Field(value = "FN104")
    private String FN104;
    @Field(value = "FN105")
    private String FN105;
    @Field(value = "FN106")
    private String FN106;
    @Field(value = "FN107")
    private String FN107;
    @Field(value = "FN108")
    private String FN108;
    @Field(value = "FN109")
    private String FN109;
    @Field(value = "FN110")
    private String FN110;
    @Field(value = "FN111")
    private String FN111;
    @Field(value = "FN112")
    private String FN112;
    @Field(value = "FN113")
    private String FN113;
    @Field(value = "FN114")
    private String FN114;
    @Field(value = "FN115")
    private String FN115;
    @Field(value = "FN116")
    private String FN116;
    @Field(value = "FN117")
    private String FN117;
    @Field(value = "FN118")
    private String FN118;
    @Field(value = "FN119")
    private String FN119;
    @Field(value = "FN120")
    private String FN120;
    @Field(value = "FC01")
    private String FC01;
    @Field(value = "FC17_X")
    private String FC17_X;
    @Field(value = "FC23_X")
    private String FC23_X;
    @Field(value = "FC02")
    private String FC02;
    @Field(value = "FC03")
    private String FC03;
    @Field(value = "FC04")
    private String FC04;
    @Field(value = "FC05")
    private String FC05;
    @Field(value = "FC06")
    private String FC06;
    @Field(value = "FC07")
    private String FC07;
    @Field(value = "FC08")
    private String FC08;
    @Field(value = "FC09")
    private String FC09;
    @Field(value = "FC10")
    private String FC10;
    @Field(value = "FC11")
    private String FC11;
    @Field(value = "FC12")
    private String FC12;
    @Field(value = "FC13")
    private String FC13;
    @Field(value = "FC14")
    private String FC14;
    @Field(value = "FC15")
    private String FC15;
    @Field(value = "FC16")
    private String FC16;
    @Field(value = "FC17")
    private String FC17;
    @Field(value = "FC18")
    private String FC18;
    @Field(value = "FC19")
    private String FC19;
    @Field(value = "FC20")
    private String FC20;
    @Field(value = "FC21")
    private String FC21;
    @Field(value = "FC22_1")
    private String FC22_1;
    @Field(value = "FC22")
    private String FC22;
    @Field(value = "FC23")
    private String FC23;
    @Field(value = "FC24")
    private String FC24;
    @Field(value = "FC25")
    private String FC25;
    @Field(value = "FC26")
    private String FC26;
    @Field(value = "FC27")
    private String FC27;
    @Field(value = "FC28")
    private String FC28;
    @Field(value = "FC29")
    private String FC29;
    @Field(value = "FC30")
    private String FC30;
    @Field(value = "FC31")
    private String FC31;
    @Field(value = "FC32")
    private String FC32;
    @Field(value = "FC33")
    private String FC33;
    @Field(value = "FC34")
    private String FC34;
    @Field(value = "FC35")
    private String FC35;
    @Field(value = "FC36")
    private String FC36;
    @Field(value = "FC37")
    private String FC37;
    @Field(value = "FC38")
    private String FC38;
    @Field(value = "FC39")
    private String FC39;
    @Field(value = "FC40")
    private String FC40;
    @Field(value = "FC41")
    private String FC41;
    @Field(value = "FC42")
    private String FC42;
    @Field(value = "VERSION_DATA")
    private String versionData;
    @Field(value = "VERSION_LAST")
    private String versionLast;
    @Field(value = "FN121")
    private String FN121;
    @Field(value = "FN122")
    private String FN122;
    @Field(value = "FN123")
    private String FN123;
    @Field(value = "FN124")
    private String FN124;
    @Field(value = "FN125")
    private String FN125;
    @Field(value = "FN126")
    private String FN126;
    @Field(value = "FN127")
    private String FN127;
    @Field(value = "FN128")
    private String FN128;
    @Field(value = "FN129")
    private String FN129;
    @Field(value = "FN130")
    private String FN130;
    @Field(value = "FN131")
    private String FN131;
    @Field(value = "FN132")
    private String FN132;
    @Field(value = "FN133")
    private String FN133;
    @Field(value = "FN134")
    private String FN134;
    @Field(value = "FN135")
    private String FN135;
    @Field(value = "FN136")
    private String FN136;
    @Field(value = "FN137")
    private String FN137;
    @Field(value = "FN138")
    private String FN138;
    @Field(value = "FN139")
    private String FN139;
    @Field(value = "FN140")
    private String FN140;
    @Field(value = "FN141")
    private String FN141;
    @Field(value = "FN142")
    private String FN142;
    @Field(value = "FN143")
    private String FN143;
    @Field(value = "FN144")
    private String FN144;
    @Field(value = "FN145")
    private String FN145;
    @Field(value = "FN146")
    private String FN146;
    @Field(value = "FN147")
    private String FN147;
    @Field(value = "FN148")
    private String FN148;
    @Field(value = "FN149")
    private String FN149;
    @Field(value = "FN150")
    private String FN150;
    @Field(value = "FN151")
    private String FN151;
    @Field(value = "FN152")
    private String FN152;
    @Field(value = "FN153")
    private String FN153;
    @Field(value = "FN154")
    private String FN154;
    @Field(value = "FN155")
    private String FN155;
    @Field(value = "FN156")
    private String FN156;
    @Field(value = "FN157")
    private String FN157;
    @Field(value = "FN158")
    private String FN158;
    @Field(value = "FN159")
    private String FN159;
    @Field(value = "FN160")
    private String FN160;
    @Field(value = "FN161")
    private String FN161;
    @Field(value = "FN162")
    private String FN162;
    @Field(value = "FN163")
    private String FN163;
    @Field(value = "FN164")
    private String FN164;
    @Field(value = "FN165")
    private String FN165;
    @Field(value = "FN166")
    private String FN166;
    @Field(value = "FN167")
    private String FN167;
    @Field(value = "FN168")
    private String FN168;
    @Field(value = "FN169")
    private String FN169;
    @Field(value = "FN170")
    private String FN170;
    @Field(value = "FN171")
    private String FN171;
    @Field(value = "FN172")
    private String FN172;
    @Field(value = "FN173")
    private String FN173;
    @Field(value = "FN174")
    private String FN174;
    @Field(value = "FN175")
    private String FN175;
    @Field(value = "FN176")
    private String FN176;
    @Field(value = "FN177")
    private String FN177;
    @Field(value = "FN178")
    private String FN178;
    @Field(value = "FN179")
    private String FN179;
    @Field(value = "FN180")
    private String FN180;
    @Field(value = "FN181")
    private String FN181;
    @Field(value = "FN182")
    private String FN182;
    @Field(value = "FN183")
    private String FN183;
    @Field(value = "FN184")
    private String FN184;
    @Field(value = "FN185")
    private String FN185;
    @Field(value = "FN186")
    private String FN186;
    @Field(value = "FN187")
    private String FN187;
    @Field(value = "FN188")
    private String FN188;
    @Field(value = "FN189")
    private String FN189;
    @Field(value = "FN190")
    private String FN190;
    @Field(value = "FN191")
    private String FN191;
    @Field(value = "FN192")
    private String FN192;
    @Field(value = "FN193")
    private String FN193;
    @Field(value = "FN194")
    private String FN194;
    @Field(value = "FN195")
    private String FN195;
    @Field(value = "FN196")
    private String FN196;
    @Field(value = "FN197")
    private String FN197;
    @Field(value = "FN198")
    private String FN198;
    @Field(value = "FN199")
    private String FN199;
    @Field(value = "FN200")
    private String FN200;
    @Field(value = "FN201")
    private String FN201;
    @Field(value = "FN202")
    private String FN202;
    @Field(value = "FN203")
    private String FN203;
    @Field(value = "FN204")
    private String FN204;
    @Field(value = "FN205")
    private String FN205;
    @Field(value = "FN206")
    private String FN206;
    @Field(value = "FN207")
    private String FN207;
    @Field(value = "FN208")
    private String FN208;
    @Field(value = "FN209")
    private String FN209;
    @Field(value = "FN210")
    private String FN210;
    @Field(value = "FN211")
    private String FN211;
    @Field(value = "FN212")
    private String FN212;
    @Field(value = "FN213")
    private String FN213;
    @Field(value = "FN214")
    private String FN214;
    @Field(value = "FN215")
    private String FN215;
    @Field(value = "FN216")
    private String FN216;
    @Field(value = "FN217")
    private String FN217;
    @Field(value = "FN218")
    private String FN218;
    @Field(value = "FN219")
    private String FN219;
    @Field(value = "FN220")
    private String FN220;
    @Field(value = "FN221")
    private String FN221;
    @Field(value = "FN222")
    private String FN222;
    @Field(value = "FN223")
    private String FN223;
    @Field(value = "FN224")
    private String FN224;
    @Field(value = "FN225")
    private String FN225;
    @Field(value = "FN226")
    private String FN226;
    @Field(value = "FN227")
    private String FN227;
    @Field(value = "FN228")
    private String FN228;
    @Field(value = "FN229")
    private String FN229;
    @Field(value = "FN230")
    private String FN230;
    @Field(value = "FN231")
    private String FN231;
    @Field(value = "FN232")
    private String FN232;
    @Field(value = "FN233")
    private String FN233;
    @Field(value = "FN234")
    private String FN234;
    @Field(value = "FN235")
    private String FN235;
    @Field(value = "FN236")
    private String FN236;
    @Field(value = "FN237")
    private String FN237;
    @Field(value = "FN238")
    private String FN238;
    @Field(value = "FN239")
    private String FN239;
    @Field(value = "FN240")
    private String FN240;
    @Field(value = "FN241")
    private String FN241;
    @Field(value = "FN242")
    private String FN242;
    @Field(value = "FN243")
    private String FN243;
    @Field(value = "FN244")
    private String FN244;
    @Field(value = "FN245")
    private String FN245;
    @Field(value = "FN246")
    private String FN246;
    @Field(value = "FN247")
    private String FN247;
    @Field(value = "FN248")
    private String FN248;
    @Field(value = "FN249")
    private String FN249;
    @Field(value = "FN250")
    private String FN250;
    @Field(value = "FN251")
    private String FN251;
    @Field(value = "FN252")
    private String FN252;
    @Field(value = "FN253")
    private String FN253;
    @Field(value = "FN254")
    private String FN254;
    @Field(value = "FN255")
    private String FN255;
    @Field(value = "FN256")
    private String FN256;
    @Field(value = "FN257")
    private String FN257;
    @Field(value = "FN258")
    private String FN258;
    @Field(value = "FN259")
    private String FN259;
    @Field(value = "FN260")
    private String FN260;
    @Field(value = "FN261")
    private String FN261;
    @Field(value = "FN262")
    private String FN262;
    @Field(value = "FN263")
    private String FN263;
    @Field(value = "FN264")
    private String FN264;
    @Field(value = "FN265")
    private String FN265;
    @Field(value = "FN266")
    private String FN266;
    @Field(value = "FN267")
    private String FN267;
    @Field(value = "FN268")
    private String FN268;
    @Field(value = "FN269")
    private String FN269;
    @Field(value = "FN270")
    private String FN270;
    @Field(value = "FN271")
    private String FN271;
    @Field(value = "FN272")
    private String FN272;
    @Field(value = "FN273")
    private String FN273;
    @Field(value = "FN274")
    private String FN274;
    @Field(value = "FN275")
    private String FN275;
    @Field(value = "FN276")
    private String FN276;
    @Field(value = "FN277")
    private String FN277;
    @Field(value = "FN278")
    private String FN278;
    @Field(value = "FN279")
    private String FN279;
    @Field(value = "FN280")
    private String FN280;
    @Field(value = "FN281")
    private String FN281;
    @Field(value = "FN282")
    private String FN282;
    @Field(value = "FN283")
    private String FN283;
    @Field(value = "FN284")
    private String FN284;
    @Field(value = "FN285")
    private String FN285;
    @Field(value = "FN286")
    private String FN286;
    @Field(value = "FN287")
    private String FN287;
    @Field(value = "FN288")
    private String FN288;
    @Field(value = "FN289")
    private String FN289;
    @Field(value = "FN290")
    private String FN290;
    @Field(value = "FN291")
    private String FN291;
    @Field(value = "FN292")
    private String FN292;
    @Field(value = "FN293")
    private String FN293;
    @Field(value = "FN294")
    private String FN294;
    @Field(value = "FN295")
    private String FN295;
    @Field(value = "FN296")
    private String FN296;
    @Field(value = "FN297")
    private String FN297;
    @Field(value = "FN298")
    private String FN298;
    @Field(value = "FN299")
    private String FN299;
    @Field(value = "FN300")
    private String FN300;

    public void setField(String fld_code, String value) throws Exception {
        try {
            java.lang.reflect.Field field = this.getClass().getDeclaredField(fld_code.trim());
            field.setAccessible(true);
            field.set(this, value);
        } catch (Exception e) {
        }
    }

    public String getField(String fld_code) throws Exception {
        try {
            java.lang.reflect.Field field = this.getClass().getDeclaredField(fld_code);
            field.setAccessible(true);
            return (String) field.get(this);
        } catch (Exception e) {
        }
        return null;
    }
}
