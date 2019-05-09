package com.wangxb.wxbhome.service;

import com.wangxb.wxbhome.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author wangxb
 */
@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public Object getUserInfoById(long id){
        return userDao.getUserById(id);
    }

    public Object getUserInfoByStudentId(long studentId){
        return userDao.getUserByStudentId(studentId);
    }

    public Object login(String nickname,String password){
        System.out.println(nickname);
        System.out.println(password);
        String expectedPassword = userDao.getPasswordByNickName(nickname);
        return password.equals(expectedPassword);
    }

}
