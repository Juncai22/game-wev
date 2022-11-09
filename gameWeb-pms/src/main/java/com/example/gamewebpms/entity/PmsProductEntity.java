package com.example.gamewebpms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-11-04 14:35:16
 */
@Data
@TableName("pms_product")
public class PmsProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 商品介绍
	 */
	private String descript;
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
