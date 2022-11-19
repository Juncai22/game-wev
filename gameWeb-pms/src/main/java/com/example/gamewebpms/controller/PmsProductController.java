package com.example.gamewebpms.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.common.valid.UpdataStatuGro;
import com.example.gamewebpms.entity.PmsBrandEntity;
import com.example.gamewebpms.vo.AttrVo;
import com.example.gamewebpms.vo.PmsProductEveryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gamewebpms.entity.PmsProductEntity;
import com.example.gamewebpms.service.PmsProductService;
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
@RequestMapping("gamewebpms/pmsproduct")
public class PmsProductController {
    @Autowired
    private PmsProductService pmsProductService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmsProductService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/update/status")
    public R updateStatu(@Validated({UpdataStatuGro.class}) @RequestBody PmsProductEntity product){
        pmsProductService.up(product);

        return R.ok();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{prId}")
    public R info(@PathVariable("prId") Long prId){
		PmsProductEveryVo pmsProductEveryVo = pmsProductService.getEveryById(prId);

        return R.ok().put("pmsProduct", pmsProductEveryVo);
    }

    @RequestMapping("/listToOne")
    public R listToOne(){
        AttrVo attrVo = pmsProductService.getAttrVo();

        return R.ok().put("attrVo", attrVo);
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PmsProductEveryVo pmsProductEveryVo){
		pmsProductService.saveEvery(pmsProductEveryVo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PmsProductEveryVo pmsProductEveryVo){
		pmsProductService.updateEverById(pmsProductEveryVo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] prIds){
		pmsProductService.removeEverByIds(Arrays.asList(prIds));

        return R.ok();
    }

}
