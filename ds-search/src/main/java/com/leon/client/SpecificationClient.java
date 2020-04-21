package com.leon.client;

import com.leon.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LeonMac
 * @description
 */
@FeignClient(value = "ds-item-service")
public interface SpecificationClient extends SpecificationApi {
}
