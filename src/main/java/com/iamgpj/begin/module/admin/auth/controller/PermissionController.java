package com.iamgpj.begin.module.admin.auth.controller;

import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.module.admin.auth.param.PermissionParam;
import com.iamgpj.begin.module.admin.auth.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: GPJ
 * @Description: 权限管理控制器
 * @Date Created in 13:58 2018/2/6
 * @Modified By:
 */
@RestController
@RequestMapping("/v1/auth/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    
    @ApiOperation(value = "查询权限列表树状结构（已实现）", notes = "查询权限列表树状结构", tags = {"auth", "auth-permission"})
    @GetMapping
    public RespJson list() {
        return RespJson.createSuccess(permissionService.findAllTree());
    }


    @ApiOperation(value = "保存或者编辑权限（已实现）", notes = "保存或者编辑权限", tags = {"auth", "auth-permission"})
    @PostMapping
    public RespJson save(@Validated @RequestBody PermissionParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        permissionService.save(param);
        return RespJson.createSuccess("操作成功");
    }

    @ApiOperation(value = "删除权限（已实现）", notes = "删除权限", tags = {"auth", "auth-permission"})
    @DeleteMapping("/{id:\\d+}")
    public RespJson deleteOne(@PathVariable("id") Integer id) {
        permissionService.removeById(id);
        return RespJson.createSuccess("删除成功");
    }


    @ApiOperation(value = "切换权限状态（已实现）", notes = "切换权限状态", tags = {"auth", "auth-permission"})
    @GetMapping("/{id:\\d+}/change-status")
    public RespJson changeStatus(@PathVariable("id") Integer id) {
        permissionService.changeStatus(id);
        return RespJson.createSuccess("切换状态成功");
    }

    @GetMapping("/tree")
    @ApiOperation(value = "查询树状结构用于 tree组件（已实现）", notes = "切换权限状态", tags = {"auth", "auth-permission"})
    public RespJson findRuleTree() {
        return RespJson.createSuccess(permissionService.findPermissionTree());
    }
    
}
