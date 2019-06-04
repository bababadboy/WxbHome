package com.wangxb.wxbhome.authentication;

import com.wangxb.wxbhome.dao.PermissionDao;
import com.wangxb.wxbhome.dao.RoleDao;
import com.wangxb.wxbhome.dao.UserDao;
import com.wangxb.wxbhome.model.Permission;
import com.wangxb.wxbhome.model.Role;
import com.wangxb.wxbhome.model.User;
import com.wangxb.wxbhome.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxb
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = securityService.findUserByNickname(s);

        if (null == user){
            throw new UsernameNotFoundException("没有此用户");
        }

        log.info("用户nickname为{}的信息是{}",s,user);
        List<Permission> permissionList = securityService.findPermissionsByUserId(user.getUserId());
        log.info("用户nickname为{}的permissionList：{}",s,permissionList);
        List<Role> roleList = securityService.findRolesByUserId(user.getUserId());
        log.info("用户nickname为{}的roleList：{}",s,roleList);

        MyUserDetails userDetails = new MyUserDetails(user,roleList,permissionList);
        return userDetails;
    }
}
