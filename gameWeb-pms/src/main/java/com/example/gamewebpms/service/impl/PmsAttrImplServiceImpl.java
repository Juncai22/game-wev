package com.example.gamewebpms.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.gamewebpms.dao.PmsAttrImplDao;
import com.example.gamewebpms.entity.PmsAttrImplEntity;
import com.example.gamewebpms.service.PmsAttrImplService;


@Service("pmsAttrImplService")
public class PmsAttrImplServiceImpl extends ServiceImpl<PmsAttrImplDao, PmsAttrImplEntity> implements PmsAttrImplService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsAttrImplEntity> page = this.page(
                new Query<PmsAttrImplEntity>().getPage(params),
                new QueryWrapper<PmsAttrImplEntity>()
        );

        return new PageUtils(page);
    }

}