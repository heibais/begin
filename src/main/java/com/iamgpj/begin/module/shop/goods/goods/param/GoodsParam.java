package com.iamgpj.begin.module.shop.goods.goods.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iamgpj.begin.module.shop.goods.goods.enums.UnitEnum;
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
    private Integer goodsNumber = 0;
    /** 库存警告数量 */
    private Integer warnNumber = 10;
    /** 市场价格 */
    private Double marketPrice = 0.0;
    /** 本地售价 */
    @NotNull(message = "本地价格不能为空")
    private Double shopPrice;
    /** 是否促销 */
    private Boolean ifPromote = false;
    /** 促销价格 */
    private Double promotePrice = 0.0;
    /** 促销开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime promoteStartTime;
    /** 促销结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime promoteEndTime;
    /** 关键字 */
    private String keywords = "";
    /** 商品简介 */
    private String goodsBrief = "";
    /** 商品详情 */
    private String goodsDesc = "";
    /** 商品主图 */
    private String goodsImg = "";
    /** 商品其他图片 */
    private List<String> goodsOtherImg;
    /** 是否免运费 */
    private Boolean noFreight = true;
    /** 是否上架 */
    private Boolean ifOnSale = true;
    /** 是否新品 */
    private Boolean ifNew = false;
    /** 是否热销 */
    private Boolean ifHot = false;
    /** 是否精品 */
    private Boolean isBest = false;
    /** 是否删除 */
    private Boolean ifDelete = false;
    /** 排序 */
    private Integer sort = 99;
}
