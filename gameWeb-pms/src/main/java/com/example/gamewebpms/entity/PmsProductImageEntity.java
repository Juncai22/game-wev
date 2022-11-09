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
@TableName("pms_product_image")
public class PmsProductImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品图片的主Id
	 */
	@TableId(type = IdType.AUTO)
	private Long primaId;
	/**
	 * 商品的ID
	 */
	private Long prId;
	/**
	 * 商品的图片
	 */
	private String prImage;
	/**
	 * 商品图片的顺序
	 */
	private Integer sort;
	/**
	 * 商品图片是否展示
	 */
	private Integer quickShow;

}
