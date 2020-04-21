package com.leon.order.mapper;

import com.leon.order.pojo.Order;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @author LeonMac
 * @description
 */
public interface OrderMapper extends Mapper<Order> {
    /**
     * 分页查询订单
     * @param userId
     * @param status
     * @return
     */
    List<Order> queryOrderList(@Param("userId") Long userId, @Param("status") Integer status);
}
