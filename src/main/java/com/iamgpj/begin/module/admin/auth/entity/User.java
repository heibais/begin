package com.iamgpj.begin.module.admin.auth.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: gpj
 * @Description: 用户表
 * @Date: Created in 19:05 2018/1/28
 * @Modified By:
 */
@Data
@Entity
@Table(name = "bg_sys_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 盐 */
    private String salt;
    /** 昵称 */
    private String nickname;
    /** 头像 */
    private String avatar;
    /** 邮箱 */
    private String email;
    /** 手机 */
    private String mobile;
    /** 状态 */
    private Integer status;
}
