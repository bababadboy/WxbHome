package com.wangxb.wxbhome.controller;

import com.wangxb.wxbhome.dto.MessageResponse;
import com.wangxb.wxbhome.model.User;
import com.wangxb.wxbhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 普通用户controller接口
 * @author wangxb
 */

@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "getAll")
    public MessageResponse getAllUsers(){
        MessageResponse response = new MessageResponse();
        Object userInfo = userService.getAllUsers();
        response.setContent(userInfo);
        return response;
    }

    @GetMapping(value = "getAllByMutiplyThreads")
    public MessageResponse getAllUsersByMutiplyThreads() throws ExecutionException, InterruptedException {
        MessageResponse response = new MessageResponse();
        Object userInfo = userService.getAllUsersByMutiplyThreads(0);
        response.setContent(userInfo);
        return response;
    }

    @GetMapping(value = "info")
    public MessageResponse getUserInfoById(@RequestParam("id") long id){
        MessageResponse response = new MessageResponse();
        Object userInfo = userService.getUserInfoById(id);
        response.setContent(userInfo);
        return response;
    }

    /**
     * 根据学号返回用户信息
     * @param studentId 学号
     * @return 返回 MessageResponse dto
     */
    @GetMapping(value = "studentInfo")
    public MessageResponse getUserInfoByStudentId(@RequestParam("studentId") long studentId){
        MessageResponse response = new MessageResponse();
        Object userInfo = userService.getUserInfoByStudentId(studentId);
        response.setContent(userInfo);
        return response;
    }



    @PostMapping(value = "signin")
    public MessageResponse signin(){
        return null;
    }

    @RequestMapping(value = "session")
    public Map<String, Object> getSession(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<>();
        // 这里的session.getId为什么不是空的呢？
        // 因为创建了一个新的session，可以指定参数 为 false禁止创建新的session

        // 服务器会通过sessionId判断服务器内存是否存在相关session
        HttpSession session = request.getSession(false);
        if (session == null){
            System.out.println("服务器不存在session,创建新的session ！");
            session = request.getSession(true);
            String sessionId = session.getId();
            // 把sessionId设为cookie的值
            Cookie cookie = new Cookie("sessionId",sessionId);
            // 86400s == 24 h
            cookie.setMaxAge(86400);
            response.addCookie(cookie);

            map.put("cookie", cookie);


        }
        else {
            System.out.println("服务器存在指定session");
            Cookie cookie = (Cookie) session.getAttribute("cookie");
            // 重新设置cookie的到期时间
            cookie.setMaxAge(86400);
        }
        return map;
    }

    @RequestMapping(value = "get")
    public String get(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("userName");
    }

    @GetMapping(value = "hello")
    public String hello(@CookieValue(value = "hitCounter" ,defaultValue = "0") long hitCounter,
                        HttpServletResponse response){
        hitCounter ++;
        Cookie cookie = new Cookie("hitCounter",String.valueOf(hitCounter));
        response.addCookie(cookie);
        return "hitCounter"+ hitCounter;
    }

}
