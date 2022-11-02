package com.example.gamewebpms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.gamewebpms.entity.PmsPubEntity;

import java.util.Map;

/**
 * 
 *
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-11-02 20:47:40
 */
public interface PmsPubService extends IService<PmsPubEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

