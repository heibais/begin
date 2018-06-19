package com.iamgpj.begin.module.admin.auth.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: GPJ
 * @Description: 角色实体类
 * @Date Created in 15:38 2018/2/6
 * @Modified By:
 */
@Data
@TableName(value = "bg_sys_role")
public class Role extends SuperEntity {

    /** 角色名称 */
    private String name;
    /** 排序 */
    private Integer sort;
    /** 角色状态 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
