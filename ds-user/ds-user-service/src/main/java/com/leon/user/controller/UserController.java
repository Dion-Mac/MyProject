package com.leon.user.controller;

import com.leon.user.pojo.User;
import com.leon.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author LeonMac
 * @description
 */

public class UserController {


    @Autowired
    private UserService userService;

    /**
     * @param data
     * @param type
     * @return org.springframework.http.ResponseEntity<java.lang.Boolean>
     * @description: 用户数据检查
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data, @PathVariable(value = "type") Integer type) {
        Boolean result = this.userService.checkData(data, type);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * @param phone
     * @return org.springframework.http.ResponseEntity
     * @description: 发送短信验证码
     */
    @PostMapping("code")
    public ResponseEntity sendVerifyCode(@RequestParam("phone") String phone) {
        Boolean result = this.userService.sendVerifyCode(phone);
        if (result == null || !result) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * @param user
     * @param code
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @description: 注册
     */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code) {
        Boolean result = this.userService.register(user, code);
        if (result == null || !result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * @param username
     * @param password
     * @return org.springframework.http.ResponseEntity<com.leon.order.pojo.User>
     * @description: 用户验证
     */
    @GetMapping("query")
    @ResponseBody
    public User queryUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = this.userService.queryUser(username, password);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(user);
        return user;
    }

    /**
    * @description: 修改密码
    * @param username
	* @param oldPassword
	* @param newPassword
    * @return org.springframework.http.ResponseEntity
    */
    @PutMapping("update")
    public ResponseEntity<Boolean> updatePassword(@RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        Boolean result = this.userService.updatePassword(username, oldPassword, newPassword);
        if (result == null || !result) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(result);
    }
}
