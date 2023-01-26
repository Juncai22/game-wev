package com.example.gamewebauthserver.controller;

import com.example.common.utils.R;
import com.example.common.utils.WordContent;
import com.example.gamewebauthserver.VO.MemberVo;
import com.example.gamewebauthserver.feign.MemberFeign;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Resource
    MemberFeign memberFeign;

    @GetMapping("/login")
    public String login(HttpSession session) {
        Object login = session.getAttribute(WordContent.LOGIN.getWord());

        if (login == null)
            return "regAndLogin";

        if (login.equals(R.ok().getCode()))
            return "redirect:http://gameweb.com";

        return "regAndLogin";
    }


    @PostMapping("/loginvf")
    @ResponseBody
    public R loginvf(@RequestBody MemberVo memberVo, HttpSession session) {
        R login = memberFeign.login(memberVo);

        if (login.getCode() != 0)
            return R.error(login.getCode(), (String) login.get("msg"));


        session.setAttribute(WordContent.LOGIN_USER.getWord(), login.get("data"));
        session.setAttribute(WordContent.LOGIN.getWord(), login.getCode());

        return R.ok();
    }

    @PostMapping("/regin")
    @ResponseBody
    public R regvf(@RequestBody MemberVo memberVo, HttpSession session) {
        R reg = memberFeign.reg(memberVo);

        if (reg.getCode() != 0)
            return R.error(reg.getCode(), (String) reg.get("msg"));


        return R.ok();
    }
}
