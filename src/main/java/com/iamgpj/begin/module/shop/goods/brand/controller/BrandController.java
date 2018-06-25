package com.iamgpj.begin.module.shop.goods.brand.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.module.shop.goods.brand.dto.BrandDTO;
import com.iamgpj.begin.module.shop.goods.brand.entity.Brand;
import com.iamgpj.begin.module.shop.goods.brand.param.BrandParam;
import com.iamgpj.begin.module.shop.goods.brand.service.BrandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/20 23:08
 */
@RestController
@RequestMapping("/v1/shop")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "查询商品品牌列表（已实现）", notes = "查询商品品牌列表", tags = {"shop", "shop-brand"})
    @GetMapping("/{userId:\\d+}/brand")
    public RespJson list(@PageableDefault Page<BrandDTO> page, @PathVariable("userId") Integer userId) {
        Page<BrandDTO> brandDTOList = brandService.list(page, userId);
        return RespJson.createSuccess(brandDTOList);
    }

    @ApiOperation(value = "查询商品品牌列表（已实现）", notes = "查询商品品牌列表", tags = {"shop", "shop-brand"})
    @PostMapping("/{userId:\\d+}/brand")
    public RespJson save(@PathVariable("userId") Integer userId, @Validated @RequestBody BrandParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        brandService.save(userId, param);
        return RespJson.createSuccess();
    }

    @ApiOperation(value = "查询商品品牌列表（已实现）", notes = "查询商品品牌列表", tags = {"shop", "shop-brand"})
    @DeleteMapping("/{userId:\\d+}/brand/{brandId:\\d+}")
    public RespJson list(@PathVariable("userId") Integer userId, @PathVariable("brandId") Integer brandId) {
        brandService.delete(userId, brandId);
        return RespJson.createSuccess();
    }


}
