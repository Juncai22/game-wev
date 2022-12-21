package com.example.common.enumException;

public enum DirErrorCodeEnum {


    SearchError(13000, "service添加服务出现了错误"),

    //用户错误点
    UserSameError(12001, "用户名重复，请重新输入"),
    UserRegError(12002, "用户注册发生未知错误"),
    UserNotHaveError(12003, "用户暂未注册"),
    UserNotInError(12004, "用户名或密码错误");

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
