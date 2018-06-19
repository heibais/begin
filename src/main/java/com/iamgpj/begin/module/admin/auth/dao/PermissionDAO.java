package com.iamgpj.begin.module.admin.auth.dao;

import com.iamgpj.begin.core.biz.mybatisPlus.SuperMapper;
import com.iamgpj.begin.module.admin.auth.entity.Permission;

import java.util.List;

/**
 * @author: GPJ
 * @Description:
 * @Date Created in 14:04 2018/2/6
 * @Modified By:
 */
public interface PermissionDAO extends SuperMapper<Permission> {

    List<Integer> findPermissionIdByUserId(Integer userId);
}
