package com.iamgpj.begin.module.shop.goods.brand.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 商品品牌
 * @author: gpj
 * @Create: 2018/6/20 23:02
 */
@Data
@TableName(value = "bg_goods_brand")
public class Brand extends SuperEntity {

    /** 所属用户 */
    private Integer userId;
    /** 品牌名称 */
    private String brandName;
    /** 品牌logo */
    private String brandLogo;
    /** 品牌描述 */
    private String brandDesc;
    /** 品牌网址 */
    private String siteUrl;
    /** 排序 */
    private String sort;
    /** 是否显示 (否时：当品牌下还没有商品的时候，首页及分类页的品牌区将不会显示该品牌。) */
    private Integer show;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
