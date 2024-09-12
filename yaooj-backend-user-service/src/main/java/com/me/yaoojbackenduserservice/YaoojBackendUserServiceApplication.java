package com.me.yaoojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Me.
 * @version 0.0.1
 * @createTime 2024/9/10 19:01
 * @from <a href="https://github.com/Me-60">Me.</a>
 * @blog <a href="https://www.cnblogs.com/Me60">博客园</a>
 */
@SpringBootApplication
@MapperScan("com.me.yaoojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.me")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.me.yaoojbackendserviceclient.service")
public class YaoojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YaoojBackendUserServiceApplication.class, args);
    }
}
