package com.iamgpj.begin.module.shop.goods.gallery.service;

import com.baomidou.mybatisplus.service.IService;
import com.iamgpj.begin.module.shop.goods.gallery.entity.Gallery;

import java.util.List;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/25 12:03
 */
public interface GalleryService extends IService<Gallery> {

    /**
     * 通过商品id 查询
     * @param goodsId
     * @return
     */
    List<Gallery> findByGoodsId(Integer goodsId);

    /**
     * 通过商品id 删除
     * @param id
     * @return
     */
    Integer deleteByGoodsId(Integer id);
}
