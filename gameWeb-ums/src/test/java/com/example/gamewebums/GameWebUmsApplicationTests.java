package com.example.gamewebums;

import com.alibaba.nacos.common.util.Md5Utils;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameWebUmsApplicationTests {

    @Test
    public void contextLoads() {
        Long s = 1604859070555205633L;
        System.out.println(s);
    }
//    7a8b3e84      TLd+q9Ii8qPY1uBfzKrHAA==
//PGJxpfTveURnQAKMzNEFLQ==
    public static String md5(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");//利用哈希算法，MD5
            //面向字节处理，所以可以处理字节的东西，如图片，压缩包。。
            byte[] input = password.getBytes();
            byte[] output = md.digest(input);
            //将md5处理后的output结果利用Base64转成原有的字符串,不会乱码
            String str = Base64.encodeBase64String(output);
//			String str = new String(output); //原有转换
            return str;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("密码加密失败");
            return "";
        }

    }
}
