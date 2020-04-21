package com.leon.authentication.service;

/**
 * @author LeonMac
 * @description
 */

public interface AuthenticationService {
    /**
     * @description: 用户授权
     * @param username
     * @param password
     * @return java.lang.String
     */
    String authentication(String username, String password);
}
