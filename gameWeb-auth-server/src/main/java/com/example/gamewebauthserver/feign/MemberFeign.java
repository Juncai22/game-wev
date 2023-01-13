package com.example.gamewebauthserver.feign;

import com.example.common.utils.R;
import com.example.common.vo.PrModel;
import com.example.gamewebauthserver.VO.MemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("game-ums")
public interface MemberFeign {


    @RequestMapping("/gamewebums/umsmember/reg")
    public R reg(@RequestBody MemberVo memberVo);

    @RequestMapping("/gamewebums/umsmember/login")
    public R login(@RequestBody MemberVo memberVo);
}
