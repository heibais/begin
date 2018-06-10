package com.iamgpj.begin.web.rule.service;

import com.iamgpj.begin.module.admin.auth.dto.RoleDTO;
import com.iamgpj.begin.module.admin.auth.entity.Role;
import com.iamgpj.begin.module.admin.auth.param.RoleParam;

import java.util.List;

/**
 * @Description: 角色业务接口
 * @author: gpj
 * @Create: 2018/4/23 23:14
 */
public interface RoleService {

    /**
     * 查询所有角色树状列表
     * @return
     */
    List<RoleDTO> findAll();

    /**
     * 新增，更新
     * @param param 参数
     * @return
     */
    void save(RoleParam param);


    /**
     * 主键删除
     * @param id 主键
     * @return
     */
    void removeById(Integer id);

    /**
     * 切换状态
     * @param id 主键
     */
    void changeStatus(Integer id);

    /**
     * 保存角色权限
     * @param roleId 主键
     * @param permissionIds 权限集合
     */
    void saveRolePermission(Integer roleId, String permissionIds);

    /**
     * 查询简单角色列表
     * @return
     */
    List<Role> findSimpleAll();
}
