package com.wangxb.wxbhome.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author wangxb
 */
public class UserDto implements Serializable {
    private String nickname;
    private String password;
    private String email;
}
