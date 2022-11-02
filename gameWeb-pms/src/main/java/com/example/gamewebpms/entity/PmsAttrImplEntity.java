package com.example.gamewebpms.entity;

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
@TableName("pms_attr_impl")
public class PmsAttrImplEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * attr实现类ID
	 */
	@TableId
	private Integer id;
	/**
	 * attrID
	 */
	private Integer attrId;
	/**
	 * attr具体事物的ID
	 */
	private String attrImplId;

}
