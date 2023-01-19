package com.example.common.utils;

import com.example.common.enumException.DirErrorCodeEnum;

public class DirErrorUtils {

    public String tochek(int check) {
        if (check == DirErrorCodeEnum.UserSameError.getCode())
            return DirErrorCodeEnum.UserSameError.getDescription();
        else if (check == DirErrorCodeEnum.UserRegError.getCode())
            return DirErrorCodeEnum.UserRegError.getDescription();
        else if (check == DirErrorCodeEnum.UserRegNameEmityError.getCode())
            return DirErrorCodeEnum.UserRegNameEmityError.getDescription();
        else if (check == DirErrorCodeEnum.UserRegPassWordEmityError.getCode())
            return DirErrorCodeEnum.UserRegPassWordEmityError.getDescription();
        else if (check == DirErrorCodeEnum.UserRegEmailEmityError.getCode())
            return DirErrorCodeEnum.UserRegEmailEmityError.getDescription();
        else if (check == DirErrorCodeEnum.UserRegNameToLongError.getCode())
            return DirErrorCodeEnum.UserRegNameToLongError.getDescription();
        else if (check == DirErrorCodeEnum.UserRegPassWordNotRightError.getCode())
            return DirErrorCodeEnum.UserRegPassWordNotRightError.getDescription();
        else if (check == DirErrorCodeEnum.UserNotHaveError.getCode())
            return DirErrorCodeEnum.UserNotHaveError.getDescription();
        else if (check == DirErrorCodeEnum.UserNotInError.getCode())
            return DirErrorCodeEnum.UserNotInError.getDescription();
        else if (check == DirErrorCodeEnum.UserCodeNotRight.getCode())
            return DirErrorCodeEnum.UserCodeNotRight.getDescription();

        return DirErrorCodeEnum.ServerToError.getDescription();
    }
}
