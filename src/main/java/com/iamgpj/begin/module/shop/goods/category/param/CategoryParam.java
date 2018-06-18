package com.iamgpj.begin.module.shop.goods.category.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/18 18:08
 */
@Data
public class CategoryParam {

    private Integer id;
    /** 父id */
    @NotNull(message = "上级栏目不能为空")
    private Integer pid;
    /** 用户id */
    private Integer userId;
    /** 分类名称 */
    @NotBlank(message = "分类名称不能为空")
    private String name;
    /** 排序 */
    @NotNull(message = "排序不能为空")
    private Integer sort;
    /** 是否显启用 */
    @NotNull(message = "状态不能为空")
    private Integer status;
    /** 是否推荐 */
    @NotNull(message = "是否推荐不能为空")
    private Integer recommend;
}
