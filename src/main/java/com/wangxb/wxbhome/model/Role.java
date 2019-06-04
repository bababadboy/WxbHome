package com.wangxb.wxbhome.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxb
 */
@Data
public class Role implements Serializable {
    private int id;
    private String roleName;
    private String description;
}
