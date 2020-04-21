package com.leon.authentication.utils;

/**
 * @author LeonMac
 * @description
 * @date 2020/4/9
 */

public abstract class JwtConstaints {
    //为什么常量要用static修饰？
    //在创建类的多个对象时，用static修饰的常量在内存中只有一份拷贝。
    public static final String JWT_KEY_ID = "id";
    public static final String JWT_KEY_USER_NAME = "username";
}
