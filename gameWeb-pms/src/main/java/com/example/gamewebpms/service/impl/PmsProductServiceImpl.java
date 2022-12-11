package com.example.gamewebpms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.example.common.utils.R;
import com.example.common.vo.PrModel;
import com.example.gamewebpms.controller.PmsProductAttrValueController;
import com.example.gamewebpms.dao.PmsProductImageDao;
import com.example.gamewebpms.entity.*;
import com.example.gamewebpms.feign.ElSearchFeign;
import com.example.gamewebpms.service.*;
import com.example.gamewebpms.vo.AttrVo;
import com.example.gamewebpms.vo.PmsProductEveryVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.gamewebpms.dao.PmsProductDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("pmsProductService")
public class PmsProductServiceImpl extends ServiceImpl<PmsProductDao, PmsProductEntity> implements PmsProductService {


    @Resource
    PmsProductImageService pmsProductImageService;

    @Resource
    ElSearchFeign elSearchFeign;
    @Resource
    PmsProductAttrValueService pmsProductAttrValueService;

    @Resource
    PmsBrandService pmsBrandService;

    @Resource
    PmsSeriesService pmsSeriesService;

    @Resource
    PmsPubService pmsPubService;

    @Resource
    PmsCategoryService pmsCategoryService;

    @Resource
    PmsProductDao pmsProductDao;

    @Resource
    PmsProductImageDao pmsProductImageDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        IPage<PmsProductEntity> page = this.page(
                new Query<PmsProductEntity>().getPage(params),
                new QueryWrapper<>()
        );

        List<PmsProductEntity> list = new ArrayList<>();
        Object key = null;
        if (params.get("key") != null && !params.get("key").equals("")) {
            key = params.get("key");
        }
        Object category = null;
        if (params.get("category") != null && !params.get("category").equals("") && !params.get("category").equals("全部类型")) {
            category = params.get("category");
        }
        if (key != null && category == null) {
            list = this.list(new QueryWrapper<PmsProductEntity>().like("name", "%" + key + "%"));
        } else if (category != null) {
            Long catId = pmsCategoryService.getOne(new QueryWrapper<PmsCategoryEntity>().eq("name", category)).getCatId();

            List<PmsProductAttrValueEntity> valueEntities = pmsProductAttrValueService.list(
                    new QueryWrapper<PmsProductAttrValueEntity>().eq("attr_id", 1).
                            like("attrImpl_id", catId + ";").or().
                            like("attrImpl_id", "%;" + catId + ";"));
            List<Long> collect = valueEntities.stream().map(PmsProductAttrValueEntity::getPrId).collect(Collectors.toList());

            if (collect.size() != 0) {
                if (key == null) {
                    list = this.listByIds(collect);
                } else {
                    HashSet<Long> idByCateGory = new HashSet<>(collect);
                    HashSet<Long> idByKeys = this.list(new QueryWrapper<PmsProductEntity>().like("name", "%" + key + "%"))
                            .stream().map(PmsProductEntity::getPrId).collect(Collectors.toCollection(HashSet::new));

                    List<Long> ids = new ArrayList<>();
                    for (Long aLong : idByCateGory) {
                        if (idByKeys.contains(aLong)) ids.add(aLong);
                    }
                    if (ids.size() != 0) {
                        list = this.listByIds(ids);
                    }
                }
            }
        }

