package com.leon.comments.config;


import com.leon.comments.interceptor.LoginInterceptor;
import com.leon.comments.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LeonMac
 * @description
 */
@Configuration
@EnableWebMvc
//@EnableConfigurationProperties(JwtProperties.class)
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtProperties jwtProperties;

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor(jwtProperties);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludePath = new ArrayList<>();
        excludePath.add("/list");
        excludePath.add("/commentId/**");
        excludePath.add("/comment");
        excludePath.add("/visit/**");
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**").excludePathPatterns(excludePath);
    }

}
