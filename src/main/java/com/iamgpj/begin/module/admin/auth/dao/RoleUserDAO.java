package com.iamgpj.begin.module.admin.auth.dao;

import com.iamgpj.begin.module.admin.auth.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/4/27 23:36
 */
public interface RoleUserDAO extends JpaRepository<RoleUser, Integer> {

    Integer countByRoleId(int roleId);

    @Query(value = "select ru.roleId from RoleUser ru where ru.userId = ?1")
    List<Integer> findRoleIdByUserId(Integer userId);

    @Query(value = "DELETE FROM RoleUser ru WHERE ru.userId = ?1")
    @Modifying
    @Transactional
    void deleteByUserId(Integer userId);
}
