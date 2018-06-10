package com.iamgpj.begin.module.admin.auth.service;


import com.iamgpj.begin.module.admin.auth.dto.PermissionDTO;
import com.iamgpj.begin.module.admin.auth.dto.TreeDTO;
import com.iamgpj.begin.module.admin.auth.entity.Permission;
import com.iamgpj.begin.module.admin.auth.param.PermissionParam;

import java.util.List;

/**
 * @author: GPJ
 * @Description: 权限管理业务接口
 * @Date Created in 13:59 2018/2/6
 * @Modified By:
 */
public interface PermissionService {

    /**
     * 查询所有格式化后的数据
     * @return
     */
    List<PermissionDTO> findAllTree();

    /**
     * 新增，更新权限表
     * @param param 参数
     * @return
     */
    void save(PermissionParam param);

    /**
     * 通过主键删除
     * @param id 主键
     * @return
     */
    void removeById(Integer id);


    /**
     * 改变状态
     * @param id 主键
     */
    void changeStatus(Integer id);

    /**
     * 查询角色所拥有的所有权限树
     * @return
     */
    List<TreeDTO> findPermissionTree();

    /**
     * 通过角色查询权限
     * @param roleIds
     * @return
     */
    List<Permission> findAllByRoleIds(List<Integer> roleIds);

    /**
     * 通过用户id 查询权限
     * @param userId 用户id
     * @return
     */
    List<Permission> findAllByUserId(Integer userId);

}
