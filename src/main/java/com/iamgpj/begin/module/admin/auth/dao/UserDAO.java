package com.iamgpj.begin.module.admin.auth.dao;

import com.iamgpj.begin.module.admin.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author: gpj
 * @Description: 用户表数据库操作
 * @Date: Created in 19:09 2018/1/28
 * @Modified By:
 */
public interface UserDAO extends JpaRepository<User, Integer>, JpaSpecificationExecutor {

    int countByIdNotAndUsername(Integer id, String username);

    int countByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.status = u.status*(-1)+1 where u.id = ?1")
    void changeStatus(Integer id);

    @Query(value = "SELECT role_id FROM bg_sys_role_user WHERE user_id = ?1", nativeQuery = true)
    List<Integer> findRoleIdsByUserId(Integer userId);

    User findByUsername(String username);

    User findByMobile(String mobile);

    User findByMobileAndIdNot(String mobile, Integer userId);

    User findByEmail(String email);

    User findByEmailAndIdNot(String email, Integer userId);
}
