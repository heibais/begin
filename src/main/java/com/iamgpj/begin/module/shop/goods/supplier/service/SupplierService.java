package com.iamgpj.begin.module.shop.goods.supplier.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.iamgpj.begin.module.shop.goods.supplier.dto.SupplierDTO;
import com.iamgpj.begin.module.shop.goods.supplier.param.SupplierParam;

import java.util.List;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/20 23:29
 */
public interface SupplierService {

    /**
     * 查询供应商列表
     * @param userId
     * @return
     */
    List<SupplierDTO> list(Pagination pagination, Integer userId);

    /**
     * 保存或新增供应商
     * @param userId
     * @param param
     */
    void save(Integer userId, SupplierParam param);

    /**
     * 删除供应商
     * @param userId
     * @param supplierId
     */
    void delete(Integer userId, Integer supplierId);
}
