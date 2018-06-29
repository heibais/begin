package com.iamgpj.begin.module.shop.goods.goods.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.iamgpj.begin.module.shop.goods.goods.dto.GoodsDTO;
import com.iamgpj.begin.module.shop.goods.goods.entity.Goods;
import com.iamgpj.begin.module.shop.goods.goods.enums.SomeStatusEnum;
import com.iamgpj.begin.module.shop.goods.goods.param.GoodsParam;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/25 15:11
 */
public interface GoodsService {


    Page<GoodsDTO> selectPage(Page<GoodsDTO> page,  Integer userId);

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

    /**
     * 查询商品
     * @param goodsId
     * @return
     */
    Goods findById(Integer goodsId);

    /**
     * 改变字段状态
     * @param userId 用户id
     * @param goodsId 商品id
     * @param statusEnum 哪个字段
     */
    void changeSomeStatus(Integer userId, Integer goodsId, SomeStatusEnum statusEnum);

    /**
     * 删除商品
     * @param userId
     * @param id
     */
    void delete(Integer userId, Integer id);

}
