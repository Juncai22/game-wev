package com.example.gamewebpms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * spu属性值
 * 
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-11-04 13:54:40
 */
@Data
@TableName("pms_product_attr_value")
public class PmsProductAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 商品id
	 */
	private Long prId;
	/**
	 * 哪一个属性
	 */
	private Long attrId;
	/**
	 * 属性具体值
	 */
	private Long attrimplId;
	/**
	 * 顺序
	 */
	private Integer attrSort;
	/**
	 * 快速展示【是否展示在介绍上；0-否 1-是】
	 */
	private Integer quickShow;

}
