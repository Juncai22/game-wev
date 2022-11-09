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
 * @date 2022-11-02 20:21:06
 */
@Data
@TableName("pms_attr")
public class PmsAttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * attr_id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 属性名字
	 */
	private String attrName;
	/**
	 * 是否检索
	 */
	private Integer attrStatus;

}
