package com.bo.shirodemo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author yb zheng
 * @Date 2025/7/9 17:15
 * @Version 1.0
 */

@Data
public class UserVo {

    public Long userId;

    private String userName;

    private String password;

    private String remark;

    // 用户状态：1-正常，2-禁用
    private String status;

    // 用户类型
    private String userType;

    private Date createTime;

    private Date updateTime;

}
