package com.iamgpj.begin.module.admin.auth.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: GPJ
 * @Description: 权限实体类
 * @Date Created in 14:00 2018/2/6
 * @Modified By:
 */
@Data
@Entity
@Table(name = "bg_sys_permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
}
