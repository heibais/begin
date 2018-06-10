package com.iamgpj.begin.module.admin.auth.controller;

import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.module.admin.auth.param.DeptParam;
import com.iamgpj.begin.module.admin.auth.service.DeptService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: gpj
 * @Description: 部门管理控制器
 * @Date: Created in 18:28 2018/1/28
 * @Modified By:
 */
@RestController
@RequestMapping("/v1/auth/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    @ApiOperation(value = "查询部门列表树状结构（已实现）", notes = "查询部门列表树状结构", tags = {"auth", "auth-department"})
    @GetMapping
    public RespJson listTree() {
        return RespJson.createSuccess(deptService.findAllTree());
    }

    @ApiOperation(value = "保存或者编辑部门（已实现）", notes = "保存或者编辑部门", tags = {"auth", "auth-department"})
    @PostMapping
    public RespJson save(@Validated @RequestBody DeptParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        deptService.save(param);
        return RespJson.createSuccess("操作成功");
    }

    @ApiOperation(value = "删除部门（已实现）", notes = "删除部门", tags = {"auth", "auth-department"})
    @DeleteMapping("/{id:\\d+}")
    public RespJson deleteOne(@PathVariable("id") Integer id) {
        deptService.removeById(id);
        return RespJson.createSuccess("删除成功");
    }


    @ApiOperation(value = "切换部门状态（已实现）", notes = "切换部门状态", tags = {"auth", "auth-department"})
    @GetMapping("/{id:\\d+}/change-status")
    public RespJson changeStatus(@PathVariable("id") Integer id) {
        deptService.changeStatus(id);
        return RespJson.createSuccess("切换状态成功");
    }
}
