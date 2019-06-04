package com.wangxb.wxbhome.service;

import com.wangxb.wxbhome.dao.PermissionDao;
import com.wangxb.wxbhome.dao.RoleDao;
import com.wangxb.wxbhome.dao.UserDao;
import com.wangxb.wxbhome.dto.UserDto;
import com.wangxb.wxbhome.model.Permission;
import com.wangxb.wxbhome.model.Role;
import com.wangxb.wxbhome.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxb
 */
@Service
public class SecurityService {

    @Resource
    private PermissionDao permissionDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private UserDao userDao;

    public User findUserByNickname(String nickname){
        return userDao.getUserByNickName(nickname);
    }

    public List<Role> findRolesByUserId(long id){
        return roleDao.getRolesByUserId(id);
    }

    public List<Role> findRolesByUrl(String url){
        return roleDao.getRolesByUrl(url);
    }

    public List<Permission> findPermissionsByUserId(long id){
        return permissionDao.getPermissionsByUserId(id);
    }

    public List<Permission> findPermissionsByUrl(String url){
        return permissionDao.getPermissionsByUrls(url);
    }
}
