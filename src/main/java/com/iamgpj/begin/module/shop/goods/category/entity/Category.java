package com.iamgpj.begin.module.shop.goods.category.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Description: 商品分类
 * @author: gpj
 * @Create: 2018/6/18 17:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "bg_shop_category")
public class Category extends SuperEntity {

    /** 父id */
    private Integer pid;
    /** 用户id */
    private Integer userId;
    /** 分类名称 */
    private String name;
    /** 排序 */
    private Integer sort;
    /** 是否显启用 */
    private Integer status;
    /** 是否推荐 */
    private Integer recommend;
    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