        if (key != null || category != null) page.setRecords(list);

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveEvery(PmsProductEveryVo pmsProductEveryVo) {
        System.out.println(pmsProductEveryVo.toString());

        Long prId = (long) this.list().size() + 1;
        Long primageId = (long) pmsProductImageService.list().size() + 1;

        //进行保存商品的信息
        PmsProductEntity pmsProductEntity = new PmsProductEntity();

        pmsProductEntity.setPrId(prId);
        pmsProductEntity.setDescript(pmsProductEveryVo.getDescript());
        pmsProductEntity.setImage(pmsProductEveryVo.getImage());
        pmsProductEntity.setName(pmsProductEveryVo.getName());
        pmsProductEntity.setQuickShow(pmsProductEveryVo.getQuickShow());
        pmsProductEntity.setSort(pmsProductEveryVo.getSort());
        pmsProductEntity.setFirstLetter(pmsProductEveryVo.getFirstLetter());

        //进行保存图片的信息
        for (String image : pmsProductEveryVo.getPrImage()) {
            PmsProductImageEntity pmsProductImageEntity = new PmsProductImageEntity();
            pmsProductImageEntity.setPrimaId(primageId);
            pmsProductImageEntity.setSort(pmsProductEveryVo.getSort());
            pmsProductImageEntity.setQuickShow(pmsProductEveryVo.getQuickShow());
            pmsProductImageEntity.setPrId(prId);
            pmsProductImageEntity.setPrImage(image);

            pmsProductImageService.save(pmsProductImageEntity);
        }

        /**
         *         设置Attr属性，包括attrId，attr实现Id，以及商品Id
         */
        //设置开类型
        this.save(pmsProductEntity);
        PmsProductAttrValueEntity pmsProductAttrValueEntity0 = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity0.setPrId(prId);
        pmsProductAttrValueEntity0.setAttrId(1L);
        StringBuilder idString = new StringBuilder();
        for (String category : pmsProductEveryVo.getCategorys()) {
            idString.append(pmsCategoryService.getOne(new QueryWrapper<PmsCategoryEntity>().eq("name", category)).getCatId()).append(";");
        }
        pmsProductAttrValueEntity0.setAttrimplId(String.valueOf(idString));


        //设置开发商
        PmsProductAttrValueEntity pmsProductAttrValueEntity = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity.setPrId(prId);
        pmsProductAttrValueEntity.setAttrId(2L);
        pmsProductAttrValueEntity.setAttrimplId(
                String.valueOf(pmsBrandService.getOne(new QueryWrapper<PmsBrandEntity>().eq("name", pmsProductEveryVo.getBrand())).getBrandId()));

        //设置商品的发售商
        PmsProductAttrValueEntity pmsProductAttrValueEntity1 = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity1.setPrId(prId);
        pmsProductAttrValueEntity1.setAttrId(3L);
        pmsProductAttrValueEntity1.setAttrimplId(
                String.valueOf(pmsPubService.getOne(new QueryWrapper<PmsPubEntity>().eq("name", pmsProductEveryVo.getPub())).getPubId()));

        //设置系列
        PmsProductAttrValueEntity pmsProductAttrValueEntity2 = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity2.setPrId(prId);
        pmsProductAttrValueEntity2.setAttrId(4L);
        pmsProductAttrValueEntity2.setAttrimplId(
                String.valueOf(pmsSeriesService.getOne(new QueryWrapper<PmsSeriesEntity>().eq("name", pmsProductEveryVo.getSerie())).getSeId()));

        pmsProductAttrValueService.save(pmsProductAttrValueEntity);
        pmsProductAttrValueService.save(pmsProductAttrValueEntity0);
        pmsProductAttrValueService.save(pmsProductAttrValueEntity1);
        pmsProductAttrValueService.save(pmsProductAttrValueEntity2);
    }

    @Override
    @Transactional
    public PmsProductEveryVo getEveryById(Long prId) {
        //设置Product属性
        PmsProductEntity byId = this.getById(prId);
        PmsProductEveryVo pmsProductEveryVo = new PmsProductEveryVo();
        pmsProductEveryVo.setPrId(byId.getPrId());
        pmsProductEveryVo.setDescript(byId.getDescript());
        pmsProductEveryVo.setImage(byId.getImage());
        pmsProductEveryVo.setFirstLetter(byId.getFirstLetter());
        pmsProductEveryVo.setName(byId.getName());
        pmsProductEveryVo.setQuickShow(byId.getQuickShow());
        pmsProductEveryVo.setSort(byId.getSort());

        //使用sql语句，以及查询语句
        Wrapper<PmsProductImageEntity> wrapper = new QueryWrapper<PmsProductImageEntity>().eq("pr_id", prId);
        List<PmsProductImageEntity> list = pmsProductImageService.list(wrapper);
        List<String> prImage = new ArrayList<>();

        Wrapper<PmsProductAttrValueEntity> attrWrapper = new QueryWrapper<PmsProductAttrValueEntity>().eq("pr_id", prId);
        List<PmsProductAttrValueEntity> attrList = pmsProductAttrValueService.list(attrWrapper);

        for (PmsProductAttrValueEntity pmsProductAttrValueEntity : attrList) {
            Long attrId = pmsProductAttrValueEntity.getAttrId();
            if (attrId == 1) {
                List<String> categorys = new ArrayList<>();
                for (String s : pmsProductAttrValueEntity.getAttrimplId().split(";")) {
                    PmsCategoryEntity category = pmsCategoryService.getById(s);
                    categorys.add(category.getName());
                }
                pmsProductEveryVo.setCategorys(categorys);
            }
            if (attrId == 2) {
                PmsBrandEntity brand = pmsBrandService.getById(pmsProductAttrValueEntity.getAttrimplId());
                List<String> brands = new ArrayList<>();
                brands.add(brand.getName());
                pmsProductEveryVo.setBrands(brands);
            }
            if (attrId == 3) {
                PmsPubEntity pub = pmsPubService.getById(pmsProductAttrValueEntity.getAttrimplId());
                List<String> pubs = new ArrayList<>();
                pubs.add(pub.getName());
                pmsProductEveryVo.setPubs(pubs);
            }
            if (attrId == 4) {
                PmsSeriesEntity serie = pmsSeriesService.getById(pmsProductAttrValueEntity.getAttrimplId());
                List<String> series = new ArrayList<>();
                series.add(serie.getName());
                pmsProductEveryVo.setSeries(series);
            }
        }

        //使用for循环次数慢慢添加
        for (PmsProductImageEntity pmsProductImageEntity : list) {
            prImage.add(pmsProductImageEntity.getPrImage());
        }
        pmsProductEveryVo.setPrImage(prImage);

        return pmsProductEveryVo;
    }

