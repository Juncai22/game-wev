package com.example.gamewebpms.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.gamewebpms.dao.PmsPubDao;
import com.example.gamewebpms.entity.PmsPubEntity;
import com.example.gamewebpms.service.PmsPubService;

import javax.annotation.Resource;


@Service("pmsPubService")
public class PmsPubServiceImpl extends ServiceImpl<PmsPubDao, PmsPubEntity> implements PmsPubService {

//    public static Long pain = 0;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsPubEntity> page = this.page(
                new Query<PmsPubEntity>().getPage(params),
                new QueryWrapper<PmsPubEntity>()
        );

        return new PageUtils(page);
    }
}