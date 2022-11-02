package com.example.gamewebpms.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.common.valid.UpdataStatuGro;
import com.example.gamewebpms.entity.PmsBrandEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gamewebpms.entity.PmsSeriesEntity;
import com.example.gamewebpms.service.PmsSeriesService;
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
@RequestMapping("gamewebpms/pmsseries")
public class PmsSeriesController {
    @Autowired
    private PmsSeriesService pmsSeriesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmsSeriesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{seId}")
    public R info(@PathVariable("seId") Integer seId){
		PmsSeriesEntity pmsSeries = pmsSeriesService.getById(seId);

        return R.ok().put("pmsSeries", pmsSeries);
    }

    @RequestMapping("/update/status")
    public R updateStatu(@Validated({UpdataStatuGro.class}) @RequestBody PmsSeriesEntity series){
        pmsSeriesService.updateById(series);

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PmsSeriesEntity pmsSeries){
		pmsSeriesService.save(pmsSeries);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PmsSeriesEntity pmsSeries){
		pmsSeriesService.updateById(pmsSeries);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] seIds){
		pmsSeriesService.removeByIds(Arrays.asList(seIds));

        return R.ok();
    }

}
