package com.example.gamewebums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.gamewebums.entity.UmsMemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-10-29 19:00:46
 */
public interface UmsMemberService extends IService<UmsMemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

