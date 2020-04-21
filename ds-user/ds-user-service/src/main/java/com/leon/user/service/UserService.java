package com.leon.user.service;

import com.leon.user.pojo.User;

/**
 * @author LeonMac
 * @description
 */

public interface UserService {
    /*
    * @description:
    * 检查用户名和手机号是否可用
    * @param Data
	* @param type
    * @return java.lang.Boolean
    */
    Boolean checkData(String Data, Integer type);

    /*
    * @description:
    * 发送手机验证码
    * @param phone
    * @return java.lang.Boolean
    */
    Boolean sendVerifyCode(String phone);

    /*
    * @description:
    * 用户注册
    * @param user
	* @param code
    * @return java.lang.Boolean
    */
    Boolean register(User user, String code);

    /*
    * @description:
    * 用户验证
    * @param username
	* @param password
    * @return com.leon.order.pojo.User
    */
    User queryUser(String username, String password);

    /*
    * @description:
    * 根据用户名修改密码
    * @param username
	* @param oldPassword
	* @param newPassword
    * @return java.lang.Boolean
    */
    Boolean updatePassword(String username, String oldPassword, String newPassword);
}
