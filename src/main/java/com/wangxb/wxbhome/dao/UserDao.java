package com.wangxb.wxbhome.dao;

import com.wangxb.wxbhome.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    Integer addUser(@Param("user") User user);

    Integer nicknameCounts(@Param("nickname") String nickname);

    Integer emailCounts(@Param("email") String email);

    List<User> getAllUsers();

    long getCountAllUsers();

    List<User> getUsers(@Param("startId") long startId,@Param("limit") int limit);

    long getIdByNickname(@Param("nickname") String nickname);
}
