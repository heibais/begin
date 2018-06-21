package com.iamgpj.begin.module.shop.goods.supplier.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/21 18:21
 */
@Data
public class SupplierParam {

    private Integer id;
    @NotNull(message = "所属用户不能为空")
    private Integer userId;
    /** 供应商名称 */
    @NotBlank(message = "供应商名称不能为空")
    private String supplierName;
    /** 供应商描述 */
    private String supplierDesc = "";
    /** 状态 */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
