package com.leon.common.pojo;

import java.util.List;

/**
 * @author LeonMac
 * @description 通用页面结果类
 */

public class PageResult<T> {
    private Long total;//总条数
    private Integer totalPage;//总页数
    private List<T> items;//当前页数据

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, List<T> items, Integer totalPage) {
        this.total = total;
        this.items = items;
        this.totalPage = totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
