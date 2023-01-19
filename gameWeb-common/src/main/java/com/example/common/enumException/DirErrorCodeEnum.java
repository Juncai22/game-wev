package com.example.common.enumException;

import com.example.common.utils.R;

public enum DirErrorCodeEnum {


    SearchError(13000, "service添加服务出现了错误"),

    ServerToError(10000, "发生未知错误"),

    //用户错误点
    UserSameError(12001, "用户名重复，请重新输入"),

    UserRegError(12002, "用户注册发生未知错误"),

    UserRegNameEmityError(12003, "用户名不能为空"),

    UserRegNameToLongError(12004, "用户名长度在1-8位"),

    UserRegPassWordEmityError(12005, "密码不能为空"),

    UserRegPassWordNotRightError(12006, "密码长度为6-18位"),

    UserRegEmailEmityError(12007, "邮箱不能为空"),

    UserNotHaveError(12008, "用户暂未注册"),

    UserNotInError(12009, "用户名或密码错误"),

    UserCodeNotRight(12010, "验证码错误");


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
