package com.example.gamewebpms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.gamewebpms.entity.PmsProductImageEntity;

import java.util.Map;

/**
 * 
 *
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-11-04 14:35:16
 */
public interface PmsProductImageService extends IService<PmsProductImageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

