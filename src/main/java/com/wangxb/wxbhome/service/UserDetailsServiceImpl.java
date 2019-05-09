package com.wangxb.wxbhome.service;

import com.wangxb.wxbhome.dao.UserDao;
import com.wangxb.wxbhome.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author wangxb
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) {
        User user = userDao.getUserByNickName(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }// todo

        return null;
    }
}
