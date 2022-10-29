package com.example.gamewebums.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.gamewebums.dao.UmsMemberStatisticsInfoDao;
import com.example.gamewebums.entity.UmsMemberStatisticsInfoEntity;
import com.example.gamewebums.service.UmsMemberStatisticsInfoService;


@Service("umsMemberStatisticsInfoService")
public class UmsMemberStatisticsInfoServiceImpl extends ServiceImpl<UmsMemberStatisticsInfoDao, UmsMemberStatisticsInfoEntity> implements UmsMemberStatisticsInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UmsMemberStatisticsInfoEntity> page = this.page(
                new Query<UmsMemberStatisticsInfoEntity>().getPage(params),
                new QueryWrapper<UmsMemberStatisticsInfoEntity>()
        );

        return new PageUtils(page);
    }

}