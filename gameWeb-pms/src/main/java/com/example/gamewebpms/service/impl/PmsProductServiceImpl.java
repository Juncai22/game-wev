package com.example.gamewebpms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.utils.R;
import com.example.common.vo.PrModel;
import com.example.gamewebpms.dao.PmsProductImageDao;
import com.example.gamewebpms.entity.*;
import com.example.gamewebpms.feign.ElSearchFeign;
import com.example.gamewebpms.service.*;
import com.example.gamewebpms.vo.AttrVo;
import com.example.gamewebpms.vo.Impl.ProductImpl;
import com.example.gamewebpms.vo.PmsProductEveryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
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
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("pmsProductService")
public class PmsProductServiceImpl extends ServiceImpl<PmsProductDao, PmsProductEntity> implements PmsProductService {


    @Resource
    StringRedisTemplate stringRedisTemplate;

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
        //??????????????????????????????????????????????????????????????????
        if (params.get("key") != null && !params.get("key").equals("")) {
            key = params.get("key");
        }
        //???????????????????????????????????????
        Object category = null;
        if (params.get("category") != null && !params.get("category").equals("") && !params.get("category").equals("????????????")) {
            category = params.get("category");
        }
        //???????????????
        if (key != null && category == null) {
            list = this.list(new QueryWrapper<PmsProductEntity>().like("name", "%" + key + "%"));
        } else if (category != null) {
            Long catId = pmsCategoryService.getOne(new QueryWrapper<PmsCategoryEntity>().eq("name", category)).getCatId();
            //???????????????????????????????????????
            List<PmsProductAttrValueEntity> valueEntities = pmsProductAttrValueService.list(
                    new QueryWrapper<PmsProductAttrValueEntity>().eq("attr_id", 1).
                            like("attrImpl_id", catId + ";").or().
                            like("attrImpl_id", "%;" + catId + ";"));
            List<Long> collect = valueEntities.stream().map(PmsProductAttrValueEntity::getPrId).collect(Collectors.toList());

            //????????????????????????????????????????????????
            if (collect.size() != 0) {
                if (key == null) {
                    list = this.listByIds(collect);
                    //????????????????????????
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

        //???????????????????????????
        PmsProductEntity pmsProductEntity = new PmsProductEntity();

        pmsProductEntity.setPrId(prId);
        pmsProductEntity.setDescript(pmsProductEveryVo.getDescript());
        pmsProductEntity.setImage(pmsProductEveryVo.getImage());
        pmsProductEntity.setName(pmsProductEveryVo.getName());
        pmsProductEntity.setQuickShow(pmsProductEveryVo.getQuickShow());
        pmsProductEntity.setSort(pmsProductEveryVo.getSort());
        pmsProductEntity.setFirstLetter(pmsProductEveryVo.getFirstLetter());

        //???????????????????????????
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
         *         ??????Attr???????????????attrId???attr??????Id???????????????Id
         */
        //???????????????
        this.save(pmsProductEntity);
        PmsProductAttrValueEntity pmsProductAttrValueEntity0 = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity0.setPrId(prId);
        pmsProductAttrValueEntity0.setAttrId(1L);
        StringBuilder idString = new StringBuilder();
        for (String category : pmsProductEveryVo.getCategorys()) {
            idString.append(pmsCategoryService.getOne(new QueryWrapper<PmsCategoryEntity>().eq("name", category)).getCatId()).append(";");
        }
        pmsProductAttrValueEntity0.setAttrimplId(String.valueOf(idString));


        //???????????????
        PmsProductAttrValueEntity pmsProductAttrValueEntity = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity.setPrId(prId);
        pmsProductAttrValueEntity.setAttrId(2L);
        pmsProductAttrValueEntity.setAttrimplId(
                String.valueOf(pmsBrandService.getOne(new QueryWrapper<PmsBrandEntity>().eq("name", pmsProductEveryVo.getBrand())).getBrandId()));

        //????????????????????????
        PmsProductAttrValueEntity pmsProductAttrValueEntity1 = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity1.setPrId(prId);
        pmsProductAttrValueEntity1.setAttrId(3L);
        pmsProductAttrValueEntity1.setAttrimplId(
                String.valueOf(pmsPubService.getOne(new QueryWrapper<PmsPubEntity>().eq("name", pmsProductEveryVo.getPub())).getPubId()));

        //????????????
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
        //??????Product??????
        PmsProductEntity byId = this.getById(prId);
        PmsProductEveryVo pmsProductEveryVo = new PmsProductEveryVo();
        pmsProductEveryVo.setPrId(byId.getPrId());
        pmsProductEveryVo.setDescript(byId.getDescript());
        pmsProductEveryVo.setImage(byId.getImage());
        pmsProductEveryVo.setFirstLetter(byId.getFirstLetter());
        pmsProductEveryVo.setName(byId.getName());
        pmsProductEveryVo.setQuickShow(byId.getQuickShow());
        pmsProductEveryVo.setSort(byId.getSort());

        //??????sql???????????????????????????
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

        //??????for????????????????????????
        for (PmsProductImageEntity pmsProductImageEntity : list) {
            prImage.add(pmsProductImageEntity.getPrImage());
        }
        pmsProductEveryVo.setPrImage(prImage);

        return pmsProductEveryVo;
    }

    @Override
    @Transactional
    @Cacheable(value = {"attrvo"}, key = "'attrvoList'")
    public AttrVo getAttrVo() {
        String attrvoList = stringRedisTemplate.opsForValue().get("attrvoList");
        if (!StringUtils.isEmpty(attrvoList)) {
            return JSON.parseObject(attrvoList, new TypeReference<AttrVo>() {
            });
        }
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
        //???product???????????????????????????
        product.setPrId(pmsProductEveryVo.getPrId());
        product.setDescript(pmsProductEveryVo.getDescript());
        product.setImage(pmsProductEveryVo.getImage());
        product.setName(pmsProductEveryVo.getName());
        product.setQuickShow(pmsProductEveryVo.getQuickShow());
        product.setSort(pmsProductEveryVo.getSort());
        product.setFirstLetter(pmsProductEveryVo.getFirstLetter());

        this.updateById(product);

        /**
         * ??????????????????????????????????????????????????????
         */
        List<String> prImage = pmsProductEveryVo.getPrImage();
        pmsProductImageService.remove(new QueryWrapper<PmsProductImageEntity>().eq("pr_id", pmsProductEveryVo.getPrId()));

        //???????????????????????????
        for (String image : pmsProductEveryVo.getPrImage()) {
            PmsProductImageEntity pmsProductImageEntity = new PmsProductImageEntity();
            pmsProductImageEntity.setSort(pmsProductEveryVo.getSort());
            pmsProductImageEntity.setQuickShow(pmsProductEveryVo.getQuickShow());
            pmsProductImageEntity.setPrId(pmsProductEveryVo.getPrId());
            pmsProductImageEntity.setPrImage(image);

            pmsProductImageService.save(pmsProductImageEntity);
        }

        //???????????????????????????
        //????????????
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
        //???????????????
        PmsProductAttrValueEntity pmsProductAttrValueEntity = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity.setPrId(prId);
        pmsProductAttrValueEntity.setAttrId(2L);
        pmsProductAttrValueEntity.setAttrimplId(
                String.valueOf(pmsBrandService.getOne(new QueryWrapper<PmsBrandEntity>().eq("name", pmsProductEveryVo.getBrand())).getBrandId()));

        pmsProductAttrValueEntity.setId(pmsProductAttrValueService.getOne(new QueryWrapper<PmsProductAttrValueEntity>()
                .eq("pr_id", prId).eq("attr_id", 2)).getId());
        pmsProductAttrValueService.updateById(pmsProductAttrValueEntity);
        //????????????????????????
        PmsProductAttrValueEntity pmsProductAttrValueEntity1 = new PmsProductAttrValueEntity();
        pmsProductAttrValueEntity1.setPrId(prId);
        pmsProductAttrValueEntity1.setAttrId(3L);
        pmsProductAttrValueEntity1.setAttrimplId(
                String.valueOf(pmsPubService.getOne(new QueryWrapper<PmsPubEntity>()
                        .eq("name", pmsProductEveryVo.getPub())).getPubId()));

        pmsProductAttrValueEntity1.setId(pmsProductAttrValueService.getOne(new QueryWrapper<PmsProductAttrValueEntity>()
                .eq("pr_id", prId).eq("attr_id", 3)).getId());
        pmsProductAttrValueService.updateById(pmsProductAttrValueEntity1);
        //????????????
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
     * TODO ????????????????????????????????????PrPrice
     *      ??????????????????SaleCount
     *      ??????????????????????????????
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
        //?????????????????????
        for (int i = 0; i < collect.size(); i++) {
            //???????????????????????????List?????????????????????????????????
            if (collect.get(i) == 1) {
                String sub = collect1.get(i);
                List<Long> categoryIds = Arrays.stream(sub.split(";")).map(Long::valueOf).collect(Collectors.toList());
                model.setCatelogIds(categoryIds);
                List<String> categoryNames = pmsCategoryService.listByIds(categoryIds).stream().map(PmsCategoryEntity::getName).collect(Collectors.toList());
                model.setCategpryNames(categoryNames);
                //?????????????????????????????????????????????????????????????????????????????????
            } else if (collect.get(i) == 2) {
                Long sub = Long.valueOf(collect1.get(i));
                model.setBrandId(sub);
                model.setBrandName(pmsBrandService.getById(sub).getName());
                //?????????????????????????????????????????????????????????????????????????????????
            } else if (collect.get(i) == 3) {
                Long sub = Long.valueOf(collect1.get(i));
                model.setPubId(sub);
                model.setPubName(pmsPubService.getById(sub).getName());
                //??????????????????????????????????????????????????????????????????????????????
            } else {
                Long sub = Long.valueOf(collect1.get(i));
                model.setSerieId(sub);
                model.setSerieName(pmsSeriesService.getById(sub).getName());
            }
        }
        //??????????????????????????????Feign
        R r = elSearchFeign.savePrModel(model);

        if (r.getCode() == 0) {
            //????????????,????????????
            this.updateById(product);
        }
    }

    @Override
    public PageUtils queryPageImpl(Map<String, Object> params) {
        IPage<PmsProductEntity> page = this.page(
                new Query<PmsProductEntity>().getPage(params),
                new QueryWrapper<>()
        );

        IPage<ProductImpl> pageImpl = new Page<>();
        BeanUtils.copyProperties(page, pageImpl);

        //??????????????????
        List<ProductImpl> listR = new ArrayList<>();
        List<PmsProductEntity> list = new ArrayList<>();
        Object key = null;
        //??????????????????????????????????????????????????????????????????
        if (params.get("key") != null && !params.get("key").equals("")) {
            key = params.get("key");
        }
        //???????????????????????????????????????
        Object category = null;
        if (params.get("category") != null && !params.get("category").equals("") && !params.get("category").equals("????????????")) {
            category = params.get("category");
        }
        //???????????????
        if (key != null && category == null) {
            list = this.list(new QueryWrapper<PmsProductEntity>().like("name", "%" + key + "%"));
        } else if (category != null) {
            Long catId = pmsCategoryService.getOne(new QueryWrapper<PmsCategoryEntity>().eq("name", category)).getCatId();
            //???????????????????????????????????????
            List<PmsProductAttrValueEntity> valueEntities = pmsProductAttrValueService.list(
                    new QueryWrapper<PmsProductAttrValueEntity>().eq("attr_id", 1).
                            like("attrImpl_id", catId + ";").or().
                            like("attrImpl_id", "%;" + catId + ";"));
            List<Long> collect = valueEntities.stream().map(PmsProductAttrValueEntity::getPrId).collect(Collectors.toList());

            //????????????????????????????????????????????????
            if (collect.size() != 0) {
                if (key == null) {
                    list = this.listByIds(collect);
                    //????????????????????????
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

        //???????????????????????????
        for (PmsProductEntity pmsProductEntity : list) {
            ProductImpl product = new ProductImpl();

            //???????????????????????????Vo
            BeanUtils.copyProperties(pmsProductEntity, product);
            String[] split = pmsProductEntity.getDescript().split("-");
            //???????????????????????????????????????????????????
            product.setVersion(split[0]);
            product.setGameDesc(split[1]);
            product.setDisposition(split[2]);

            List<String> categorys = new ArrayList<>();

            //?????????????????????????????????????????????
            String[] attrs = pmsProductAttrValueService.getOne(new QueryWrapper<PmsProductAttrValueEntity>()
                    .eq("pr_id", product.getPrId()).eq("attr_id", 2L)).getAttrimplId().split(";");

            //??????????????????????????????????????????
            for (String attr : attrs) {
                String menu = pmsCategoryService.getById(Long.valueOf(attr)).getName();
                categorys.add(menu);
            }
            //???????????????
            product.setCategorys(categorys);
            //????????????????????????
            listR.add(product);
        }
        //?????????????????????Page
        pageImpl.setRecords(listR);
        //??????????????????
        return new PageUtils(pageImpl);
    }

    @Override
    public ProductImpl infoImpl(Long prId) {
        //?????????????????????????????????????????????
        PmsProductEveryVo everyById = this.getEveryById(prId);
        ProductImpl product = new ProductImpl();
        //????????????
        BeanUtils.copyProperties(everyById,product);
        String[] split = everyById.getDescript().split("-");
        //???????????????????????????????????????????????????
        product.setVersion(split[0]);
        product.setGameDesc(split[1]);
        product.setDisposition(split[2]);
        //??????
        return product;
    }

}