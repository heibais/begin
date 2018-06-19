package com.iamgpj.begin.module.admin.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
import java.util.Arrays;
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
        List<Dept> depts = deptDAO.selectList(new EntityWrapper<Dept>().orderAsc(Arrays.asList("sort")));
        List<DeptDTO> deptDTOS = depts.stream().map(dept -> ToolUtils.map(dept, DeptDTO.class)).collect(Collectors.toList());
        return ToolUtils.list2Tree(deptDTOS);
    }


    private boolean checkExist(Integer id, String name) {
        Wrapper<Dept> wrapper = new EntityWrapper<Dept>().eq("name", name);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return deptDAO.selectCount(wrapper) > 0;
    }

    @Override
    public void save(DeptParam param) {
        // 判断是否存在该部门
        if (checkExist(param.getId(), param.getName())) {
            throw new BeginException(ExceptionEnum.EXIST_CHILDREN);
        }
        // 拼装对象
        Dept dept = ToolUtils.map(param, Dept.class);
        if (dept.getId() != null) {
            deptDAO.insert(dept);
        } else {
            deptDAO.updateById(dept);
        }
    }

    @Override
    public void removeById(Integer deptId) {
        // 判断是否存在子部门
        if (deptDAO.selectCount(new EntityWrapper<Dept>().eq("pid", deptId)) > 0) {
            throw new BeginException(ExceptionEnum.EXIST_CHILDREN);
        }
        // TODO 判断是否存在用户
        deptDAO.deleteById(deptId);
    }

    @Override
    public void changeStatus(Integer id) {
        Dept dept = deptDAO.selectById(id);
        if (dept == null) {
            throw new BeginException(ExceptionEnum.SERVER_ERROR);
        }
        dept.setStatus(dept.getStatus() * -1 + 1);
        deptDAO.updateById(dept);
    }
}
