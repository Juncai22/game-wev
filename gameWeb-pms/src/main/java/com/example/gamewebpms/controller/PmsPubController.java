package com.example.gamewebpms.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.common.valid.UpdataStatuGro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gamewebpms.entity.PmsPubEntity;
import com.example.gamewebpms.service.PmsPubService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;



/**
 * 
 *
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-11-02 20:47:40
 */
@RestController
@RequestMapping("gamewebpms/pmspub")
public class PmsPubController {
    @Autowired
    private PmsPubService pmsPubService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmsPubService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/update/status")
    public R updateStatu(@Validated({UpdataStatuGro.class}) @RequestBody PmsPubEntity pub){
        pmsPubService.updateById(pub);

        return R.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{pubId}")
    public R info(@PathVariable("pubId") Integer pubId){
		PmsPubEntity pmsPub = pmsPubService.getById(pubId);

        return R.ok().put("pmsPub", pmsPub);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PmsPubEntity pmsPub){
		pmsPubService.save(pmsPub);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PmsPubEntity pmsPub){
		pmsPubService.updateById(pmsPub);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] pubIds){
		pmsPubService.removeByIds(Arrays.asList(pubIds));

        return R.ok();
    }

}
