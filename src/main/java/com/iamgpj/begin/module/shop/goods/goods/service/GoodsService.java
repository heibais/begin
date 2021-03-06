package com.iamgpj.begin.module.shop.goods.goods.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.iamgpj.begin.module.shop.goods.goods.dto.GoodsDTO;
import com.iamgpj.begin.module.shop.goods.goods.dto.GoodsSearchDTO;
import com.iamgpj.begin.module.shop.goods.goods.dto.GoodsTrashDTO;
import com.iamgpj.begin.module.shop.goods.goods.entity.Goods;
import com.iamgpj.begin.module.shop.goods.goods.enums.SomeStatusEnum;
import com.iamgpj.begin.module.shop.goods.goods.param.GoodsParam;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/25 15:11
 */
public interface GoodsService extends IService<Goods> {


    /**
     * 分页查询
     * @param page
     * @param userId
     * @param searchDTO
     * @return
     */
    Page<GoodsDTO> selectPage(Page<GoodsDTO> page,  Integer userId, GoodsSearchDTO searchDTO);

    /**
     * 查询回收站的商品
     * @param page
     * @param userId
     * @return
     */
    Page<GoodsTrashDTO> selectTrashPage(Page<GoodsTrashDTO> page, Integer userId, String goodsName);

    /**
     * 新增商品
     * @param userId
     * @param param
     */
    void insertOrUpdate(Integer userId, GoodsParam param);


    /**
     * 查询商品
     * @param goodsId
     * @return
     */
    Goods findById(Integer goodsId);

    /**
     * 查询商品 包括商品其他相关信息
     * @param userId
     * @param goodsId
     * @return
     */
    GoodsDTO findById(Integer userId, Integer goodsId);

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

    /**
     * 查询分类下的商品数量
     * @param userId
     * @param categoryId
     * @return
     */
    Integer countByCategoryId(Integer userId, Integer categoryId);

    /**
     * 查询品牌下的商品数量
     * @param userId
     * @param brandId
     * @return
     */
    Integer countByBrandId(Integer userId, Integer brandId);

    /**
     * 查询供应商下的商品数量
     * @param userId
     * @param supplierId
     * @return
     */
    Integer countBySupplierId(Integer userId, Integer supplierId);
}
