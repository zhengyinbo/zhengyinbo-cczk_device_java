package com.bo.shirodemo.entity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @Author bo
 * @DATE 2019/12/23
 **/

@Entity
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long userId;

    private String userName;

    private String password;

    private String remark;

    // 用户状态：1-正常，2-禁用
    private String status;

    // 用户类型
    private String userType;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

}
