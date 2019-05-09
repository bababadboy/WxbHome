package com.wangxb.wxbhome.dto;

import lombok.Data;

/**
 * 通用返回类
 * @author wangxb
 */
@Data
public class MessageResponse {
    private String status = "success";
    private Object content;
}
