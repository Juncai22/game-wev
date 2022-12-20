package com.example.gamewebpms.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.gamewebpms.dao.PmsCategoryDao;
import com.example.gamewebpms.entity.PmsCategoryEntity;
import com.example.gamewebpms.service.PmsCategoryService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("pmsCategoryService")
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryDao, PmsCategoryEntity> implements PmsCategoryService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    PmsCategoryService categoryService;


    @Override
    @Cacheable({"category"})
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsCategoryEntity> page = this.page(
                new Query<PmsCategoryEntity>().getPage(params),
                new QueryWrapper<PmsCategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value = {"category"}, key = "'categoryList'")
    public List<PmsCategoryEntity> toList() {
        return this.list();
    }

    @Override
    public PageUtils toQueryPage(Map<String, Object> params) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //将总体的Page提取出来
        IPage<PmsCategoryEntity> page = this.page(
                new Query<PmsCategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );
        //查看redis里面是否拥有
        String categoryPage = ops.get("categoryList");

        //如果没有则加入
        if (StringUtils.isEmpty(categoryPage)) {
            //先得到总体的基础
            List<PmsCategoryEntity> pmsCategoryEntities = categoryService.toList();
            page.setRecords(pmsCategoryEntities);
            //返回PageUtils
            return new PageUtils(page);
        }
        //如果有，得到集合团
        List<PmsCategoryEntity> pmsCategoryEntityIPage = JSON.parseArray(categoryPage, PmsCategoryEntity.class);
        page.setRecords(pmsCategoryEntityIPage);
        //返回答案
        return new PageUtils(page);
    }


}