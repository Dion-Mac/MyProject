package com.leon.web.client;

import com.leon.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LeonMac
 * @description 分类FeignClient
 */

@FeignClient(value = "ds-item-service")
public interface CategoryClient extends CategoryApi {
}
