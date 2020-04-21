package com.leon.config.myconfig;

import com.leon.config.filter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.FilterRegistration;

/**
 * @author LeonMac
 * @description
 */

public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加写好的过滤器
        registration.setFilter( new MyFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/*");
        return registration;
    }
}
