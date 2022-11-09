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
 * @date 2022-11-02 20:47:40
 */
@Data
@TableName("pms_pub")
public class PmsPubEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 发行商ID
	 */
	@TableId(type = IdType.AUTO)
	private Long pubId;
	/**
	 * 发行商名字
	 */
	private String name;
	/**
	 * 发行商介绍
	 */
	private String descript;
	/**
	 * 发行商图片
	 */
	private String image;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	private String firstLetter;
	/**
	 * 排序
	 */
	private Integer sort;

}
