package com.iamgpj.begin.module.shop.goods.category.dao;

import com.iamgpj.begin.module.shop.goods.category.entity.Category;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Description: 商品分类数据库访问
 * @author: gpj
 * @Create: 2018/6/18 18:02
 */
public interface CategoryDAO extends JpaRepository<Category, Integer> {

    int countByPid(Integer pid);

    int countByNameAndUserId(String name, Integer userId);

    int countByIdNotAndNameAndUserId(Integer id, String name, Integer userId);

    @Transactional
    @Modifying
    @Query(value = "update Category c set c.status = c.status*(-1)+1 where c.id = ?1")
    void changeStatus(Integer id);

}
