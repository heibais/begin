package com.iamgpj.begin.module.shop.goods.goods.controller;

import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.module.shop.goods.goods.param.GoodsParam;
import com.iamgpj.begin.module.shop.goods.goods.service.GoodsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/28 17:15
 */
@RestController
@RequestMapping("/v1/shop")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "保存商品（已实现）", notes = "保存商品", tags = {"shop", "shop-goods"})
    @PostMapping("/{userId:\\d+}/goods")
    public RespJson save(@PathVariable Integer userId, @Validated @RequestBody GoodsParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        goodsService.insert(userId, param);
        return RespJson.createSuccess("操作成功");
    }
}
