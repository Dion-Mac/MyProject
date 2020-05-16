package com.leon.secondkill.client;


import com.leon.order.api.OrderApi;
import com.leon.secondkill.config.OrderConfig;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value = "ds-order-service",configuration = OrderConfig.class)
public interface OrderClient extends OrderApi {
}
