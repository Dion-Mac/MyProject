package com.leon.cart.client;

import com.leon.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LeonMac
 * @description
 */

@FeignClient(value = "ds-item-service")
public interface GoodsClient extends GoodsApi {
}
