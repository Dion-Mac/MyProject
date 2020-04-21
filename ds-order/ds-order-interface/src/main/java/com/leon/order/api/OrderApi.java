package com.leon.order.api;

import com.leon.order.pojo.Order;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author LeonMac
 * @description 订单服务接口
 */

@RequestMapping("order")
public interface OrderApi {

    /**
    * @description: 创建订单
    * @param seck
	* @param order
    * @return java.util.List<java.lang.Long>
    */
    @PostMapping
    List<Long> createOrder(@RequestParam("seck") String seck, @RequestBody @Valid Order order);

    /**
    * @description: 修改订单状态
    * @param id
	* @param status
    * @return java.lang.Boolean
    */
    @PutMapping("{id}/{status}")
    Boolean updateOrderStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status);
}
