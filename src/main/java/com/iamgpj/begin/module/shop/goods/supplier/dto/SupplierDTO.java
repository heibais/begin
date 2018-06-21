package com.iamgpj.begin.module.shop.goods.supplier.dto;

import lombok.Data;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/21 18:20
 */
@Data
public class SupplierDTO {

    private Integer id;
    private Integer userId;
    /** 供应商名称 */
    private String supplierName;
    /** 供应商描述 */
    private String supplierDesc;
    /** 状态 */
    private Integer status;
}
