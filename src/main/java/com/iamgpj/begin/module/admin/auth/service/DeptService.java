package com.iamgpj.begin.module.admin.auth.service;

import com.iamgpj.begin.module.admin.auth.dto.DeptDTO;
import com.iamgpj.begin.module.admin.auth.param.DeptParam;

import java.util.List;

/**
 * @author: gpj
 * @Description: 部门管理业务接口
 * @Date: Created in 17:50 2018/1/28
 * @Modified By:
 */
public interface DeptService {

    /**
     * 查询所有部门 的树状
     * @return
     */
    List<DeptDTO> findAllTree();

    /**
     * 新增，更新部门表
     * @param param 部门参数
     * @return
     */
    void save(DeptParam param);

    /**
     * 通过主键删除
     * @param deptId 部门id
     * @return
     */
    void removeById(Integer deptId);

    /**
     * 切换部门状态
     * @param id
     */
    void changeStatus(Integer id);
}
