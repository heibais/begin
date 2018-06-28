package com.iamgpj.begin.module.shop.goods.goods.param;

import com.iamgpj.begin.core.enums.UnitEnum;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/28 16:59
 */
@Data
public class GoodsParam {

    private Integer id;
    @NotNull(message = "所属用户不能为空")
    private Integer userId;
    @NotNull(message = "商品分类不能为空")
    private Integer categoryId;
    /** 商品货号 */
    private String goodsSn;
    @NotBlank(message = "商品名称不能为空")
    private String goodsName;
    @NotNull(message = "商品品牌不能为空")
    private Integer brandId;
    @NotNull(message = "商品供应商不能为空")
    private Integer supplierId;
    /** 商品重量 */
    @Min(value = 0, message = "商品商量不能小于0")
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
    @NotNull(message = "本地价格不能为空")
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
    private String keyWords;
    /** 商品简介 */
    private String goodsBrief;
    /** 商品详情 */
    private String goodsDesc;
    /** 商品主图 */
    private String goodsImg;
    /** 商品其他图片 */
    private List<String> goodsOtherImg;
    /** 是否免运费 */
    private Integer noFreight;
    /** 是否上架 */
    private Integer ifOnSale;
    /** 是否新品 */
    private Integer ifNew;
    /** 是否热销 */
    private Integer ifHot;
    /** 是否精品 */
    private Integer isBest;
    /** 是否删除 */
    private Integer ifDelete = 0;
    /** 排序 */
    private Integer sort;
}
