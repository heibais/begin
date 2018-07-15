package com.iamgpj.begin.module.shop.goods.goods.dto;

import com.iamgpj.begin.module.shop.goods.gallery.entity.Gallery;
import com.iamgpj.begin.module.shop.goods.goods.enums.UnitEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/29 16:42
 */
@Data
public class GoodsDTO {

    private Integer key;

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
    private Boolean ifPromote;
    /** 促销价格 */
    private Double promotePrice;
    /** 促销开始时间 */
    private LocalDateTime promoteStartTime;
    /** 促销结束时间 */
    private LocalDateTime promoteEndTime;
    /** 关键字 */
    private String keywords;
    /** 商品简介 */
    private String goodsBrief;
    /** 商品详情 */
    private String goodsDesc;
    /** 商家备注 */
    private String ownerRemark = "";
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
    private LocalDateTime createTime;
    /** 修改时间 */
    private LocalDateTime updateTime;

    /** 商品图册 */
    private List<Gallery> galleryList;

    public Integer getKey() {
        return this.getId();
    }
}
