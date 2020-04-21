package com.leon.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author LeonMac
 * @description
 * 整个ds-item中，只有ds-item-service是需要启动的，
 * 所以只在这里编写启动类。
 */

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leon.item.mapper") //mapper接口的包扫描
public class ItemServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemServiceApplication.class, args);
    }
}