    @Override
    @Transactional
    public AttrVo getAttrVo() {
        AttrVo attrVo = new AttrVo();
        attrVo.setBrands(pmsBrandService.list().stream().map(PmsBrandEntity::getName).collect(Collectors.toList()));
        attrVo.setPubs(pmsPubService.list().stream().map(PmsPubEntity::getName).collect(Collectors.toList()));
        attrVo.setSeries(pmsSeriesService.list().stream().map(PmsSeriesEntity::getName).collect(Collectors.toList()));
        attrVo.setCategorys(pmsCategoryService.list().stream().map(PmsCategoryEntity::getName).collect(Collectors.toList()));

        return attrVo;
    }

    @Override
    @Transactional
    public void updateEverById(PmsProductEveryVo pmsProductEveryVo) {
        Long prId = pmsProductEveryVo.getPrId();

        PmsProductEntity product = new PmsProductEntity();
        //将product里面的数据进行修改
        product.setPrId(pmsProductEveryVo.getPrId());
        product.setDescript(pmsProductEveryVo.getDescript());
        product.setImage(pmsProductEveryVo.getImage());
        product.setName(pmsProductEveryVo.getName());
        product.setQuickShow(pmsProductEveryVo.getQuickShow());
        product.setSort(pmsProductEveryVo.getSort());
        product.setFirstLetter(pmsProductEveryVo.getFirstLetter());

        this.updateById(product);

        /**
         * 对于图片进行全方面删除之后在进行添加
         */
        List<String> prImage = pmsProductEveryVo.getPrImage();
        pmsProductImageService.remove(new QueryWrapper<PmsProductImageEntity>().eq("pr_id", pmsProductEveryVo.getPrId()));

        //进行保存图片的信息
        for (String image : pmsProductEveryVo.getPrImage()) {
            PmsProductImageEntity pmsProductImageEntity = new PmsProductImageEntity();
            pmsProductImageEntity.setSort(pmsProductEveryVo.getSort());
            pmsProductImageEntity.setQuickShow(pmsProductEveryVo.getQuickShow());
            pmsProductImageEntity.setPrId(pmsProductEveryVo.getPrId());
            pmsProductImageEntity.setPrImage(image);

            pmsProductImageService.save(pmsProductImageEntity);
        }

        //对于属性方面的保存
        //设置类型
        PmsProductAttrValueEntity pmsProductAttrValueEntity0 = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity0.setPrId(prId);
        pmsProductAttrValueEntity0.setAttrId(1L);
        StringBuilder idString = new StringBuilder();
        for (String category : pmsProductEveryVo.getCategorys()) {
            idString.append(pmsCategoryService.getOne(new QueryWrapper<PmsCategoryEntity>().eq("name", category)).getCatId()).append(";");
        }
        pmsProductAttrValueEntity0.setAttrimplId(String.valueOf(idString));
        pmsProductAttrValueEntity0.setId(pmsProductAttrValueService.getOne(new QueryWrapper<PmsProductAttrValueEntity>()
                .eq("pr_id", prId).eq("attr_id", 1)).getId());
        pmsProductAttrValueService.updateById(pmsProductAttrValueEntity0);
        //设置开发商
        PmsProductAttrValueEntity pmsProductAttrValueEntity = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity.setPrId(prId);
        pmsProductAttrValueEntity.setAttrId(2L);
        pmsProductAttrValueEntity.setAttrimplId(
                String.valueOf(pmsBrandService.getOne(new QueryWrapper<PmsBrandEntity>().eq("name", pmsProductEveryVo.getBrand())).getBrandId()));

        pmsProductAttrValueEntity.setId(pmsProductAttrValueService.getOne(new QueryWrapper<PmsProductAttrValueEntity>()
                .eq("pr_id", prId).eq("attr_id", 2)).getId());
        pmsProductAttrValueService.updateById(pmsProductAttrValueEntity);
        //设置商品的发售商
        PmsProductAttrValueEntity pmsProductAttrValueEntity1 = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity1.setPrId(prId);
        pmsProductAttrValueEntity1.setAttrId(3L);
        pmsProductAttrValueEntity1.setAttrimplId(
                String.valueOf(pmsPubService.getOne(new QueryWrapper<PmsPubEntity>()
                        .eq("name", pmsProductEveryVo.getPub())).getPubId()));

        pmsProductAttrValueEntity1.setId(pmsProductAttrValueService.getOne(new QueryWrapper<PmsProductAttrValueEntity>()
                .eq("pr_id", prId).eq("attr_id", 3)).getId());
        pmsProductAttrValueService.updateById(pmsProductAttrValueEntity1);
        //设置系列
        PmsProductAttrValueEntity pmsProductAttrValueEntity2 = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity2.setPrId(prId);
        pmsProductAttrValueEntity2.setAttrId(4L);
        pmsProductAttrValueEntity2.setAttrimplId(
                String.valueOf(pmsSeriesService.getOne(new QueryWrapper<PmsSeriesEntity>().eq("name", pmsProductEveryVo.getSerie())).getSeId()));

        pmsProductAttrValueEntity2.setId(pmsProductAttrValueService.getOne(new QueryWrapper<PmsProductAttrValueEntity>()
                .eq("pr_id", prId).eq("attr_id", 4)).getId());
        pmsProductAttrValueService.updateById(pmsProductAttrValueEntity2);
    }

