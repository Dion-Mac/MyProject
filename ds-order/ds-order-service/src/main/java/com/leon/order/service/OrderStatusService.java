package com.leon.order.service;

import com.leon.order.vo.CommentsParameter;
import com.leon.order.vo.OrderStatusMessage;

/**
 * @author LeonMac
 * @description
 */
public interface OrderStatusService {


    /**
     * 发送消息到延时队列
     * @param orderStatusMessage
     */
    void sendMessage(OrderStatusMessage orderStatusMessage);

    /**
     * 发送评论信息
     * @param commentsParameter
     */
    void sendComments(CommentsParameter commentsParameter);
}
