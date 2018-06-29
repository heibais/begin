package com.iamgpj.begin.module.shop.goods.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.core.enums.RedisPrefixEnum;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.RedisUtils;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.shop.goods.brand.dto.BrandDTO;
import com.iamgpj.begin.module.shop.goods.gallery.entity.Gallery;
import com.iamgpj.begin.module.shop.goods.gallery.service.GalleryService;
import com.iamgpj.begin.module.shop.goods.goods.dao.GoodsDAO;
import com.iamgpj.begin.module.shop.goods.goods.dto.GoodsDTO;
import com.iamgpj.begin.module.shop.goods.goods.entity.Goods;
import com.iamgpj.begin.module.shop.goods.goods.enums.SomeStatusEnum;
import com.iamgpj.begin.module.shop.goods.goods.param.GoodsParam;
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
    @Autowired
    private RedisUtils redisUtils;


    @Override
    public Page<GoodsDTO> selectPage(Page<GoodsDTO> page,  Integer userId) {
        // 查询条件
        EntityWrapper<Goods> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        // 查询商品基本数据
        List<Goods> goodsList = baseMapper.selectPage(page, wrapper);
        List<GoodsDTO> goodsDTOS = ToolUtils.mapList(goodsList, GoodsDTO.class);
        // 查询其他额外数据

        page.setRecords(goodsDTOS);
        return page;
    }

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

    @Override
    public Goods findById(Integer goodsId) {
        // 查询缓存
        String key = RedisPrefixEnum.GOODS.getPrefix() + goodsId;
        String goodsStr = redisUtils.get(key);
        if (goodsStr == null) {
            Goods goods = baseMapper.selectById(goodsId);
            if (goods != null) {
                goodsStr = ToolUtils.object2String(goods);
                // 存入缓存
                redisUtils.set(key, goodsStr);
            }
        }
        return ToolUtils.string2Object(goodsStr, Goods.class);
    }

    @Override
    public void changeSomeStatus(Integer userId, Integer goodsId, SomeStatusEnum statusEnum) {
        Goods goods = findById(goodsId);
        if (goods != null && goods.getUserId().equals(userId)) {
            switch (statusEnum) {
                case PROMOTE: goods.setIfPromote(goods.getIfPromote() * -1 + 1); break;
                case HOT: goods.setIfHot(goods.getIfHot() * -1 + 1); break;
                case NEW: goods.setIfNew(goods.getIfNew() * -1 + 1); break;
                case BEST: goods.setIfBest(goods.getIfBest() * -1 + 1); break;
                case DELETE: goods.setIfDelete(goods.getIfDelete() * -1 + 1); break;
                case ONSALE: goods.setIfOnSale(goods.getIfOnSale() * -1 + 1); break;
                    default: throw new BeginException(ExceptionEnum.PARAM_ERROR);
            }
            baseMapper.updateById(goods);
        } else {
            throw new BeginException(ExceptionEnum.PARAM_ERROR);
        }
    }

    @Override
    public void delete(Integer userId, Integer id) {
        Goods goods = findById(id);
        if (goods != null && goods.getUserId().equals(userId)) {
            baseMapper.deleteById(id);
        } else {
            throw new BeginException(ExceptionEnum.PARAM_ERROR);
        }
    }
}
