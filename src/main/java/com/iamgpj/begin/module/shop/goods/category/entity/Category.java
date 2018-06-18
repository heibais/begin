package com.iamgpj.begin.module.shop.goods.category.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Description: 商品分类
 * @author: gpj
 * @Create: 2018/6/18 17:56
 */
@Data
@Entity
@Table(name = "bg_shop_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** 父id */
    private Integer pid;
    /** 用户id */
    private Integer userId;
    /** 分类名称 */
    private String name;
    /** 排序 */
    private Integer sort;
    /** 是否显启用 */
    private Integer status;
    /** 是否推荐 */
    private Integer recommend;
}
