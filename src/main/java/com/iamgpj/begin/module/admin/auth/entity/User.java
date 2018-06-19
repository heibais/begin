package com.iamgpj.begin.module.admin.auth.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: gpj
 * @Description: 用户表
 * @Date: Created in 19:05 2018/1/28
 * @Modified By:
 */
@Data
@TableName(value = "bg_sys_user")
public class User extends SuperEntity {

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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
