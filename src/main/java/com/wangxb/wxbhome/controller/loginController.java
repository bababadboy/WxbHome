package com.wangxb.wxbhome.controller;

import com.wangxb.wxbhome.dto.MessageResponse;
import com.wangxb.wxbhome.exception.ValidateCodeException;
import com.wangxb.wxbhome.model.User;
import com.wangxb.wxbhome.service.UserService;
import com.wangxb.wxbhome.util.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

/**
 * @author wangxb
 */
@Controller
public class loginController {

    @Autowired
    private UserService userService;

    /*获取静态html的页面资源*/

    /* 请求action*/
//    @RequestMapping(value = "/user-logout")
//    public String logout(HttpSession httpSession){
//        httpSession.removeAttribute("user");
//        return "index";
//    }
//
//    @RequestMapping(value = "logout")
//    public String logout(){
//        return "index";
//    }

//    @PostMapping(value = "user-login")
//    public String login(@ModelAttribute(value = "user") User user, Model model,HttpSession session){
//        boolean verify = (boolean)userService.login(user.getNickname(),user.getPassword());
//        model.addAttribute("user",user);
//        if (verify){
//            model.addAttribute("result","success");
//            session.setAttribute("user",user);
//            return "redirect:index";
//        }
//        else{
//            return "redirect:login";
//        }
//    }

    /**
     * 注意：Errors参数必须在@Valid参数后面
     */
    @PostMapping(value = "user-signup")
    public String signup(@ModelAttribute(value = "user") @Valid User user,
                         BindingResult result,Model model,  HttpServletRequest request){
        if (result.hasErrors()){
            // 如果@Valid验证出错，则重新提交注册信息
            return "redirect:join?invalid_user";
        }

        try{
            VerificationCodeUtil.checkVerifyCode(request);
        }catch (ValidateCodeException e){
            return "redirect:join?invalid_code";
        }

        MessageResponse msg = (MessageResponse) userService.signup(user);

        if ("success".equals(msg.getStatus())){
            model.addAttribute("nickname",user.getNickname());
            model.addAttribute("id",user.getUserId());

            return "redirect:login?source=join";
        }
        else {
            return "redirect:join";
        }

    }
}
