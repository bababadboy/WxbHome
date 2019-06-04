package com.wangxb.wxbhome.dao;

import com.wangxb.wxbhome.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangxb
 */

@Mapper
public interface PermissionDao {

    /**
     * 根据用户id查询permissions权限
     * @param id 用户id
     * @return  permission列表
     */
    List<Permission> getPermissionsByUserId(@Param("id") long id);

    /**
     * 根据url获取权限列表
     * @param url 资源路径
     * @return 权限列表
     */
    List<Permission> getPermissionsByUrls(@Param("url") String url);
}
