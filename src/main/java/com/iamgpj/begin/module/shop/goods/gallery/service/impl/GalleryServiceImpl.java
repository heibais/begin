package com.iamgpj.begin.module.shop.goods.gallery.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.module.shop.goods.gallery.dao.GalleryDAO;
import com.iamgpj.begin.module.shop.goods.gallery.entity.Gallery;
import com.iamgpj.begin.module.shop.goods.gallery.service.GalleryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/25 12:03
 */
@Service
public class GalleryServiceImpl extends ServiceImpl<GalleryDAO, Gallery> implements GalleryService {

    @Override
    public List<Gallery> findByGoodsId(Integer goodsId) {
        return baseMapper.selectList(new EntityWrapper<Gallery>().eq("goods_id", goodsId));
    }

    @Override
    public Integer deleteByGoodsId(Integer goodsId) {
        return baseMapper.delete(new EntityWrapper<Gallery>().eq("goods_id", goodsId));
    }
}
