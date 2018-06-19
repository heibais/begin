package com.iamgpj.begin.module.admin.auth.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: GPJ
 * @Description: 权限实体类
 * @Date Created in 14:00 2018/2/6
 * @Modified By:
 */
@Data
@TableName(value = "bg_sys_permission")
public class Permission extends SuperEntity {

    /** 上级权限 */
    private Integer pid;
    /** 权限名称 */
    private String name;
    /** 权限类型 */
    private Integer type;
    /** url地址 */
    private String url;
    /** 权限点 */
    private String permission;
    /** 图标 */
    private String icon;
    /** 排序 */
    private Integer sort;
    /** 状态 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
