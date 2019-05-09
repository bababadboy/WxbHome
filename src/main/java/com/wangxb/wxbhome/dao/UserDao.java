package com.wangxb.wxbhome.dao;

import com.wangxb.wxbhome.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户dao层
 * @author wangxb
 */

@Mapper
public interface UserDao {

    Object getUserById(@Param("id") Long id);

    Object getUserByStudentId(@Param("studentId") Long studentId);

    User getUserByNickName(@Param("nickname") String nickname);

    String getPasswordByNickName(@Param("nickname") String nickname);
}
