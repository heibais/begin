package com.iamgpj.begin.module.shop.goods.goods.entity;

import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import lombok.Data;

/**
 * @Description: 商品表
 * @author: gpj
 * @Create: 2018/6/20 23:19
 */
@Data
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


}
