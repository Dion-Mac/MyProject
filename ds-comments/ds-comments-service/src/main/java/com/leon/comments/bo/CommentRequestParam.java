package com.leon.comments.bo;

/**
 * @author LeonMac
 * @description
 */
public class CommentRequestParam {
    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页大小，不从页面接收，而是固定大小
     */
    private static final Integer DEFAULT_SIZE = 20;

    /**
     * 默认页
     */
    private static final Integer DEFAULT_PAGE = 1;

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Integer getPage() {
        if (page == null){
            return DEFAULT_PAGE;
        }
        /**
         * 获取页码时做一些校验，不能小于1
         */
        return Math.max(DEFAULT_PAGE,page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getDefaultSize() {
        return DEFAULT_SIZE;
    }

}
