package com.leon.authentication.service.impl;


import com.leon.authentication.client.UserClient;
import com.leon.authentication.entity.UserInfo;
import com.leon.authentication.properties.JwtProperties;
import com.leon.authentication.service.AuthenticationService;
import com.leon.user.pojo.User;

import com.leon.authentication.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LeonMac
 * @description
 */

public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * @description: 用户授权
     * @param username
     * @param password
     * @return java.lang.String
     */
    @Override
    public String authentication(String username, String password) {

        try {
            //1.调用微服务查询用户信息
            User user = this.userClient.queryUser(username, password);
            //2.查询结果为空，则直接返回null
            if (user == null) {
                return null;
            }
            //3.查询结果不为空，则生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()), jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

