package com.iamgpj.begin.module.admin.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iamgpj.begin.module.admin.auth.dao.RoleUserDAO;
import com.iamgpj.begin.module.admin.auth.entity.RoleUser;
import com.iamgpj.begin.module.admin.auth.service.RoleUserService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/19 23:06
 */
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserDAO, RoleUser> implements RoleUserService {
}
