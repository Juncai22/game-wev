package com.example.gamewebpms;

import com.aliyun.oss.*;
import com.example.gamewebpms.controller.PmsCategoryBrandRelationController;
import com.example.gamewebpms.controller.PmsCategoryController;
import com.example.gamewebpms.dao.PmsCategoryDao;
import com.example.gamewebpms.entity.PmsCategoryEntity;
import com.example.gamewebpms.service.PmsCategoryService;
import com.example.gamewebpms.service.impl.PmsCategoryServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameWebPmsApplicationTests {

    PmsCategoryService categoryController = new PmsCategoryServiceImpl();



    }
