package com.iamgpj.begin.module.admin.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.admin.auth.dao.RoleDAO;
import com.iamgpj.begin.module.admin.auth.dao.RolePermissionDAO;
import com.iamgpj.begin.module.admin.auth.dao.RoleUserDAO;
import com.iamgpj.begin.module.admin.auth.dto.RoleDTO;
import com.iamgpj.begin.module.admin.auth.entity.Permission;
import com.iamgpj.begin.module.admin.auth.entity.Role;
import com.iamgpj.begin.module.admin.auth.entity.RolePermission;
import com.iamgpj.begin.module.admin.auth.entity.RoleUser;
import com.iamgpj.begin.module.admin.auth.param.RoleParam;
import com.iamgpj.begin.module.admin.auth.service.PermissionService;
import com.iamgpj.begin.module.admin.auth.service.RoleUserService;
import com.iamgpj.begin.web.rule.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/4/23 23:15
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDAO, Role> implements RoleService {

    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private PermissionService permissionService;


    @Override
    public List<RoleDTO> findAll() {
        List<Role> roleList = baseMapper.selectList(new EntityWrapper<Role>().orderAsc(Arrays.asList("sort")));
        return roleList.stream().map(role -> {
            RoleDTO roleDTO = ToolUtils.map(role, RoleDTO.class);
            roleDTO.setPermissionIdList(permissionService.findPermissionIdByRoleId(role.getId()));
            return roleDTO;
        }).collect(Collectors.toList());
    }

    private boolean checkExist(Integer id, String name) {
        Wrapper<Role> wrapper = new EntityWrapper<Role>().eq("name", name);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return baseMapper.selectCount(wrapper) > 0;
    }

    @Override
    public void save(RoleParam param) {
        // 判断是否存在该角色
        if (checkExist(param.getId(), param.getName())) {
            throw new BeginException(ExceptionEnum.DATA_EXISTED);
        }
        // 拼装对象
        Role role = ToolUtils.map(param, Role.class);
        if (role.getId() == null) {
            baseMapper.insert(role);
        } else {
            baseMapper.updateById(role);
        }
    }

    @Override
    public void removeById(Integer id) {
        // 判断该角色下是否拥有用户
        if (roleUserService.selectCount(new EntityWrapper<RoleUser>().eq("role_id", id)) > 0) {
            throw new BeginException(ExceptionEnum.EXIST_CHILDREN);
        }
        baseMapper.deleteById(id);
    }

    @Override
    public void changeStatus(Integer id) {
        Role role = baseMapper.selectById(id);
        if (role == null) {
            throw new BeginException(ExceptionEnum.SERVER_ERROR);
        }
        role.setStatus(role.getStatus() * -1 + 1);
        baseMapper.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRolePermission(Integer roleId, String permissionIds) {
        //1 清除旧权限
        permissionService.deletePermissionByRoleId(roleId);
        //2 添加新权限
        if (StringUtils.hasText(permissionIds)) {
            String[] split = permissionIds.split(",");
            List<RolePermission> list = new ArrayList<>(split.length);
            for (String ruleId : split) {
                list.add(new RolePermission(roleId, Integer.parseInt(ruleId)));
            }
            permissionService.batchInsertRolePermission(list);
        }
    }

    @Override
    public List<Role> findSimpleAll() {
        List<Role> roles = baseMapper.selectList(new EntityWrapper<Role>().orderAsc(Arrays.asList("sort")));
        return roles.stream().filter(role -> role.getStatus().equals(1)).collect(Collectors.toList());
    }
}
