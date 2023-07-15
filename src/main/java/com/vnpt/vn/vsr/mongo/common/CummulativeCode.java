package com.vnpt.vn.vsr.mongo.common;

public class CummulativeCode {

    public static String getString(int value) {
        switch (value) {
            case 1:
                return "EXM";
            case 2:
                return "INC_M";
            case 3:
                return "INC_Q";
            case 4:
                return "INC_CQ";
            case 5:
                return "MN_INC";
            case 6:
                return "PQY";
            case 7:
                return "YPRE";
            case 8:
                return "INCMN_PRE";
            case 9:
                return "12MPRE";
            case 10:
                return "INC_D";
            case 11:
                return "INC_CD";
            case 12:
                return "EXTM";
            case 13:
                return "PRE";
            default:
                return null;
        }
    }

    public static int isCummulativeCode(String formula) {
        if (formula.contains("EXM")) return 1;
        else if (formula.contains("INC_M")) return 2;
        else if (formula.contains("INC_Q")) return 3;
        else if (formula.contains("INC_CQ")) return 4;
        else if (formula.contains("MN_INC")) return 5;
        else if (formula.contains("PQY")) return 6;
        else if (formula.contains("YPRE")) return 7;
        else if (formula.contains("INCMN_PRE")) return 8;
        else if (formula.contains("12MPRE")) return 9;
        else if (formula.contains("INC_D")) return 10;
        else if (formula.contains("INC_CD")) return 11;
        else if (formula.contains("EXTM")) return 12;
        else if (formula.contains("PRE")) return 13;
        else return 0;
    }
}

