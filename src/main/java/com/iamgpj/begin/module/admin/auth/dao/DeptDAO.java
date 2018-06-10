package com.iamgpj.begin.module.admin.auth.dao;

import com.iamgpj.begin.module.admin.auth.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @author: gpj
 * @Description: 部门数据库访问
 * @Date: Created in 17:49 2018/1/28
 * @Modified By:
 */
public interface DeptDAO extends JpaRepository<Dept, Integer> {

    int countDeptByPid(Integer pid);

    int countByIdNotAndName(Integer id, String name);

    int countByName(String name);

    @Transactional
    @Modifying
    @Query(value = "update Dept d set d.status = d.status*(-1)+1 where d.id = ?1")
    void changeStatus(Integer id);
}
