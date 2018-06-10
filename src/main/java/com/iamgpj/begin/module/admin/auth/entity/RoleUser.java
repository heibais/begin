package com.iamgpj.begin.module.admin.auth.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: GPJ
 * @Description: 角色用户关联表
 * @Date Created in 16:51 2018/2/26
 * @Modified By:
 */
@Data
@Entity
@Table(name = "bg_sys_role_user")
public class RoleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** 角色id */
    private Integer roleId;
    /** 用户id */
    private Integer userId;

    public RoleUser() {

    }

    public RoleUser(Integer roleId, Integer userId) {
        this.roleId = roleId;
        this.userId = userId;
    }
}
