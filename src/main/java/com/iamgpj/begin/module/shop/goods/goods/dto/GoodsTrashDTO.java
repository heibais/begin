package com.iamgpj.begin.module.shop.goods.goods.dto;

import lombok.Data;

/**
 * @Description: 商品回收站传输对象
 * @author: GPJ
 * @Create: 2018/6/29 16:42
 */
@Data
public class GoodsTrashDTO {

    private Integer key;

    private Integer id;
    /** 所属用户id */
    private Integer userId;
    /** 商品货号 */
    private String goodsSn;
    /** 商品名称 */
    private String goodsName;
    /** 商品价格 */
    private Double shopPrice;

    public Integer getKey() {
        return this.getId();
    }
}
