package com.example.gamewebpms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.gamewebpms.entity.PmsProductAttrValueEntity;

import java.util.Map;

/**
 * spu属性值
 *
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-11-04 13:54:40
 */
public interface PmsProductAttrValueService extends IService<PmsProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

