package com.leon.secondkill.client;

import com.leon.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

//商品FeignClient
@FeignClient(value = "ds-item-service")
public interface GoodsClient extends GoodsApi {
}
