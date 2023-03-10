package com.example.gamewebpms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.gamewebpms.entity.PmsBrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-10-29 17:58:12
 */
public interface PmsBrandService extends IService<PmsBrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

