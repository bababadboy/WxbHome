package com.wangxb.wxbhome.authentication;

import com.wangxb.wxbhome.model.Permission;
import com.wangxb.wxbhome.model.Role;
import com.wangxb.wxbhome.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户信息的封装，包含用户名称密码及用户状态、权限等信息
 * @author wangxb
 */
@Slf4j
public class MyUserDetails extends User implements UserDetails {

    //用户角色列表
    private List<Role> roleList;
    //用户资源权限列表
    private List<Permission> permissionList;

    public MyUserDetails(User user,List<Role> roleList, List<Permission> permissionList) {
        super(user.getNickname(),user.getPassword(),user.getEmail());
        this.roleList = roleList;
        this.permissionList = permissionList;
    }


    /**
     * 获取用户权限列表方法
     * 可以理解成，返回了一个List<String>，之后所谓的权限控制、鉴权，其实就是跟这个list里的String进行对比
     * 这里处理了角色和资源权限两个列表，可以这么理解，
     * 角色是权限的抽象集合，是为了更方便的控制和分配权限，而真正颗粒化细节方面，还是需要资源权限自己来做
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色
        for (Role r : this.getRoleList()) {
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        }
        // 权限
        for (Permission p: this.getPermissionList()){
            authorities.add(new SimpleGrantedAuthority(p.getPermissionName()));
        }

        for (GrantedAuthority g : authorities) {
            log.info("用户{}的权限有{}",this.getNickname(),g);
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return getNickname();
    }

    /**
     * 判断账号是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 判断账号是否被锁定,Nonlocked = true:没被锁定啊啊啊
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 判断信用凭证是否过期，默认没有过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 判断账号是否可用，默认可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /** getter and setter **/
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
