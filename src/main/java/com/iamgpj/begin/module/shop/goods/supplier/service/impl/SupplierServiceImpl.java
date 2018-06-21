package com.iamgpj.begin.module.shop.goods.supplier.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.shop.goods.supplier.dao.SupplierDAO;
import com.iamgpj.begin.module.shop.goods.supplier.dto.SupplierDTO;
import com.iamgpj.begin.module.shop.goods.supplier.entity.Supplier;
import com.iamgpj.begin.module.shop.goods.supplier.param.SupplierParam;
import com.iamgpj.begin.module.shop.goods.supplier.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/20 23:30
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierDAO, Supplier> implements SupplierService {

    @Override
    public List<SupplierDTO> list(Pagination pagination, Integer userId) {
        List<Supplier> supplierList = baseMapper.selectPage(pagination, new EntityWrapper<Supplier>().eq("user_id", userId));
        return ToolUtils.mapList(supplierList, SupplierDTO.class);
    }

    @Override
    public void save(Integer userId, SupplierParam param) {
        param.setUserId(userId);
        Supplier supplier = ToolUtils.map(param, Supplier.class);
        if (supplier.getId() == null) {
            baseMapper.insert(supplier);
        } else {
            baseMapper.updateById(supplier);
        }
    }

    @Override
    public void delete(Integer userId, Integer supplierId) {
        Supplier supplier = baseMapper.selectById(supplierId);
        if (supplier == null && !supplier.getUserId().equals(userId)) {
            throw new BeginException(ExceptionEnum.PARAM_ERROR);
        }
        baseMapper.deleteById(supplierId);
    }
}
