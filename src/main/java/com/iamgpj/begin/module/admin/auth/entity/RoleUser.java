package com.iamgpj.begin.module.admin.auth.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import lombok.Data;

/**
 * @author: GPJ
 * @Description: 角色用户关联表
 * @Date Created in 16:51 2018/2/26
 * @Modified By:
 */
@Data
@TableName(value = "bg_sys_role_user")
public class RoleUser extends SuperEntity {

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
