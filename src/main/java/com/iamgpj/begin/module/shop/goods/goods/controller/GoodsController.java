package com.iamgpj.begin.module.shop.goods.goods.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.module.shop.goods.goods.dto.GoodsDTO;
import com.iamgpj.begin.module.shop.goods.goods.dto.GoodsSearchDTO;
import com.iamgpj.begin.module.shop.goods.goods.entity.Goods;
import com.iamgpj.begin.module.shop.goods.goods.enums.SomeStatusEnum;
import com.iamgpj.begin.module.shop.goods.goods.param.GoodsParam;
import com.iamgpj.begin.module.shop.goods.goods.service.GoodsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
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

    @ApiOperation(value = "查询商品分页列表（已实现）", notes = "查询商品分页列表", tags = {"shop", "shop-goods"})
    @GetMapping("/{userId:\\d+}/goods")
    public RespJson listPage(@PageableDefault Page<GoodsDTO> page,
                             @PathVariable("userId") Integer userId,
                             GoodsSearchDTO searchDTO) {
        Page<GoodsDTO> goodsDTOList = goodsService.selectPage(page, userId, searchDTO);
        return RespJson.createSuccess(goodsDTOList);
    }

    @ApiOperation(value = "查询商品详情（已实现）", notes = "查询商品详情", tags = {"shop", "shop-goods"})
    @GetMapping("/{userId:\\d+}/goods/{goodsId:\\d+}")
    public RespJson findOne(@PathVariable("userId") Integer userId, @PathVariable("goodsId") Integer goodsId) {
        GoodsDTO goods = goodsService.findById(userId, goodsId);
        return RespJson.createSuccess(goods);
    }

    @ApiOperation(value = "保存商品（已实现）", notes = "保存商品", tags = {"shop", "shop-goods"})
    @PostMapping("/{userId:\\d+}/goods")
    public RespJson save(@PathVariable Integer userId, @Validated @RequestBody GoodsParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        goodsService.insertOrUpdate(userId, param);
        return RespJson.createSuccess("操作成功");
    }

    @ApiOperation(value = "改变商品属性状态（已实现）", notes = "改变商品属性状态", tags = {"shop", "shop-goods"})
    @GetMapping("/{userId:\\d+}/goods/{goodsId:\\d+}/change-status")
    public RespJson change(@PathVariable Integer userId, @PathVariable Integer goodsId, @RequestParam SomeStatusEnum statusEnum) {
        goodsService.changeSomeStatus(userId, goodsId, statusEnum);
        return RespJson.createSuccess("操作成功");
    }

    @ApiOperation(value = "删除商品（已实现）", notes = "删除商品", tags = {"shop", "shop-goods"})
    @DeleteMapping("/{userId:\\d+}/goods/{goodsId:\\d+}")
    public RespJson deleteOne(@PathVariable Integer userId, @PathVariable Integer goodsId) {
        goodsService.delete(userId, goodsId);
        return RespJson.createSuccess("删除成功");
    }


}
