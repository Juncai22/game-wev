package com.example.gamewebpms.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gamewebpms.entity.PmsProductImageEntity;
import com.example.gamewebpms.service.PmsProductImageService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;



/**
 * 
 *
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-11-04 14:35:16
 */
@RestController
@RequestMapping("gamewebpms/pmsproductimage")
public class PmsProductImageController {
    @Autowired
    private PmsProductImageService pmsProductImageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmsProductImageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{primaId}")
    public R info(@PathVariable("primaId") Long primaId){
		PmsProductImageEntity pmsProductImage = pmsProductImageService.getById(primaId);

        return R.ok().put("pmsProductImage", pmsProductImage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PmsProductImageEntity pmsProductImage){
		pmsProductImageService.save(pmsProductImage);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PmsProductImageEntity pmsProductImage){
		pmsProductImageService.updateById(pmsProductImage);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] primaIds){
		pmsProductImageService.removeByIds(Arrays.asList(primaIds));

        return R.ok();
    }

}
