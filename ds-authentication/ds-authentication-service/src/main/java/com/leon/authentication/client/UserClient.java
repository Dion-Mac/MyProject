package com.leon.authentication.client;

import com.leon.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author LeonMac
 * @description
 */

@FeignClient(value = "ds-user")
public interface UserClient extends UserApi {
}
