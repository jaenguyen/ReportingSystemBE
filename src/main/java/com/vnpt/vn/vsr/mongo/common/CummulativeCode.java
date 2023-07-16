package com.vnpt.vn.vsr.mongo.common;

public enum CummulativeCode {

    PRE(1),
    INC_M(2),
    INC_Q(3),
    INC_CQ(4),
    MN_INC(5),
    INCMN_PRE(6),
    YPRE(7),
    MPRE(8),
    PQY(9),
    INC_D(10),
    INC_CD(11),
    EXTM(12),
    EXM(13);

    private final int value;

    private CummulativeCode(int value) {
        this.value = value;
    }

    public int getInteger() {
        return value;
    }

    public String getString() {
        return valueOf(value);
    }

    public static CummulativeCode getValue(String text) {
        if (text.contains("PRE")) return PRE;
        else if (text.contains("INC_M")) return INC_M;
        else if (text.contains("INC_Q")) return INC_Q;
        else if (text.contains("INC_CQ")) return INC_CQ;
        else if (text.contains("MN_INC")) return MN_INC;
        else if (text.contains("INCMN_PRE")) return INCMN_PRE;
        else if (text.contains("YPRE")) return YPRE;
        else if (text.contains("12MPRE")) return MPRE;
        else if (text.contains("PQY")) return PQY;
        else if (text.contains("INC_D")) return INC_D;
        else if (text.contains("INC_CD")) return INC_CD;
        else if (text.contains("EXTM")) return EXTM;
        else if (text.contains("EXM")) return EXM;
        else return null;
    }

    public String valueOf(int value) {
        switch (value) {
            case -1:
                return "ANY";
            case 1:
                return "PRE";
            case 2:
                return "INC_M";
            case 3:
                return "INC_Q";
            case 4:
                return "INC_CQ";
            case 5:
                return "MN_INC";
            case 6:
                return "INCMN_PRE";
            case 7:
                return "YPRE";
            case 8:
                return "12MPRE";
            case 9:
                return "PQY";
            case 10:
                return "INC_D";
            case 11:
                return "INC_CD";
            case 12:
                return "EXTM";
            case 13:
                return "EXM";
        }
        return "UNKNOWN";
    }
}

