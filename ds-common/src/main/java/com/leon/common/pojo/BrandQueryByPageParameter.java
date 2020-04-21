package com.leon.common.pojo;

/**
 * @author LeonMac
 * @description
 */

public class BrandQueryByPageParameter {

    private Integer page;   //当前页
    private Integer rows;   //每页大小
    private String sortBy;  //排序字段
    private Boolean desc;   //是否降序
    private String key; //搜索关键字

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDesc() {
        return desc;
    }

    public void setDesc(Boolean desc) {
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BrandQueryByPageParameter(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        this.page = page;
        this.rows = rows;
        this.sortBy = sortBy;
        this.desc = desc;
        this.key = key;
    }

    public BrandQueryByPageParameter() {
        super();
    }

    @Override
    public String toString() {
        return "BrandQueryByPageParameter{" +
                "page=" + page +
                ", rows=" + rows +
                ", sortBy='" + sortBy + '\'' +
                ", desc=" + desc +
                ", key='" + key + '\'' +
                '}';
    }
}
