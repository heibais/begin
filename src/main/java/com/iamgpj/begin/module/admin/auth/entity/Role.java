package com.iamgpj.begin.module.admin.auth.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: GPJ
 * @Description: 角色实体类
 * @Date Created in 15:38 2018/2/6
 * @Modified By:
 */
@Data
@Entity
@Table(name = "bg_sys_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** 角色名称 */
    private String name;
    /** 排序 */
    private Integer sort;
    /** 角色状态 */
    private Integer status;
}
