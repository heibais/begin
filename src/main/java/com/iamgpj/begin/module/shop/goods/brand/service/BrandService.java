package com.iamgpj.begin.module.shop.goods.brand.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.iamgpj.begin.module.shop.goods.brand.dto.BrandDTO;
import com.iamgpj.begin.module.shop.goods.brand.param.BrandParam;

import java.util.List;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/20 23:07
 */
public interface BrandService {

    /**
     * 查询品牌列表
     * @param userId
     * @return
     */
    List<BrandDTO> list(Pagination pagination, Integer userId);

    /**
     * 保存或新增品牌
     * @param userId
     * @param param
     */
    void save(Integer userId, BrandParam param);

    /**
     * 删除品牌
     * @param userId
     * @param brandId
     */
    void delete(Integer userId, Integer brandId);
}
