package com.iamgpj.begin.module.shop.goods.category.service;

import com.iamgpj.begin.module.shop.goods.category.dto.CategoryDTO;
import com.iamgpj.begin.module.shop.goods.category.param.CategoryParam;

import java.util.List;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/18 18:03
 */
public interface CategoryService {

    /**
     * 查询分类列表
     * @return
     */
    List<CategoryDTO> list(Integer userId);

    /**
     * 新增或编辑
     * @param param
     */
    void save(Integer userId, CategoryParam param);

    /**
     * 删除
     * @param id 主键
     */
    void delete(Integer userId, Integer id);

    /**
     * 改变状态
     * @param id 主键
     */
    void changeStatus(Integer id);
}
