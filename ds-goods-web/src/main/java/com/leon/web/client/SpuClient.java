package com.leon.web.client;

import com.leon.item.api.SpuApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LeonMac
 * @description Spu FeignClient
 */

@FeignClient(value = "ds-item-service")
public interface SpuClient extends SpuApi {
}
