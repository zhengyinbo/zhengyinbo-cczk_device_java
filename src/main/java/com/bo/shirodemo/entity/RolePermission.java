package com.bo.shirodemo.entity;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.io.Serializable;

/**
 * 角色权限表
 * @Author bo
 * @DATE 2019/12/23
 **/

@Entity
@Data
public class RolePermission implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private Long roleId;

    private Long permissionId;

}
