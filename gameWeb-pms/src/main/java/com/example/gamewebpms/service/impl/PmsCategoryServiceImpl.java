package com.example.gamewebpms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.gamewebpms.entity.PmsProductEntity;
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


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsCategoryEntity> page = this.page(
                new Query<PmsCategoryEntity>().getPage(params),
                new QueryWrapper<PmsCategoryEntity>()
        );

        return new PageUtils(page);
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
        String categoryList = ops.get("categoryList");

        //如果没有则加入
        if (StringUtils.isEmpty(categoryList)) {
            //先得到总体的基础
            List<PmsCategoryEntity> list = this.list();
            page.setRecords(list);
            //将集合转化为json字符串，并保存如redis
            String categorys = JSON.toJSONString(list);
            ops.set("categoryList", categorys);
            //返回PageUtils
            return new PageUtils(page);
        }
        //如果有，得到集合团
        List<PmsCategoryEntity> categorys = JSON.parseArray(categoryList, PmsCategoryEntity.class);
        page.setRecords(categorys);

        //返回答案
        return new PageUtils(page);
    }
}