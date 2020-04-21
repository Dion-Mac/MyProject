package com.leon.client;

import com.leon.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LeonMac
 * @description 品牌FeignClient
 */
@FeignClient(value = "ds-item-service")
public interface BrandClient extends BrandApi {
}
