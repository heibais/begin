package com.iamgpj.begin.module.admin.auth.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: GPJ
 * @Description: 角色权限关联表
 * @Date Created in 16:51 2018/2/8
 * @Modified By:
 */
@Data
@Entity
@Table(name = "bg_sys_role_permission")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** 角色主键 */
    private Integer roleId;
    /** 权限主键 */
    private Integer permissionId;

    public RolePermission() {
    }

    public RolePermission(Integer roleId, Integer permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
}
