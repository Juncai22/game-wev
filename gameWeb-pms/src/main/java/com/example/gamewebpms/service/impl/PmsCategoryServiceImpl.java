package com.example.gamewebpms.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Cacheable(value = {"category"}, key = "'categoryNameList'")
    public List<String> listFn() {
        return this.list().stream().map(PmsCategoryEntity::getName).collect(Collectors.toList());
    }

    @Override
    public PageUtils toQueryPage(Map<String, Object> params) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //????????????Page????????????
        IPage<PmsCategoryEntity> page = this.page(
                new Query<PmsCategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );
        //??????redis??????????????????
        String categoryPage = ops.get("categoryList");

        //?????????????????????
        if (StringUtils.isEmpty(categoryPage)) {
            //????????????????????????
            List<PmsCategoryEntity> pmsCategoryEntities = categoryService.toList();
            page.setRecords(pmsCategoryEntities);
            //??????PageUtils
            return new PageUtils(page);
        }
        //???????????????????????????
        List<PmsCategoryEntity> pmsCategoryEntityIPage = JSON.parseArray(categoryPage, PmsCategoryEntity.class);
        page.setRecords(pmsCategoryEntityIPage);
        //????????????
        return new PageUtils(page);
    }


}