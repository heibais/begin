package com.iamgpj.begin.module.admin.auth.dao;

import com.iamgpj.begin.module.admin.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author: GPJ
 * @Description:
 * @Date Created in 14:04 2018/2/6
 * @Modified By:
 */
public interface PermissionDAO extends JpaRepository<Permission, Integer> {

    int countByPid(Integer pid);

    int countByName(String name);

    int countByNameAndIdNot(String name, Integer id);


    @Query(value = "select p from Permission p inner join RolePermission rp on rp.permissionId = p.id where rp.roleId in ?1")
    List<Permission> findAllByRoleIds(List<Integer> roleIds);

    @Transactional
    @Modifying
    @Query(value = "update Permission p set p.status = p.status*(-1)+1 where p.id = ?1")
    void changeStatus(Integer id);

}
