package com.example.common.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PrModel {

    private Long prId;

    private String prName;

    private BigDecimal prPrice;

    private String prImage;

    private Long saleCount;

    private Boolean hasStock;

    private List<Long> catelogIds;

    private List<String> categpryNames;

    private Long brandId;

    private String brandName;

    private Long pubId;

    private String pubName;

    private Long serieId;

    private String serieName;

    public Long getPrId() {
        return prId;
    }

    public void setPrId(Long prId) {
        this.prId = prId;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public BigDecimal getPrPrice() {
        return prPrice;
    }

    public void setPrPrice(BigDecimal prPrice) {
        this.prPrice = prPrice;
    }

    public String getPrImage() {
        return prImage;
    }

    public void setPrImage(String prImage) {
        this.prImage = prImage;
    }

    public Long getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Long saleCount) {
        this.saleCount = saleCount;
    }

    public Boolean getHasStock() {
        return hasStock;
    }

    public void setHasStock(Boolean hasStock) {
        this.hasStock = hasStock;
    }

    public List<Long> getCatelogIds() {
        return catelogIds;
    }

    public void setCatelogIds(List<Long> catelogIds) {
        this.catelogIds = catelogIds;
    }

    public List<String> getCategpryNames() {
        return categpryNames;
    }

    public void setCategpryNames(List<String> categpryNames) {
        this.categpryNames = categpryNames;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getPubId() {
        return pubId;
    }

    public void setPubId(Long pubId) {
        this.pubId = pubId;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public Long getSerieId() {
        return serieId;
    }

    public void setSerieId(Long serieId) {
        this.serieId = serieId;
    }

    public String getSerieName() {
        return serieName;
    }

    public void setSerieName(String serieName) {
        this.serieName = serieName;
    }
}
