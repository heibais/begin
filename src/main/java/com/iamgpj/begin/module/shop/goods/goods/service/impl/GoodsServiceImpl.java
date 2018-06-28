package com.iamgpj.begin.module.shop.goods.goods.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.shop.goods.gallery.entity.Gallery;
import com.iamgpj.begin.module.shop.goods.gallery.service.GalleryService;
import com.iamgpj.begin.module.shop.goods.goods.param.GoodsParam;
import com.iamgpj.begin.module.shop.goods.goods.dao.GoodsDAO;
import com.iamgpj.begin.module.shop.goods.goods.entity.Goods;
import com.iamgpj.begin.module.shop.goods.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/25 15:13
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsDAO, Goods> implements GoodsService {

    @Autowired
    private GalleryService galleryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Integer userId, GoodsParam param) {
        // 1, 获取数据
        Goods goods = ToolUtils.map(param, Goods.class);
        baseMapper.insert(goods);
        // 2、保存商品图片
        if (!CollectionUtils.isEmpty(param.getGoodsOtherImg())) {
            List<Gallery> galleries = new ArrayList<>(param.getGoodsOtherImg().size());
            param.getGoodsOtherImg().forEach(item -> {
                Gallery gallery = new Gallery();
                gallery.setUserId(userId);
                gallery.setImgUrl(item);
                galleries.add(gallery);
            });
            galleryService.insertBatch(galleries);
        }
    }

    @Override
    public void update(Integer userId, GoodsParam param) {

    }
}