    @Override
    @Transactional
    public void removeEverByIds(List<Long> asList) {
        this.removeByIds(asList);

        for (Long aLong : asList) {
            pmsProductImageService.remove(new QueryWrapper<PmsProductImageEntity>().eq("pr_id", aLong));
        }
        for (Long aLong : asList) {
            pmsProductAttrValueService.remove(new QueryWrapper<PmsProductAttrValueEntity>().eq("pr_id", aLong));
        }
    }


    /**
     * TODO 以后要进行的价格修改地点PrPrice
     *      以后要修改的SaleCount
     *      以后要修改是否有库存
     *
     * @param product
     */
    @Override
    public void up(PmsProductEntity product) {
        PrModel model = new PrModel();

        Long prId = product.getPrId();
        product = this.getById(prId);
        model.setPrId(prId);
        model.setPrName(product.getName());
        model.setPrPrice(BigDecimal.valueOf(0L));
        model.setPrImage(product.getImage());
        model.setSaleCount(0L);
        model.setHasStock(true);

        List<PmsProductAttrValueEntity> prae = pmsProductAttrValueService.list(new QueryWrapper<PmsProductAttrValueEntity>().eq("pr_id", prId));
        List<Long> collect = prae.stream().map(PmsProductAttrValueEntity::getAttrId).collect(Collectors.toList());
        List<String> collect1 = prae.stream().map(PmsProductAttrValueEntity::getAttrimplId).collect(Collectors.toList());
        //将各个属性赋值
        for (int i = 0; i < collect.size(); i++) {
            //如果是分类，因为是List集合，所以将会进行结合
            if (collect.get(i) == 1) {
                String sub = collect1.get(i);
                List<Long> categoryIds = Arrays.stream(sub.split(";")).map(Long::valueOf).collect(Collectors.toList());
                model.setCatelogIds(categoryIds);
                List<String> categoryNames = pmsCategoryService.listByIds(categoryIds).stream().map(PmsCategoryEntity::getName).collect(Collectors.toList());
                model.setCategpryNames(categoryNames);
                //如果是开发商，则直接可以把需要的和那些不需要的直接保存
            } else if (collect.get(i) == 2) {
                Long sub = Long.valueOf(collect1.get(i));
                model.setBrandId(sub);
                model.setBrandName(pmsBrandService.getById(sub).getName());
                //如果是发行商，则直接可以把需要的和那些不需要的直接保存
            } else if (collect.get(i) == 3) {
                Long sub = Long.valueOf(collect1.get(i));
                model.setPubId(sub);
                model.setPubName(pmsPubService.getById(sub).getName());
                //如果是系列，则直接可以把需要的和那些不需要的直接保存
            } else {
                Long sub = Long.valueOf(collect1.get(i));
                model.setSerieId(sub);
                model.setSerieName(pmsSeriesService.getById(sub).getName());
            }
        }
        //赋值完进行远程调用，Feign
        R r = elSearchFeign.savePrModel(model);

        if (r.getCode() == 0){
            //上架成功,改变状态
            this.updateById(product);
        }
    }

}