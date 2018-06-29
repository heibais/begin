package com.iamgpj.begin.module.shop.goods.goods.dto;

import com.iamgpj.begin.core.enums.UnitEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/29 16:42
 */
@Data
public class GoodsDTO {

    private Integer id;
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
    private UnitEnum goodsWeightUnit;
    /** 商品数量（库存） */
    private Integer goodsNumber;
    /** 库存警告数量 */
    private Integer warnNumber;
    /** 市场价格 */
    private Double marketPrice;
    /** 本地售价 */
    private Double shopPrice;
    /** 是否促销 */
    private Integer ifPromote;
    /** 促销价格 */
    private Double promotePrice;
    /** 促销开始时间 */
    private LocalDateTime promoteStartDate;
    /** 促销结束时间 */
    private LocalDateTime promoteEndDate;
    /** 关键字 */
    private String keyWords;
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
    private Integer noFreight;
    /** 是否上架 */
    private Integer ifOnSale;
    /** 是否新品 */
    private Integer ifNew;
    /** 是否热销 */
    private Integer ifHot;
    /** 是否精品 */
    private Integer ifBest;
    /** 是否删除 */
    private Integer ifDelete;
    /** 排序 */
    private Integer sort;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 修改时间 */
    private LocalDateTime updateTime;
}
