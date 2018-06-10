package com.iamgpj.begin.module.admin.auth.service.impl;

import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.admin.auth.dao.PermissionDAO;
import com.iamgpj.begin.module.admin.auth.dao.RoleUserDAO;
import com.iamgpj.begin.module.admin.auth.dto.PermissionDTO;
import com.iamgpj.begin.module.admin.auth.dto.TreeDTO;
import com.iamgpj.begin.module.admin.auth.entity.Permission;
import com.iamgpj.begin.module.admin.auth.param.PermissionParam;
import com.iamgpj.begin.module.admin.auth.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 权限管理接口实现
 * @author: gpj
 * @Create: 2018/4/24 20:26
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionDAO permissionDAO;

    @Resource
    private RoleUserDAO roleUserDAO;


    @Override
    public List<PermissionDTO> findAllTree() {
        List<Permission> list = permissionDAO.findAll(Sort.by(Sort.Direction.ASC, "sort"));
        List<PermissionDTO> ruleDTOList = list.stream().map(permission -> ToolUtils.map(permission, PermissionDTO.class)).collect(Collectors.toList());
        return ToolUtils.list2Tree(ruleDTOList);
    }

    private boolean checkExist(Integer id, String name) {
        if (id == null) {
            return permissionDAO.countByName(name) > 0;
        } else {
            return permissionDAO.countByNameAndIdNot(name, id) > 0;
        }
    }

    @Override
    public void save(PermissionParam param) {
        // 判断是否存在该部门
        if (checkExist(param.getId(), param.getName())) {
            throw new BeginException(ExceptionEnum.DATA_EXISTED);
        }
        // 拼装对象
        Permission permission = ToolUtils.map(param, Permission.class);
        permissionDAO.save(permission);
    }

    @Override
    public void removeById(Integer id) {
        // 判断是否存在子栏目
        if (permissionDAO.countByPid(id) > 0) {
            throw new BeginException(ExceptionEnum.EXIST_CHILDREN);
        }
        permissionDAO.deleteById(id);
    }

    @Override
    public void changeStatus(Integer id) {
        permissionDAO.changeStatus(id);
    }

    @Override
    public List<TreeDTO> findPermissionTree() {
        List<Permission> list = permissionDAO.findAll(Sort.by(Sort.Direction.ASC, "sort"));

        List<TreeDTO> treeVOList = list.stream().filter(item -> item.getStatus().equals(1))
                .map(permission -> adapt(permission))
                .collect(Collectors.toList());

        return ToolUtils.list2Tree(treeVOList);
    }

    /**
     * 将Permission 转为 TreeVO
     * @param permission
     * @return
     */
    private TreeDTO adapt(Permission permission) {
        TreeDTO vo = new TreeDTO();
        vo.setId(permission.getId());
        vo.setPid(permission.getPid());

        vo.setKey(permission.getId().toString());
        vo.setTitle(permission.getName());
        return vo;
    }

    @Override
    public List<Permission> findAllByRoleIds(List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return null;
        }
        return permissionDAO.findAllByRoleIds(roleIds);
    }

    @Override
    public List<Permission> findAllByUserId(Integer userId) {
        List<Integer> roleIds = roleUserDAO.findRoleIdByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return null;
        }
        return findAllByRoleIds(roleIds);
    }
}
