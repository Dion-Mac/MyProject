package com.leon.web.client;

import com.leon.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LeonMac
 * @description 规格FeignClient
 */

@FeignClient(value = "ds-item-service")
public interface SpecificationClient extends SpecificationApi {
}
