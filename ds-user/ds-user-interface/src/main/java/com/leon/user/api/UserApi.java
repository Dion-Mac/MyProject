package com.leon.user.api;

import com.leon.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author LeonMac
 * @description 用户服务接口
 */

public interface UserApi {

    /**
    * @description:
    * 用于用户验证
    * @param username
	* @param password
    * @return com.leon.order.pojo.User
    */
    @GetMapping("query")
    User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);
}
