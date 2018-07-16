package com.iamgpj.begin.module.shop.goods.supplier.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.shop.goods.goods.service.GoodsService;
import com.iamgpj.begin.module.shop.goods.supplier.dao.SupplierDAO;
import com.iamgpj.begin.module.shop.goods.supplier.dto.SupplierDTO;
import com.iamgpj.begin.module.shop.goods.supplier.entity.Supplier;
import com.iamgpj.begin.module.shop.goods.supplier.param.SupplierParam;
import com.iamgpj.begin.module.shop.goods.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/20 23:30
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierDAO, Supplier> implements SupplierService {

    @Autowired
    private GoodsService goodsService;

    @Override
    public Page<SupplierDTO> listPage(Page<SupplierDTO> page, Integer userId) {
        List<Supplier> supplierList = baseMapper.selectPage(page, new EntityWrapper<Supplier>().eq("user_id", userId));
        return page.setRecords(ToolUtils.mapList(supplierList, SupplierDTO.class));
    }

    @Override
    public List<SupplierDTO> list(Integer userId) {
        List<Supplier> supplierList = baseMapper.selectList(new EntityWrapper<Supplier>().eq("user_id", userId).eq("status", 1));
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
        // 判断该供应商下是否存在商品
        if (goodsService.countBySupplierId(userId, supplierId) > 0) {
            throw new BeginException(ExceptionEnum.SUPPLIER_EXIST_GOODS);
        }
        baseMapper.deleteById(supplierId);
    }

    @Override
    public void changeStatus(Integer userId, Integer id) {
        Supplier supplier = baseMapper.selectById(id);
        if (supplier == null || !supplier.getUserId().equals(userId)) {
            throw new BeginException(ExceptionEnum.PARAM_ERROR);
        }
        supplier.setStatus(supplier.getStatus() * -1 + 1);
        baseMapper.updateById(supplier);
    }
}
