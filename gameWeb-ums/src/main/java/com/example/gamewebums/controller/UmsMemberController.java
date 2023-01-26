package com.example.gamewebums.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.common.enumException.DirErrorCodeEnum;
import com.example.common.utils.DirErrorUtils;
import com.example.gamewebums.VO.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gamewebums.entity.UmsMemberEntity;
import com.example.gamewebums.service.UmsMemberService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;


/**
 * 会员
 *
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-10-29 19:00:46
 */
@RestController
@RequestMapping("gamewebums/umsmember")
public class UmsMemberController {
    @Autowired
    private UmsMemberService umsMemberService;

    private DirErrorUtils dirErrorUtils = new DirErrorUtils();

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = umsMemberService.queryPage(params);

        return R.ok().put("page", page);
    }


    @RequestMapping("/reg")
    public R reg(@RequestBody MemberVo memberVo) {
        int regCheck = umsMemberService.reg(memberVo);

        if (regCheck != 0) {
            return R.error(regCheck, dirErrorUtils.tochek(regCheck));
        }

        return R.ok();
    }

    @RequestMapping("/login")
    public R login(@RequestBody MemberVo memberVo) {
        //得到所有保留的数据
        String loginCheck = umsMemberService.login(memberVo);

        String[] split = loginCheck.split(";");
        int check = Integer.parseInt(split[0]);

        if (check != 0) {
            return R.error(check, dirErrorUtils.tochek(check));
        }
        memberVo.setLevel(Integer.parseInt(split[1]));
        memberVo.setPassWord(" ");

        return R.ok().put("data", memberVo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        UmsMemberEntity umsMember = umsMemberService.getById(id);

        return R.ok().put("umsMember", umsMember);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UmsMemberEntity umsMember) {
        umsMemberService.save(umsMember);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UmsMemberEntity umsMember) {
        umsMemberService.updateById(umsMember);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        umsMemberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
