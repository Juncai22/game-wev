package com.example.common.enumException;

public enum DirErrorCodeEnum {


    SearchError(13000,"service添加服务出现了错误"),
    UserSameError(12001,"用户名重复，请重新输入");

    private final int code;

    private final String description;

    private DirErrorCodeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
