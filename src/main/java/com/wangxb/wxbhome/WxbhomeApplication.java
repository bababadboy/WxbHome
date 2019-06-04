package com.wangxb.wxbhome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
//        (exclude = {
//        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
//})
@EnableRedisHttpSession
@EnableScheduling
@ServletComponentScan   // filter配置必须
public class WxbhomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxbhomeApplication.class, args);
    }

}
