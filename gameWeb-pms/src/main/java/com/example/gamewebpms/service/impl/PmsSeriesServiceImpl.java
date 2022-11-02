package com.example.gamewebpms.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.gamewebpms.dao.PmsSeriesDao;
import com.example.gamewebpms.entity.PmsSeriesEntity;
import com.example.gamewebpms.service.PmsSeriesService;


@Service("pmsSeriesService")
public class PmsSeriesServiceImpl extends ServiceImpl<PmsSeriesDao, PmsSeriesEntity> implements PmsSeriesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsSeriesEntity> page = this.page(
                new Query<PmsSeriesEntity>().getPage(params),
                new QueryWrapper<PmsSeriesEntity>()
        );

        return new PageUtils(page);
    }

}