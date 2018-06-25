package com.iamgpj.begin.module.admin.auth.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: GPJ
 * @Description: 角色权限关联表
 * @Date Created in 16:51 2018/2/8
 * @Modified By:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "bg_sys_role_permission")
public class RolePermission extends SuperEntity {

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
