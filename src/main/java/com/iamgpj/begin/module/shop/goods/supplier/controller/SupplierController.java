package com.iamgpj.begin.module.shop.goods.supplier.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.module.shop.goods.supplier.dto.SupplierDTO;
import com.iamgpj.begin.module.shop.goods.supplier.param.SupplierParam;
import com.iamgpj.begin.module.shop.goods.supplier.service.SupplierService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/21 18:26
 */
@RestController
@RequestMapping("/v1/shop")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @ApiOperation(value = "查询商品供应商分页列表（已实现）", notes = "查询商品供应商分页列表", tags = {"shop", "shop-supplier"})
    @GetMapping("/{userId:\\d+}/supplier")
    public RespJson listPage(@PageableDefault Page<SupplierDTO> page, @PathVariable("userId") Integer userId) {
        Page<SupplierDTO> brandDTOList = supplierService.listPage(page, userId);
        return RespJson.createSuccess(brandDTOList);
    }

    @ApiOperation(value = "查询商品供应商列表（已实现）", notes = "查询商品供应商列表", tags = {"shop", "shop-supplier"})
    @GetMapping("/{userId:\\d+}/supplier/no-page")
    public RespJson list(@PathVariable("userId") Integer userId) {
        List<SupplierDTO> brandDTOList = supplierService.list(userId);
        return RespJson.createSuccess(brandDTOList);
    }

    @ApiOperation(value = "查询商品供应商列表（已实现）", notes = "查询商品供应商列表", tags = {"shop", "shop-supplier"})
    @PostMapping("/{userId:\\d+}/supplier")
    public RespJson save(@PathVariable("userId") Integer userId, @Validated @RequestBody SupplierParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        supplierService.save(userId, param);
        return RespJson.createSuccess("操作成功");
    }

    @ApiOperation(value = "删除商品供应商（已实现）", notes = "查询商品供应商", tags = {"shop", "shop-supplier"})
    @DeleteMapping("/{userId:\\d+}/supplier/{supplierId:\\d+}")
    public RespJson list(@PathVariable("userId") Integer userId, @PathVariable("supplierId") Integer brandId) {
        supplierService.delete(userId, brandId);
        return RespJson.createSuccess("删除成功");
    }

    @ApiOperation(value = "切换供应商状态（已实现）", notes = "切换供应商状态", tags = {"shop", "shop-supplier"})
    @GetMapping("/{userId:\\d+}/supplier/{id:\\d+}/change-status")
    public RespJson changeStatus(@PathVariable("userId") Integer userId, @PathVariable("id") Integer id) {
        supplierService.changeStatus(userId, id);
        return RespJson.createSuccess("切换成功");
    }
}
