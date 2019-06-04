package com.wangxb.wxbhome.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wangxb
 */
@Controller
public class PageController {

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")  // 只有admin权限的用户才能访问
    public String admin(){
        return "/admin";
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('hello')")
    public String hello(){
        return "/hello";
    }
}
