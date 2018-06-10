package com.iamgpj.begin.module.admin.auth.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: gpj
 * @Description:
 * @Date: Created in 17:45 2018/1/28
 * @Modified By:
 */
@Data
@Entity
@Table(name = "bg_sys_dept")
public class Dept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** 上级部门id */
    private Integer pid;
    /** 部门名称 */
    private String name;
    /** 排序 */
    private Integer sort;
    /** 状态 */
    private Integer status;
}
