package com.example.gamewebpms.vo.Impl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;


@Data
public class ProductImpl {

    /**
     * 商品Id
     */
    @TableId(type = IdType.AUTO)
    private Long prId;
    /**
     * 商品名字
     */
    private String name;
    /**
     * 商品版本
     */
    private String version;
    /**
     * 游戏介绍
     */
    private String gameDesc;
    /**
     * 游戏配置介绍
     */
    private String disposition;
    /**
     * 游戏类型
     */
    private List<String> categorys;
    /**
     * 商品主图
     */
    private String image;
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
