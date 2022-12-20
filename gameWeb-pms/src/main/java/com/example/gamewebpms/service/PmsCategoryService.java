package com.example.gamewebpms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.gamewebpms.entity.PmsCategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-10-29 17:20:12
 */
public interface PmsCategoryService extends IService<PmsCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils toQueryPage(Map<String, Object> params);

    List<PmsCategoryEntity> toList();

    List<String> listFn();
}

