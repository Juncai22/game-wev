package com.example.gamewebpms;

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

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameWebPmsApplicationTests {

    PmsCategoryService categoryController = new PmsCategoryServiceImpl();

    @Test
    public void contextLoads() {
        PmsCategoryEntity categoryEntity = new PmsCategoryEntity();
        categoryEntity.setCatId(1L);
        categoryEntity.setCatLevel(1);
        categoryEntity.setIcon("a");
        categoryEntity.setName("JUncai");
        categoryEntity.setParentCid(1L);
        categoryEntity.setProductCount(100);
        categoryEntity.setProductUnit("a");
        categoryEntity.setShowStatus(0);
        categoryEntity.setSort(1);

        categoryController.save(categoryEntity);
    }

}
