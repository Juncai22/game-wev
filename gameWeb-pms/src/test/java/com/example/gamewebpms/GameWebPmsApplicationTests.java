package com.example.gamewebpms;

import com.example.gamewebpms.service.PmsCategoryService;
import com.example.gamewebpms.service.impl.PmsCategoryServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GameWebPmsApplicationTests {

    PmsCategoryService categoryController = new PmsCategoryServiceImpl();



    }
