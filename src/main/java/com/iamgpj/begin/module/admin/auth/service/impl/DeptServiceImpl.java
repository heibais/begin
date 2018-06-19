package com.iamgpj.begin.module.admin.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class DeptServiceImpl extends ServiceImpl<DeptDAO, Dept> implements DeptService {


    @Override
    public List<DeptDTO> findAllTree() {
        List<Dept> depts = baseMapper.selectList(new EntityWrapper<Dept>().orderAsc(Arrays.asList("sort")));
        List<DeptDTO> deptDTOS = depts.stream().map(dept -> ToolUtils.map(dept, DeptDTO.class)).collect(Collectors.toList());
        return ToolUtils.list2Tree(deptDTOS);
    }


    private boolean checkExist(Integer id, String name) {
        Wrapper<Dept> wrapper = new EntityWrapper<Dept>().eq("name", name);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return baseMapper.selectCount(wrapper) > 0;
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
            baseMapper.insert(dept);
        } else {
            baseMapper.updateById(dept);
        }
    }

    @Override
    public void removeById(Integer deptId) {
        // 判断是否存在子部门
        if (baseMapper.selectCount(new EntityWrapper<Dept>().eq("pid", deptId)) > 0) {
            throw new BeginException(ExceptionEnum.EXIST_CHILDREN);
        }
        // TODO 判断是否存在用户
        baseMapper.deleteById(deptId);
    }

    @Override
    public void changeStatus(Integer id) {
        Dept dept = baseMapper.selectById(id);
        if (dept == null) {
            throw new BeginException(ExceptionEnum.SERVER_ERROR);
        }
        dept.setStatus(dept.getStatus() * -1 + 1);
        baseMapper.updateById(dept);
    }
}
