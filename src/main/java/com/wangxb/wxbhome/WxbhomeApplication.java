package com.wangxb.wxbhome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
        (exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class WxbhomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxbhomeApplication.class, args);
    }

}
