package com.example.gamewebpms.vo;

import lombok.Data;

import java.util.List;

@Data
public class PmsProductEveryVo {

    private static final long serialVersionUID = 1L;

    /**
     * 商品Id
     */
    private Long prId;
    /**
     * 商品图片的主Id
     */
    private Long primaId;
    /**
     * 商品名字
     */
    private String name;
    /**
     * 商品介绍
     */
    private String descript;
    /**
     * 商品主图
     */
    private String image;
    /**
     * 商品的图片
     */
    private List<String> prImage;
    /**
     * 商品的类型
     */
    private List<String> categorys;
    /**
     * 商品的开发商属性
     */
    private String brand;
    /**
     * 商品的开发商属性
     */
    private String pub;
    /**
     * 商品的开发商属性
     */
    private String serie;
    /**
     * 商品的开发商属性
     */
    private List<String> brands;
    /**
     * 商品的开发商属性
     */
    private List<String> pubs;
    /**
     * 商品的开发商属性
     */
    private List<String> series;
    /**
     * 顺序
     */
    private Integer sort;
    /**
     * 监测首字母
     */
    private String firstLetter;
    /**
     * 快速展示
     */
    private Integer quickShow;
}
