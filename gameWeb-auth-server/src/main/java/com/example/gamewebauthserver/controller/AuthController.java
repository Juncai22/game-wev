package com.example.gamewebauthserver.controller;

import com.example.common.utils.R;
import com.example.gamewebauthserver.VO.MemberVo;
import com.example.gamewebauthserver.feign.MemberFeign;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Resource
    MemberFeign memberFeign;

    @GetMapping("/login")
    public String login() {
        return "regAndLogin";
    }


    @GetMapping("/loginvf")
    public String loginvf(@RequestBody MemberVo memberVo, HttpSession session) {
        R login = memberFeign.login(memberVo);

        if (login.getCode() != 0) {
            session.setAttribute("error", login.get("msg"));
            return "redirect: http://auth.gameweb.com/regAndLogin";
        }

        session.setAttribute("loginUser", login.get("data"));
        session.setAttribute("login",login.getCode());
        return "redirect: http://gameweb.com/";
    }

    @GetMapping("/regin")
    public String regvf(@RequestBody MemberVo memberVo, HttpSession session) {
        R reg = memberFeign.reg(memberVo);

        if (reg.getCode() != 0) {
            session.setAttribute("error", reg.get("msg"));
            return "redirect: http://auth.gameweb.com/regAndLogin";
        }

        session.setAttribute("reg",reg.getCode());
        return "redirect: http://auth.gameweb.com/regAndLogin";
    }
}
