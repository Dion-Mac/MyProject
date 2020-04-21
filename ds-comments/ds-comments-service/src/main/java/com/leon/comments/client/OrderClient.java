package com.leon.comments.client;

import com.leon.order.api.OrderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LeonMac
 * @description 订单接口
 */
@FeignClient(value = "order-service")
public interface OrderClient extends OrderApi {
}
