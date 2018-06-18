package com.iamgpj.begin.module.shop.goods.category.service.impl;

import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.shop.goods.category.dao.CategoryDAO;
import com.iamgpj.begin.module.shop.goods.category.dto.CategoryDTO;
import com.iamgpj.begin.module.shop.goods.category.entity.Category;
import com.iamgpj.begin.module.shop.goods.category.param.CategoryParam;
import com.iamgpj.begin.module.shop.goods.category.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/18 18:04
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public List<CategoryDTO> list(Integer userId) {
        // 查询条件
        List<Category> categoryList = categoryDAO.findAll(new Sort(Sort.Direction.ASC, "sort"));
        return ToolUtils.mapList(categoryList, CategoryDTO.class);
    }

    @Override
    public void save(CategoryParam param) {
        // 判断是否存在该分类
        if (checkExist(param.getId(), param.getName(), param.getUserId())) {
            throw new BeginException(ExceptionEnum.DATA_EXISTED);
        }
        Category category = ToolUtils.map(param, Category.class);
        categoryDAO.save(category);
    }

    /**
     * 检查是否存在该数据
     * @param id 主键
     * @param name 分类名称
     * @param userId 所属用户
     * @return
     */
    private boolean checkExist(Integer id, String name, Integer userId) {
        if (id == null) {
            return categoryDAO.countByNameAndUserId(name, userId) > 0;
        } else {
            return categoryDAO.countByIdNotAndNameAndUserId(id, name, userId) > 0;
        }
    }

    @Override
    public void delete(Integer id) {
        if (categoryDAO.countByPid(id) > 0) {
            throw new BeginException(ExceptionEnum.EXIST_CHILDREN);
        }
        // 判断是否存在子栏目
        categoryDAO.deleteById(id);
    }

    @Override
    public void changeStatus(Integer id) {
        categoryDAO.changeStatus(id);
    }
}
