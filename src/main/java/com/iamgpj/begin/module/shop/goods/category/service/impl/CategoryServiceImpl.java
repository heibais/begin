package com.iamgpj.begin.module.shop.goods.category.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.shop.goods.category.dao.CategoryDAO;
import com.iamgpj.begin.module.shop.goods.category.dto.CategoryDTO;
import com.iamgpj.begin.module.shop.goods.category.entity.Category;
import com.iamgpj.begin.module.shop.goods.category.param.CategoryParam;
import com.iamgpj.begin.module.shop.goods.category.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/18 18:04
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryDAO, Category> implements CategoryService {

    @Override
    public List<CategoryDTO> list(Integer userId) {
        // 查询条件
        List<Category> categoryList = baseMapper.selectList(new EntityWrapper<Category>().eq("user_id", userId).orderAsc(Arrays.asList("sort")));
        List<CategoryDTO> categoryDTOList = ToolUtils.mapList(categoryList, CategoryDTO.class);
        return ToolUtils.list2Tree(categoryDTOList);
    }

    @Override
    public void save(Integer userId, CategoryParam param) {
        param.setUserId(userId);
        // 判断是否存在该分类
        if (checkExist(param.getId(), param.getName(), param.getUserId())) {
            throw new BeginException(ExceptionEnum.DATA_EXISTED);
        }
        Category category = ToolUtils.map(param, Category.class);
        if (category.getId() == null) {
            category.setCreateTime(LocalDateTime.now());
            baseMapper.insert(category);
        } else {
            baseMapper.updateById(category);
        }
    }

    /**
     * 检查是否存在该数据
     * @param id 主键
     * @param name 分类名称
     * @param userId 所属用户
     * @return
     */
    private boolean checkExist(Integer id, String name, Integer userId) {
        Wrapper<Category> wrapper = new EntityWrapper<Category>().eq("name", name).eq("user_id", userId);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return baseMapper.selectCount(wrapper) > 0;
    }

    @Override
    public void delete(Integer userId, Integer id) {
        if (baseMapper.selectCount(new EntityWrapper<Category>().eq("pid", id).eq("user_id", userId)) > 0) {
            throw new BeginException(ExceptionEnum.EXIST_CHILDREN);
        }
        // 判断是否存在子栏目
        baseMapper.deleteById(id);
    }

    @Override
    public void changeStatus(Integer id) {
        Category category = baseMapper.selectById(id);
        if (category == null) {
            throw new BeginException(ExceptionEnum.SERVER_ERROR);
        }
        category.setStatus(category.getStatus() * -1 + 1);
        baseMapper.updateById(category);
    }
}
