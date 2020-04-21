package com.leon.item.bo;

import com.leon.item.pojo.Sku;
import com.leon.item.pojo.Spu;
import com.leon.item.pojo.SpuDetail;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author LeonMac
 * @description
 */

public class SpuBo extends Spu {

    @Transient
    String cname;   //商品分类名称
    @Transient
    String bname;   //品牌名称
    @Transient
    SpuDetail spuDetail;    //商品详情
    @Transient
    List<Sku> skus; //sku列表

    public SpuBo() {
    }

    public SpuBo(Long brandId, Long cid1, Long cid2, Long cid3, String title, String subTitle, Boolean saleable, Boolean valid, Date createTime, Date lastUpdateTime) {
        super(brandId, cid1, cid2, cid3, title, subTitle, saleable, valid, createTime, lastUpdateTime);
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

}
