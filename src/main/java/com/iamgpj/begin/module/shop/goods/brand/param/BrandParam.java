package com.iamgpj.begin.module.shop.goods.brand.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/21 13:28
 */
@Data
public class BrandParam {

    private Integer id;
    @NotNull(message = "所属用户不能为空")
    private Integer userId;
    /** 品牌名称 */
    @NotBlank(message = "品牌名称不能为空")
    private String brandName;
    /** 品牌logo */
    private String brandLogo = "";
    /** 品牌描述 */
    private String brandDesc = "";
    /** 品台网址 */
    private String siteUrl = "";
    /** 排序 */
    @NotNull(message = "排序不能为空")
    private String sort;
    /** 是否显示 (当品牌下还没有商品的时候，首页及分类页的品牌区将不会显示该品牌。) */
    @NotNull(message = "是否显示不能为空")
    private Integer show;
}
