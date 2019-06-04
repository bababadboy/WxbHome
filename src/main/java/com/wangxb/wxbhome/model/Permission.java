package com.wangxb.wxbhome.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxb
 */
@Data
public class Permission implements Serializable {
    private int id;
    private String permissionName;
    private String url;
    private String description;
}
