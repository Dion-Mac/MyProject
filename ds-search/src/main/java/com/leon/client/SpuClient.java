package com.leon.client;

import com.leon.item.api.SpuApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LeonMac
 * @description spu FeignClient
 */
@FeignClient(value = "ds-item-service")
public interface SpuClient extends SpuApi {
}
