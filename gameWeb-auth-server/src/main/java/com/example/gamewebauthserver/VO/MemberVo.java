package com.example.gamewebauthserver.VO;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class MemberVo implements Serializable {

    @NotEmpty(message = "用户名不能为空")
    @Length(min = 0,max = 8,message = "用户名长度不能大于8")
    private String nickName;

    @NotEmpty(message = "email不能为空")
    private String email;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6,max = 18,message = "密码是6-18位")
    private String passWord;

    private String inviteCode;


}
