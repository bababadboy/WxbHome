package com.wangxb.wxbhome.util;

import com.wangxb.wxbhome.exception.ValidateCodeException;
import org.apache.catalina.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.thymeleaf.util.Validate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 验证码验证工具类
 * @author wangxb
 */
public class VerificationCodeUtil {
    /**
     * 将获取到的前端参数转为string类型
     */
    private static String getString(HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if(result != null) {
                result = result.trim();
            }
            if("".equals(result)) {
                result = null;
            }
            return result;
        }catch(Exception e) {
            return null;
        }
    }

    /**
     * 验证码校验
     */
    public static void checkVerifyCode(HttpServletRequest request) {
        //获取生成的验证码
        String verifyCodeExpected = (String) request.getSession().
                getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

        if (null == verifyCodeExpected){
            throw new ValidateCodeException("验证码不存在");
        }
        //获取用户输入的验证码
        String verifyCodeActual = VerificationCodeUtil.getString(request, "verifyCodeActual");

        if (null == verifyCodeActual){
           throw new ValidateCodeException("验证码不能为空");
        }
        else if (!verifyCodeActual.equals(verifyCodeExpected)){
            throw new ValidateCodeException("验证码不正确");
        }
    }
}
