package com.iamgpj.begin.module.shop.goods.category.controller;

import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.module.shop.goods.category.dto.CategoryDTO;
import com.iamgpj.begin.module.shop.goods.category.param.CategoryParam;
import com.iamgpj.begin.module.shop.goods.category.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/18 22:24
 */
@RestController
@RequestMapping("/v1/shop")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "查询部门列表树状结构（已实现）", notes = "商品分类列表", tags = {"shop", "shop-category"})
    @GetMapping("/{userId:\\d+}/category")
    public RespJson list(@PathVariable Integer userId) {
        List<CategoryDTO> dtoList = categoryService.list(userId);
        return RespJson.createSuccess(dtoList);
    }

    @ApiOperation(value = "保存或者编辑商品分类（已实现）", notes = "保存或者编辑商品分类", tags = {"shop", "shop-category"})
    @PostMapping("/{userId:\\d+}/category")
    public RespJson save(@PathVariable Integer userId, @Validated @RequestBody CategoryParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        categoryService.save(userId, param);
        return RespJson.createSuccess("操作成功");
    }

    @ApiOperation(value = "删除商品分类（已实现）", notes = "删除商品分类", tags = {"shop", "shop-category"})
    @DeleteMapping("/{userId:\\d+}/category/{id:\\d+}")
    public RespJson deleteOne(@PathVariable("userId") Integer userId, @PathVariable("id") Integer id) {
        categoryService.delete(userId, id);
        return RespJson.createSuccess("删除成功");
    }


    @ApiOperation(value = "切换商品分类状态（已实现）", notes = "切换商品分类状态", tags = {"shop", "shop-category"})
    @GetMapping("/{userId:\\d+}/category/{id:\\d+}/change-status")
    public RespJson changeStatus(@PathVariable("userId") Integer userId, @PathVariable("id") Integer id) {
        categoryService.changeStatus(id);
        return RespJson.createSuccess("切换状态成功");
    }

}
