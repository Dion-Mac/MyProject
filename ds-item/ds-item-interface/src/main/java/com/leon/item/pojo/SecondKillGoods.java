package com.leon.item.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author LeonMac
 * @description 秒杀商品对象
 */

@Table(name = "tb_seckill_sku")
public class SecondKillGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 秒杀商品的id
     */
    private Long skuId;
    /**
     * 秒杀开始时间
     */
    private Date startTime;
    /**
     * 秒杀结束时间
     */
    private Date endTime;
    /**
     * 秒杀价格
     */
    private Double secondKillPrice;
    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 是否可以秒杀
     */
    private Boolean enable;

    /**
     * 秒杀库存
     */
    @JsonIgnore
    @Transient
    private Integer stock;

    @JsonIgnore
    @Transient
    private Integer secondKillTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getSecondKillPrice() {
        return secondKillPrice;
    }

    public void setSecondKillPrice(Double secondKillPrice) {
        this.secondKillPrice = secondKillPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSecondKillTotal() {
        return secondKillTotal;
    }

    public void setSecondKillTotal(Integer secondKillTotal) {
        this.secondKillTotal = secondKillTotal;
    }

    @Override
    public String toString() {
        return "SecondKillGoods{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", secondKillPrice=" + secondKillPrice +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", enable=" + enable +
                ", stock=" + stock +
                ", secondKillTotal=" + secondKillTotal +
                '}';
    }
}
