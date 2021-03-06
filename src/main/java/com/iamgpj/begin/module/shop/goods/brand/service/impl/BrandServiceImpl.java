package com.iamgpj.begin.module.shop.goods.brand.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.shop.goods.brand.dao.BrandDao;
import com.iamgpj.begin.module.shop.goods.brand.dto.BrandDTO;
import com.iamgpj.begin.module.shop.goods.brand.entity.Brand;
import com.iamgpj.begin.module.shop.goods.brand.param.BrandParam;
import com.iamgpj.begin.module.shop.goods.brand.service.BrandService;
import com.iamgpj.begin.module.shop.goods.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/20 23:07
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandDao, Brand> implements BrandService {

    @Autowired
    private GoodsService goodsService;

    @Override
    public Page<BrandDTO> listPage(Page<BrandDTO> page, Integer userId) {
        List<Brand> brandList = baseMapper.selectPage(page, new EntityWrapper<Brand>().eq("user_id", userId).orderBy("sort", true));
        page.setRecords(ToolUtils.mapList(brandList, BrandDTO.class));
        return page;
    }

    @Override
    public List<BrandDTO> list(Integer userId) {
        List<Brand> brandList = baseMapper.selectList(new EntityWrapper<Brand>().eq("user_id", userId).orderBy("sort", true));
        return ToolUtils.mapList(brandList, BrandDTO.class);
    }

    @Override
    public void save(Integer userId, BrandParam param) {
        param.setUserId(userId);
        Brand brand = ToolUtils.map(param, Brand.class);
        if (brand.getId() == null) {
            baseMapper.insert(brand);
        } else {
            baseMapper.updateById(brand);
        }
    }

    @Override
    public void delete(Integer userId, Integer brandId) {
        Brand brand = baseMapper.selectById(brandId);
        if (brand == null || !brand.getUserId().equals(userId)) {
            throw new BeginException(ExceptionEnum.PARAM_ERROR);
        }
        // 判断该品牌下是否存在商品
        if (goodsService.countByBrandId(userId, brandId) > 0) {
            throw new BeginException(ExceptionEnum.BRAND_EXIST_GOODS);
        }
        baseMapper.deleteById(brandId);
    }

    @Override
    public void changeShow(Integer userId, Integer id) {
        Brand brand = baseMapper.selectById(id);
        if (brand == null || !brand.getUserId().equals(userId)) {
            throw new BeginException(ExceptionEnum.PARAM_ERROR);
        }
        brand.setShow(brand.getShow() * -1 + 1);
        baseMapper.updateById(brand);
    }
}
