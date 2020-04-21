package com.leon.order.vo;


import com.leon.comments.pojo.Review;

/**
 * @author LeonMac
 * @description 新增评论消息对象
 */

public class CommentsParameter {

    private Long orderId;

    private Review review;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
