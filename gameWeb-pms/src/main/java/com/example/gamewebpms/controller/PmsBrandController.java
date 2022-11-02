package com.example.gamewebpms.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.common.utils.PageUtils;
import com.example.common.utils.R;
import com.example.common.valid.Addgro;
import com.example.common.valid.UpdataStatuGro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gamewebpms.entity.PmsBrandEntity;
import com.example.gamewebpms.service.PmsBrandService;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-10-29 17:58:12
 */
@RestController
@RequestMapping("gamewebpms/pmsbrand")
public class PmsBrandController {
    @Autowired
    private PmsBrandService pmsBrandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmsBrandService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/update/status")
    public R updateStatu(@Validated({UpdataStatuGro.class}) @RequestBody PmsBrandEntity brand){
        pmsBrandService.updateById(brand);

        return R.ok();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		PmsBrandEntity pmsBrand = pmsBrandService.getById(brandId);

        return R.ok().put("pmsBrand", pmsBrand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated({Addgro.class}) @RequestBody PmsBrandEntity pmsBrand){
		pmsBrandService.save(pmsBrand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PmsBrandEntity pmsBrand){
		pmsBrandService.updateById(pmsBrand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		pmsBrandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
