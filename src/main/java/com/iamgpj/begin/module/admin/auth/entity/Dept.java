package com.iamgpj.begin.module.admin.auth.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


/**
 * @author: gpj
 * @Description:
 * @Date: Created in 17:45 2018/1/28
 * @Modified By:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "bg_sys_dept")
public class Dept extends SuperEntity {

    /** 上级部门id */
    private Integer pid;
    /** 部门名称 */
    private String name;
    /** 排序 */
    private Integer sort;
    /** 状态 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
