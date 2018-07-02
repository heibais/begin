package com.iamgpj.begin.module.shop.goods.goods.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import com.iamgpj.begin.core.enums.UnitEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Description: 商品表
 * @author: gpj
 * @Create: 2018/6/20 23:19
 */
@Data
@TableName(value = "bg_shop_goods")
@EqualsAndHashCode(callSuper = false)
public class Goods extends SuperEntity {

    /** 所属用户id */
    private Integer userId;
    /** 商品分类id */
    private Integer categoryId;
    /** 商品货号 */
    private String goodsSn;
    /** 商品名称 */
    private String goodsName;
    /** 品牌id */
    private Integer brandId;
    /** 供应商id */
    private Integer supplierId;
    /** 商品重量 */
    private Double goodsWeight;
    /** 商品重量单位 */
    private Integer goodsWeightUnit;
    /** 商品数量（库存） */
    private Integer goodsNumber;
    /** 库存警告数量 */
    private Integer warnNumber;
    /** 市场价格 */
    private Double marketPrice;
    /** 本地售价 */
    private Double shopPrice;
    /** 是否促销 */
    private Boolean ifPromote;
    /** 促销价格 */
    private Double promotePrice;
    /** 促销开始时间 */
    private LocalDateTime promoteStartDate;
    /** 促销结束时间 */
    private LocalDateTime promoteEndDate;
    /** 关键字 */
    private String keywords;
    /** 商品简介 */
    private String goodsBrief;
    /** 商品详情 */
    private String goodsDesc;
    /** 商品主图 */
    private String goodsImg;
    /** 商品缩略图 */
    //private String goodsThumb;
    /** 商品原图 */
    //private String goodsOriginalImg;
    /** 是否免运费 */
    private Boolean noFreight;
    /** 是否上架 */
    private Boolean ifOnSale;
    /** 是否新品 */
    private Boolean ifNew;
    /** 是否热销 */
    private Boolean ifHot;
    /** 是否精品 */
    private Boolean ifBest;
    /** 是否删除 */
    private Boolean ifDelete;
    /** 排序 */
    private Integer sort;
    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /** 修改时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
