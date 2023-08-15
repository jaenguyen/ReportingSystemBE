package com.vnpt.vn.vsr.mongo.common;

public enum ComIndType {
    COM_IND_TYPE1(1, "Chỉ tiêu bình thường", "", "1", "Normal indicator"),
    COM_IND_TYPE2(2, "Không nhập số liệu", "", "", "Label indicator"),
    COM_IND_TYPE3(3,  "Tổng các chỉ tiêu con",  "sum", "3", "Sum from child indicators"),
    COM_IND_TYPE4(4, "TB cộng các chỉ tiêu con", "avg", "3", "Average of child indicator"),
    COM_IND_TYPE5(5, "Lấy max chỉ tiêu con", "max", "3", "Max among child indicators"),
    COM_IND_TYPE6(6, "Lấy min chỉ tiêu con", "min", "3", "Min among child indicators");
    // Tên đầy đủ
    private final int id;
    private final String name;
    private final String command;
    private final String groupId;
    private final String name_la;

    ComIndType(int id, String name, String command, String groupId, String name_la) {
        this.id = id;
        this.name = name;
        this.command = command;
        this.groupId = groupId;
        this.name_la = name_la;
    }

    public static ComIndType get(String id) {
        switch (id) {
            case "1":
                return COM_IND_TYPE1;
            case "2":
                return COM_IND_TYPE2;
            case "3":
                return COM_IND_TYPE3;
            case "4":
                return COM_IND_TYPE4;
            case "5":
                return COM_IND_TYPE5;
            case "6":
                return COM_IND_TYPE6;
            default:
                return null;
        }
    }

    public int getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public String getGroupId() {
        return groupId;
    }
}
