package com.iamgpj.begin.module.shop.goods.goods.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.core.enums.RedisPrefixEnum;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.RedisUtils;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.shop.goods.gallery.entity.Gallery;
import com.iamgpj.begin.module.shop.goods.gallery.service.GalleryService;
import com.iamgpj.begin.module.shop.goods.goods.dao.GoodsDAO;
import com.iamgpj.begin.module.shop.goods.goods.dto.GoodsDTO;
import com.iamgpj.begin.module.shop.goods.goods.dto.GoodsSearchDTO;
import com.iamgpj.begin.module.shop.goods.goods.dto.GoodsTrashDTO;
import com.iamgpj.begin.module.shop.goods.goods.entity.Goods;
import com.iamgpj.begin.module.shop.goods.goods.enums.SomeStatusEnum;
import com.iamgpj.begin.module.shop.goods.goods.param.GoodsParam;
import com.iamgpj.begin.module.shop.goods.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public Page<GoodsDTO> selectPage(Page<GoodsDTO> page,  Integer userId, GoodsSearchDTO searchDTO) {
        // 查询条件
        EntityWrapper<Goods> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId).eq("if_delete", 0);
        if (searchDTO != null) {
            if (searchDTO.getCategoryId() != null) {
                wrapper.eq("category_id", searchDTO.getCategoryId());
            }
            if (searchDTO.getBrandId() != null) {
                wrapper.eq("brand_id", searchDTO.getBrandId());
            }
            if (searchDTO.getSupplierId() != null) {
                wrapper.eq("supplier_id", searchDTO.getSupplierId());
            }
            if (searchDTO.getIfOnSale() != null) {
                wrapper.eq("if_on_sale", searchDTO.getIfOnSale());
            }
            if (StringUtils.hasText(searchDTO.getGoodsName())) {
                wrapper.like("goods_name", searchDTO.getGoodsName());
            }
            if (searchDTO.getRecommend() != null) {
                switch (searchDTO.getRecommend()) {
                    case BEST: wrapper.eq("if_best", true); break;
                    case NEW: wrapper.eq("if_new", true); break;
                    case HOT: wrapper.eq("if_hot", true); break;
                    default:
                }
            }
        }
        // 查询商品基本数据
        List<Goods> goodsList = baseMapper.selectPage(page, wrapper);
        List<GoodsDTO> goodsDTOS = ToolUtils.mapList(goodsList, GoodsDTO.class);
        // 查询其他额外数据

        page.setRecords(goodsDTOS);
        return page;
    }

    @Override
    public Page<GoodsTrashDTO> selectTrashPage(Page<GoodsTrashDTO> page, Integer userId, String goodsName) {
        Wrapper<Goods> wrapper = new EntityWrapper<Goods>()
                                    .setSqlSelect("id", "goods_name", "goods_sn", "shop_price")
                                    .eq("user_id", userId)
                                    .eq("if_delete", 1);
        if (StringUtils.hasText(goodsName)) {
            wrapper.like("goods_name", goodsName);
        }
        // 查询商品基本数据
        List<Goods> goodsList = baseMapper.selectPage(page, wrapper);
        List<GoodsTrashDTO> goodsDTOS = ToolUtils.mapList(goodsList, GoodsTrashDTO.class);
        page.setRecords(goodsDTOS);
        return page;
    }

    /**
     * 生成商品货号
     * @return
     */
    private String mkGoodsSn(Integer userId) {
        // 前缀
        StringBuilder sb = new StringBuilder("CSQX-");
        // 日期
        DateTimeFormatter dtf = DateTimeFormatter.BASIC_ISO_DATE;
        String format = dtf.format(LocalDate.now());
        sb.append(format);
        // 随机
        sb.append(ToolUtils.getRandomNum(8));
        // 判断是否存在该货号
        Integer count = baseMapper.selectCount(new EntityWrapper<Goods>().eq("goods_sn", sb.toString()));
        if (count > 0) {
            return mkGoodsSn(userId);
        }
        return sb.toString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOrUpdate(Integer userId, GoodsParam param) {
        // 1, 获取数据
        Goods goods = ToolUtils.map(param, Goods.class);
        // 2,判断是否有商品货号
        if (StringUtils.hasText(goods.getGoodsSn())) {
            // TODO 存在：判断是否符合格式
        } else {
            // 不存在，自动生成
            goods.setGoodsSn(mkGoodsSn(userId));
        }
        if (param.getId() == null) {
            // 新增
            baseMapper.insert(goods);
        } else {
            // 编辑
            baseMapper.updateById(goods);
            // 删除所有商品图册
            galleryService.deleteByGoodsId(param.getId());
            // 清除缓存
            String goodsKey = RedisPrefixEnum.GOODS.getPrefix() + param.getId();
            redisUtils.remove(goodsKey, goodsKey + ":desc");
        }
        // 2、保存商品图片
        if (!CollectionUtils.isEmpty(param.getGoodsOtherImg())) {
            List<Gallery> galleries = new ArrayList<>(param.getGoodsOtherImg().size());
            param.getGoodsOtherImg().forEach(item -> {
                Gallery gallery = new Gallery();
                gallery.setUserId(userId);
                gallery.setGoodsId(goods.getId());
                gallery.setOriginalUrl(item);
                galleries.add(gallery);
            });
            galleryService.insertBatch(galleries);
        }
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
    public GoodsDTO findById(Integer userId, Integer goodsId) {
        // 查询缓存
        String key = RedisPrefixEnum.GOODS.getPrefix() + goodsId + ":desc";
        String goodsDtoStr = redisUtils.get(key);

        if (goodsDtoStr == null) {
            // 1 查询商品
            Goods goods = this.findById(goodsId);
            // 2 检查参数
            if (!goods.getUserId().equals(userId)) {
                throw new BeginException(ExceptionEnum.PARAM_ERROR);
            }
            GoodsDTO goodsDTO = ToolUtils.map(goods, GoodsDTO.class);
            // 3 查询商品图册
            goodsDTO.setGalleryList(galleryService.findByGoodsId(goodsId));
            // 存入缓存
            goodsDtoStr = ToolUtils.object2String(goodsDTO);
            redisUtils.set(key, goodsDtoStr);
            return goodsDTO;
        } else {
            return ToolUtils.string2Object(goodsDtoStr, GoodsDTO.class);
        }
    }

    @Override
    public void changeSomeStatus(Integer userId, Integer goodsId, SomeStatusEnum statusEnum) {
        Goods goods = findById(goodsId);
        if (goods != null && goods.getUserId().equals(userId)) {
            switch (statusEnum) {
                case PROMOTE: goods.setIfPromote(!goods.getIfPromote()); break;
                case HOT: goods.setIfHot(!goods.getIfHot()); break;
                case NEW: goods.setIfNew(!goods.getIfNew()); break;
                case BEST: goods.setIfBest(!goods.getIfBest()); break;
                case DELETE: goods.setIfDelete(!goods.getIfDelete()); break;
                case ONSALE: goods.setIfOnSale(!goods.getIfOnSale()); break;
                    default: throw new BeginException(ExceptionEnum.PARAM_ERROR);
            }
            baseMapper.updateById(goods);
            // 清除缓存
            String goodsKey = RedisPrefixEnum.GOODS.getPrefix() + goodsId;
            redisUtils.remove(goodsKey, goodsKey + ":desc");
        } else {
            throw new BeginException(ExceptionEnum.PARAM_ERROR);
        }
    }

    @Override
    public void delete(Integer userId, Integer goodsId) {
        Goods goods = findById(goodsId);
        if (goods != null && goods.getUserId().equals(userId)) {
            baseMapper.deleteById(goodsId);
            // 清除缓存
            String goodsKey = RedisPrefixEnum.GOODS.getPrefix() + goodsId;
            redisUtils.remove(goodsKey, goodsKey + ":desc");
        } else {
            throw new BeginException(ExceptionEnum.PARAM_ERROR);
        }
    }

    @Override
    public Integer countByCategoryId(Integer userId, Integer categoryId) {
        return baseMapper.selectCount(new EntityWrapper<Goods>()
                .eq("user_id", userId)
                .eq("category_id", categoryId));

    }

    @Override
    public Integer countByBrandId(Integer userId, Integer brandId) {
        return baseMapper.selectCount(new EntityWrapper<Goods>()
                .eq("user_id", userId)
                .eq("brand_id", brandId));
    }

    @Override
    public Integer countBySupplierId(Integer userId, Integer supplierId) {
        return baseMapper.selectCount(new EntityWrapper<Goods>()
                .eq("user_id", userId)
                .eq("supplier_id", supplierId));
    }
}
