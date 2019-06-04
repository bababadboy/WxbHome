package com.wangxb.wxbhome.dao;

import com.wangxb.wxbhome.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangxb
 */
@Mapper
public interface RoleDao {

    /**
     * 根据用户id查询角色
     * @param id 用户id
     * @return 角色列表
     */
    List<Role> getRolesByUserId(@Param("id") long id);


    /**
     * 根据url（资源路径）查询角色
     * @param url 资源路径
     * @return 角色列表
     */
    List<Role> getRolesByUrl(@Param("url") String url);

    List<Role> getRolesByNickName(@Param("name") String nickName);
}
