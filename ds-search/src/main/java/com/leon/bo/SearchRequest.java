package com.leon.bo;

import java.util.Map;

/**
 * @author LeonMac
 * @description 搜索业务对象
 */

public class SearchRequest {
    private String key; //搜索条件
    private Integer page;   //当前页
    private String sortBy;  //排序字段
    private Boolean descending; //是否降序
    private Map<String,String> filter;  //过滤字段
    private static final Integer DEFAULT_SIZE = 20; //每页大小
    private static final Integer DEFAULT_PAGE = 1;  //默认页

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getDefaultSize() {
        return DEFAULT_SIZE;
    }

    public Integer getPage() {
        if (page == null){
            return DEFAULT_PAGE;
        }
        //获取页码时做些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "key='" + key + '\'' +
                ", page=" + page +
                ", sortBy='" + sortBy + '\'' +
                ", descending=" + descending +
                ", filter=" + filter +
                '}';
    }
}
