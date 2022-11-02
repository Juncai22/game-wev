package com.example.gamewebpms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.example.common.valid.Addgro;
import com.example.common.valid.ListValue;
import com.example.common.valid.UpdataGro;
import com.example.common.valid.UpdataStatuGro;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-10-29 17:58:12
 *
 * TODO JSR303
 */
@Data
@TableName("pms_brand")
public class PmsBrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	@Null(message = "新增不能添加ID",groups = {Addgro.class})
	@NotNull(message = "修改必须添加Id",groups = {UpdataGro.class , UpdataStatuGro.class})
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名必须提交",groups = {Addgro.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotNull(groups = {Addgro.class})
	@URL(message = "logo必须是一个合法的字段",groups = {Addgro.class,UpdataGro.class})
	private String logo;
	/**
	 * 介绍
	 */

	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@NotNull(groups = {Addgro.class , UpdataStatuGro.class , UpdataGro.class})
	@ListValue(vals = {0,1},groups = {Addgro.class , UpdataStatuGro.class} , message = "必须提交指点的值")
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotNull(groups = {Addgro.class})
	@Pattern(regexp = "^[a-zA-Z]$" , message = "必须为一个字母" , groups = {Addgro.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(groups = {Addgro.class})
	@Min(value = 0,message = "排序必须大于等于零",groups = {Addgro.class,UpdataGro.class})
	private Integer sort;


}
