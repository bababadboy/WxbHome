package com.wangxb.wxbhome.model;

import lombok.Data;

/**
 * 普通用户model
 * @author wangxb
 */
@Data
public class User {
    private long userId;
    private String nickname;
    private String realName;
    private long studentId;
    private String password;
    private String email;
    private long collegeId;
    private int sex;
}
