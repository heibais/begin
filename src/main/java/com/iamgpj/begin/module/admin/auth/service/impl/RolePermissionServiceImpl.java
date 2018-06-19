package com.iamgpj.begin.module.admin.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.module.admin.auth.dao.RolePermissionDAO;
import com.iamgpj.begin.module.admin.auth.entity.RolePermission;
import com.iamgpj.begin.module.admin.auth.service.RolePermissionService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/19 21:54
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDAO, RolePermission> implements RolePermissionService {
}
