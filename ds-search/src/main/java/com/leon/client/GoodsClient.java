package com.leon.client;

import com.leon.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LeonMac
 * @description 商品FeignClient
 */
@FeignClient(value = "ds-item-service")
public interface GoodsClient extends GoodsApi {
}
