package com.iamgpj.begin.module.admin.auth.controller;

import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.module.admin.auth.param.RoleParam;
import com.iamgpj.begin.module.admin.auth.param.RolePermissionParam;
import com.iamgpj.begin.web.rule.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 角色控制器
 * @author: gpj
 * @Create: 2018/4/23 23:13
 */
@RestController
@RequestMapping("/v1/auth/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @ApiOperation(value = "查询角色列表（已实现）", notes = "查询角色列表", tags = {"auth", "auth-role"})
    @GetMapping
    public RespJson list() {
        return RespJson.createSuccess(roleService.findAll());
    }

    @ApiOperation(value = "查询简洁角色列表（已实现）", notes = "查询简洁角色列表", tags = {"auth", "auth-role"})
    @GetMapping("/simple")
    public RespJson simpleList() {
        return RespJson.createSuccess(roleService.findSimpleAll());
    }

    @ApiOperation(value = "保存或者编辑角色（已实现）", notes = "保存或者编辑角色", tags = {"auth", "auth-role"})
    @PostMapping
    public RespJson save(@Validated @RequestBody RoleParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        roleService.save(param);
        return RespJson.createSuccess("操作成功");
    }

    @ApiOperation(value = "删除角色（已实现）", notes = "删除角色", tags = {"auth", "auth-role"})
    @DeleteMapping("/{id:\\d+}")
    public RespJson deleteOne(@PathVariable("id") Integer id) {
        roleService.removeById(id);
        return RespJson.createSuccess("删除成功");
    }


    @ApiOperation(value = "切换角色状态（已实现）", notes = "切换角色状态", tags = {"auth", "auth-role"})
    @GetMapping("/{id:\\d+}/change-status")
    public RespJson changeStatus(@PathVariable("id") Integer id) {
        roleService.changeStatus(id);
        return RespJson.createSuccess("切换状态成功");
    }

    @ApiOperation(value = "授予角色权限（已实现）", notes = "授予角色权限", tags = {"auth", "auth-role"})
    @PostMapping("/savePermissions")
    public RespJson saveRolePermission(@Validated @RequestBody RolePermissionParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        roleService.saveRolePermission(param.getRoleId(), param.getPermissionIds());
        return RespJson.createSuccess("保存成功");
    }

}
