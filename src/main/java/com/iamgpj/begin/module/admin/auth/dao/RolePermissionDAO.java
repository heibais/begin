package com.iamgpj.begin.module.admin.auth.dao;

import com.iamgpj.begin.module.admin.auth.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: GPJ
 * @Description: 角色权限关系表
 * @Date Created in 16:53 2018/2/8
 * @Modified By:
 */
public interface RolePermissionDAO extends JpaRepository<RolePermission, Integer> {

    void deleteAllByRoleId(Integer roleId);

    @Query(value = "SELECT permission_id FROM bg_sys_role_permission WHERE role_id = ?", nativeQuery = true)
    List<Integer> findPermissionIdListByRoleId(Integer roleId);
}
