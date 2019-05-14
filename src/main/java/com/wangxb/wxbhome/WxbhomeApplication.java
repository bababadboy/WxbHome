package com.wangxb.wxbhome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
        (exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableScheduling
@ServletComponentScan   // filter配置必须
public class WxbhomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxbhomeApplication.class, args);
    }

}
