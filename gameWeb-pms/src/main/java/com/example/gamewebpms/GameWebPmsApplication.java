package com.example.gamewebpms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
@EnableFeignClients("com.example.gamewebpms.feign")
public class GameWebPmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameWebPmsApplication.class, args);
    }

}
