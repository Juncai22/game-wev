package com.example.gamewebpms.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.gamewebpms.dao.PmsProductImageDao;
import com.example.gamewebpms.entity.PmsProductImageEntity;
import com.example.gamewebpms.service.PmsProductImageService;


@Service("pmsProductImageService")
public class PmsProductImageServiceImpl extends ServiceImpl<PmsProductImageDao, PmsProductImageEntity> implements PmsProductImageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsProductImageEntity> page = this.page(
                new Query<PmsProductImageEntity>().getPage(params),
                new QueryWrapper<PmsProductImageEntity>()
        );

        return new PageUtils(page);
    }

}