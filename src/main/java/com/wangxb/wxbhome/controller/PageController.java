package com.wangxb.wxbhome.controller;

import com.wangxb.wxbhome.authentication.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;

/**
 * @author wangxb
 */
@Controller
public class PageController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")  // 只有admin权限的用户才能访问
    public String admin(){
        return "/admin";
    }

    @GetMapping("/authenticated")
    @PreAuthorize("isAuthenticated()")
    public String hello(){
        return "/authenticated";
    }

    @RequestMapping(value = "/login")
    public String login(){
        // 如果登录状态则直接重定向到index
        return "login";
    }

    @RequestMapping(value = "/password_reset")
    public String passwordReset(HashMap<String,Object> map, String urlarg){
        return "password_reset";
    }

    @RequestMapping(value = {"index","/"},method = RequestMethod.GET)
    public String getIndex(){
        return "index";
    }

    @RequestMapping(value = "join")
    public String join(){
        return "join";
    }

}
