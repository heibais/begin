package com.iamgpj.begin.module.shop.goods.brand.dto;

import lombok.Data;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/21 13:25
 */
@Data
public class BrandDTO {

    private Integer id;
    /** 所属用户 */
    private Integer userId;
    /** 品牌名称 */
    private String brandName;
    /** 品牌logo */
    private String brandLogo;
    /** 品牌描述 */
    private String brandDesc;
    /** 品台网址 */
    private String siteUrl;
    /** 排序 */
    private String sort;
    /** 是否显示 (当品牌下还没有商品的时候，首页及分类页的品牌区将不会显示该品牌。) */
    private Integer show;
}
