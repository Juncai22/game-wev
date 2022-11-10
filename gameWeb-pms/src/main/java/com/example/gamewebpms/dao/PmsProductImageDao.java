package com.example.gamewebpms.dao;

import com.example.gamewebpms.entity.PmsProductImageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-11-04 14:35:16
 */
@Mapper
public interface PmsProductImageDao extends BaseMapper<PmsProductImageEntity> {

    void save(@Param("pmsProductImageEntity") PmsProductImageEntity pmsProductImageEntity);
}
