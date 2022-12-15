package com.example.gamewebpms;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.utils.PageUtils;
import com.example.gamewebpms.entity.PmsCategoryEntity;
import com.example.gamewebpms.service.PmsCategoryService;
import com.example.gamewebpms.service.impl.PmsCategoryServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GameWebPmsApplicationTests {

    @Resource
    StringRedisTemplate redisTemplate;

    @Test
    public void test() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String categoryList = ops.get("categoryList");

        JSONObject jsonObject = new JSONObject();
        IPage<PmsCategoryEntity> pmsCategoryEntityIPage  = jsonObject.getObject(categoryList, IPage.class);

        System.out.println(pmsCategoryEntityIPage);
    }
}
