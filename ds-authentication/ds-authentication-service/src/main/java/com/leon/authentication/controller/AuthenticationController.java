package com.leon.authentication.controller;


import com.leon.authentication.entity.UserInfo;
import com.leon.authentication.properties.JwtProperties;
import com.leon.authentication.service.AuthenticationService;
import com.leon.authentication.utils.JwtUtils;
import com.leon.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LeonMac
 * @description
 */

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * @param username
     * @param password
     * @param request
     * @param response
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @description: 登录授权
     */
    @PostMapping("accredit")
    public ResponseEntity<Void> authentication(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response) {
        //登录校验
        String token = this.authenticationService.authentication(username, password);
        if (StringUtils.isBlank(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        //将token写入cookie
        CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getCookieMaxAge(), true);
        return ResponseEntity.ok().build();
    }

    /**
    * @description: 用户验证
    * @param token
	* @param request
	* @param response
    * @return org.springframework.http.ResponseEntity<com.leon.entity.UserInfo>
    */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verifyUser(@CookieValue("DS_TOKEN") String token,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {
        try {
            //1.从token中解析token信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            //2.解析成功要重新刷新token
            token = JwtUtils.generateToken(userInfo, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());
            //3.更新cookie中的token
            CookieUtils.setCookie(request, response, this.jwtProperties.getCookieName(), token, this.jwtProperties.getCookieMaxAge());
            //4.解析成功返回用户信息
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
