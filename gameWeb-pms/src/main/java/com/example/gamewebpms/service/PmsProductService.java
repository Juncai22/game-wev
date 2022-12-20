package com.example.gamewebpms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.gamewebpms.entity.PmsProductEntity;
import com.example.gamewebpms.vo.AttrVo;
import com.example.gamewebpms.vo.Impl.ProductImpl;
import com.example.gamewebpms.vo.PmsProductEveryVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Juncai22
 * @email 1547598719@qq.com
 * @date 2022-11-04 14:35:16
 */
public interface PmsProductService extends IService<PmsProductEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveEvery(PmsProductEveryVo pmsProductEveryVo);

    PmsProductEveryVo getEveryById(Long prId);

    AttrVo getAttrVo();

    void updateEverById(PmsProductEveryVo pmsProductEveryVo);

    void removeEverByIds(List<Long> asList);

    void up(PmsProductEntity product);

    PageUtils queryPageImpl(Map<String, Object> params);

    ProductImpl infoImpl(Long prId);
}

