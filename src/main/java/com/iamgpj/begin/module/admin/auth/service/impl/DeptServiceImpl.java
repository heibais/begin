package com.iamgpj.begin.module.admin.auth.service.impl;

import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.admin.auth.dao.DeptDAO;
import com.iamgpj.begin.module.admin.auth.dto.DeptDTO;
import com.iamgpj.begin.module.admin.auth.entity.Dept;
import com.iamgpj.begin.module.admin.auth.param.DeptParam;
import com.iamgpj.begin.module.admin.auth.service.DeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author: gpj
 * @Description: 部门业务接口实现类
 * @Date: Created in 17:57 2018/1/28
 * @Modified By:
 */
@Service("deptService")
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptDAO deptDAO;

    @Override
    public List<DeptDTO> findAllTree() {
        List<Dept> depts = deptDAO.findAll(Sort.by(Sort.Direction.ASC, "sort"));
        List<DeptDTO> deptDTOS = depts.stream().map(dept -> ToolUtils.map(dept, DeptDTO.class)).collect(Collectors.toList());
        return ToolUtils.list2Tree(deptDTOS);
    }


    private boolean checkExist(Integer id, String name) {
        if (id == null) {
            return deptDAO.countByName(name) > 0;
        } else {
            return deptDAO.countByIdNotAndName(id, name) > 0;
        }
    }

    @Override
    public void save(DeptParam param) {
        // 判断是否存在该部门
        if (checkExist(param.getId(), param.getName())) {
            throw new BeginException(ExceptionEnum.EXIST_CHILDREN);
        }
        // 拼装对象
        Dept dept = new Dept();
        BeanUtils.copyProperties(param, dept);
        deptDAO.save(dept);
    }

    @Override
    public void removeById(Integer deptId) {
        // 判断是否存在子部门
        if (deptDAO.countDeptByPid(deptId) > 0) {
            throw new BeginException(ExceptionEnum.EXIST_CHILDREN);
        }
        // TODO 判断是否存在用户
        deptDAO.deleteById(deptId);
    }

    @Override
    public void changeStatus(Integer id) {
        deptDAO.changeStatus(id);
    }
}
