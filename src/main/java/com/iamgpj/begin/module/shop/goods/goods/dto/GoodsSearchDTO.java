package com.iamgpj.begin.module.shop.goods.goods.dto;

import com.iamgpj.begin.module.shop.goods.goods.enums.SomeStatusEnum;
import lombok.Data;

/**
 * @Description: 商品查询对象
 * @author: GPJ
 * @Create: 2018/7/16 17:07
 */
@Data
public class GoodsSearchDTO {

    private Integer categoryId;

    private Integer brandId;

    private Integer supplierId;

    private Boolean ifOnSale;

    private String goodsName;

    private SomeStatusEnum recommend;
}
