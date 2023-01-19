package com.example.gamewebums;

import com.alibaba.nacos.common.util.Md5Utils;
import com.example.gamewebums.entity.UmsMemberEntity;
import com.example.gamewebums.entity.UmsMemberLevelEntity;
import com.example.gamewebums.service.UmsMemberLevelService;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameWebUmsApplicationTests {


    @Autowired
    UmsMemberLevelService memberLevelService;

    @Test
    public void md5() {
        List<UmsMemberLevelEntity> memberEntities = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            UmsMemberLevelEntity memberLevel = new UmsMemberLevelEntity();
            if (i < 100)
                memberLevel.setGrowthPoint(1);
            else if (i < 300)
                memberLevel.setGrowthPoint(2);
            else
                memberLevel.setGrowthPoint(3);

            String substring = String.valueOf(UUID.randomUUID()).substring(0, 8);
            memberLevel.setName(substring);
            memberEntities.add(memberLevel);
        }

        memberLevelService.saveBatch(memberEntities);
    }
}
