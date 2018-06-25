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

    @Override
    public Page<BrandDTO> list(Page<BrandDTO> page, Integer userId) {
        List<Brand> brandList = baseMapper.selectPage(page, new EntityWrapper<Brand>().eq("user_id", userId));
        page.setRecords(ToolUtils.mapList(brandList, BrandDTO.class));
        return page;
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
        baseMapper.deleteById(brandId);
    }
}
