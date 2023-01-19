package com.example.gamewebums.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberVo implements Serializable {

    private String nickName;
    private String email;
    private String passWord;
    private String inviteCode;
    private Integer Level;
}
