package com.iamgpj.begin.module.shop.goods.goods.service;

import com.iamgpj.begin.module.shop.goods.goods.param.GoodsParam;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/25 15:11
 */
public interface GoodsService {

    /**
     * 新增商品
     * @param userId
     * @param param
     */
    void insert(Integer userId, GoodsParam param);

    /**
     * 编辑商品
     * @param userId
     * @param param
     */
    void update(Integer userId, GoodsParam param);

}
