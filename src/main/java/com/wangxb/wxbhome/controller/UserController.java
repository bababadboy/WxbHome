package com.wangxb.wxbhome.controller;

import com.wangxb.wxbhome.dto.MessageResponse;
import com.wangxb.wxbhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 普通用户controller接口
 * @author wangxb
 */

@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据用户id,返回用户信息
     * @param id 用户id
     * @return 返回 MessageResponse dto
     */
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

    /**
     * 登录
     * @param
     * @return 登录是否成功信息
     */
    @PostMapping(value = "login")
    public MessageResponse login(String nickname, String password){
        MessageResponse response = new MessageResponse();
        boolean content = (boolean)userService.login(nickname,password);
        response.setContent(content);
        return response;
    }

    @PostMapping(value = "signin")
    public MessageResponse signin(){
        return null;
    }
}
