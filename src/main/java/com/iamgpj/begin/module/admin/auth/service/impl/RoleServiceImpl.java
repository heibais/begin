package com.iamgpj.begin.module.admin.auth.service.impl;

import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.admin.auth.dao.RoleDAO;
import com.iamgpj.begin.module.admin.auth.dao.RolePermissionDAO;
import com.iamgpj.begin.module.admin.auth.dao.RoleUserDAO;
import com.iamgpj.begin.module.admin.auth.dto.RoleDTO;
import com.iamgpj.begin.module.admin.auth.entity.Role;
import com.iamgpj.begin.module.admin.auth.entity.RolePermission;
import com.iamgpj.begin.module.admin.auth.param.RoleParam;
import com.iamgpj.begin.web.rule.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/4/23 23:15
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDAO roleDAO;
    @Resource
    private RolePermissionDAO rolePermissionDAO;
    @Resource
    private RoleUserDAO roleUserDAO;

    @Override
    public List<RoleDTO> findAll() {
        List<Role> roleList = roleDAO.findAll(new Sort(Sort.Direction.ASC, "sort"));
        return roleList.stream().map(role -> {
            RoleDTO roleDTO = ToolUtils.map(role, RoleDTO.class);
            roleDTO.setPermissionIdList(rolePermissionDAO.findPermissionIdListByRoleId(role.getId()));
            return roleDTO;
        }).collect(Collectors.toList());
    }

    private boolean checkExist(Integer id, String name) {
        if (id == null) {
            return roleDAO.countByName(name) > 0;
        } else {
            return roleDAO.countByNameAndIdNot(name, id) > 0;
        }
    }

    @Override
    public void save(RoleParam param) {
        // 判断是否存在该角色
        if (checkExist(param.getId(), param.getName())) {
            throw new BeginException(ExceptionEnum.DATA_EXISTED);
        }
        // 拼装对象
        Role role = ToolUtils.map(param, Role.class);
        roleDAO.save(role);
    }

    @Override
    public void removeById(Integer id) {
        // 判断该角色下是否拥有用户
        if (roleUserDAO.countByRoleId(id) > 0) {
            throw new BeginException(ExceptionEnum.EXIST_CHILDREN);
        }
        roleDAO.deleteById(id);
    }

    @Override
    public void changeStatus(Integer id) {
        roleDAO.changeStatus(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRolePermission(Integer roleId, String permissionIds) {
        //1 清除旧权限
        rolePermissionDAO.deleteAllByRoleId(roleId);
        //2 添加新权限
        if (StringUtils.hasText(permissionIds)) {
            String[] split = permissionIds.split(",");
            List<RolePermission> list = new ArrayList<>(split.length);
            for (String ruleId : split) {
                list.add(new RolePermission(roleId, Integer.parseInt(ruleId)));
            }
            rolePermissionDAO.saveAll(list);
        }
    }

    @Override
    public List<Role> findSimpleAll() {
        List<Role> roles = roleDAO.findAll(Sort.by(Sort.Direction.ASC, "sort"));
        return roles.stream().filter(role -> role.getStatus().equals(1)).collect(Collectors.toList());
    }
}
