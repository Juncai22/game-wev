package com.example.gamewebpms.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gamewebpms.entity.PmsAttrImplEntity;
import com.example.gamewebpms.service.PmsAttrImplService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;



/**
 * 
 *
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-11-02 20:21:06
 */
@RestController
@RequestMapping("gamewebpms/pmsattrimpl")
public class PmsAttrImplController {
    @Autowired
    private PmsAttrImplService pmsAttrImplService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmsAttrImplService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		PmsAttrImplEntity pmsAttrImpl = pmsAttrImplService.getById(id);

        return R.ok().put("pmsAttrImpl", pmsAttrImpl);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PmsAttrImplEntity pmsAttrImpl){
		pmsAttrImplService.save(pmsAttrImpl);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PmsAttrImplEntity pmsAttrImpl){
		pmsAttrImplService.updateById(pmsAttrImpl);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		pmsAttrImplService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
