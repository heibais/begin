package com.iamgpj.begin.module.admin.auth.dao;

import com.iamgpj.begin.module.admin.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author: GPJ
 * @Description:
 * @Date Created in 15:42 2018/2/6
 * @Modified By:
 */
public interface RoleDAO extends JpaRepository<Role, Integer> {

    int countByName(String name);

    int countByNameAndIdNot(String name, Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Role r SET r.status = r.status*(-1)+1 WHERE r.id = ?1")
    void changeStatus(Integer id);

}
