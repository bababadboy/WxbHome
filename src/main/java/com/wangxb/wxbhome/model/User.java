package com.wangxb.wxbhome.model;

import com.wangxb.wxbhome.annotation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 普通用户model
 * @author wangxb
 */
@Data
public class User implements Serializable {
    private long userId;

    @NotNull
    @Size(min = 5,max = 16)
    private String nickname;

    private String realName;
    private long studentId;

    @NotNull
    @Size(min = 6,max = 18)
    private String password;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;
    private long collegeId;
    private int sex;

    public User(@NotNull @Size(min = 5, max = 16) String nickname, @NotNull @Size(min = 6, max = 18) String password, @NotNull String email) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }
}
