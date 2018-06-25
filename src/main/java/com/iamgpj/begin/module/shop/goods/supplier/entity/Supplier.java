package com.iamgpj.begin.module.shop.goods.supplier.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Description: 商品供应商
 * @author: gpj
 * @Create: 2018/6/20 23:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "bg_shop_supplier")
public class Supplier extends SuperEntity {

    private Integer userId;
    /** 供应商名称 */
    private String supplierName;
    /** 供应商描述 */
    private String supplierDesc;
    /** 状态 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
