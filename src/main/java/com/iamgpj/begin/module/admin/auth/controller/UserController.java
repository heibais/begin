package com.iamgpj.begin.module.admin.auth.controller;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.shiro.subject.UserPrincipal;
import com.iamgpj.begin.core.util.ShiroUtils;
import com.iamgpj.begin.module.admin.auth.param.UpdatePwdParam;
import com.iamgpj.begin.module.admin.auth.param.UserParam;
import com.iamgpj.begin.module.admin.auth.param.UserRoleParam;
import com.iamgpj.begin.module.admin.auth.param.UserSearchParam;
import com.iamgpj.begin.module.admin.auth.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: gpj
 * @Description: 用户管理控制器
 * @Date: Created in 18:28 2018/1/28
 * @Modified By:
 */
@RestController
@RequestMapping("/v1/auth/user")
public class UserController {

    @Resource
    private UserService userService;


    @ApiOperation(value = "查询用户列表（已实现）", notes = "查询用户列表", tags = {"auth", "auth-user"})
    @GetMapping
    public RespJson list(@PageableDefault Pagination pageable, UserSearchParam search) {
        return RespJson.createSuccess(userService.findAll(pageable, search));
    }

    @ApiOperation(value = "获取当前用户（已实现）", notes = "查询用户", tags = {"auth", "auth-user"})
    @GetMapping("/current-user")
    public RespJson findCurrentUser() {
        UserPrincipal principal = ShiroUtils.getUserPrincipal();
        if (principal == null) {
            return RespJson.createError("用户未登录");
        }
        return RespJson.createSuccess(principal);
    }


    @ApiOperation(value = "通过用户id查询角色（已实现）", notes = "通过用户id查询角色", tags = {"auth", "auth-user"})
    @GetMapping("/{id:\\d+}/roles")
    public RespJson findMyRoles(@PathVariable("id") Integer id) {
        return RespJson.createSuccess(userService.findRoleIdsByUserId(id));
    }


    @ApiOperation(value = "保存或者编辑用户（已实现）", notes = "保存或者编辑用户", tags = {"auth", "auth-user"})
    @PostMapping
    public RespJson save(@Validated @RequestBody UserParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        userService.save(param);
        return RespJson.createSuccess("操作成功");
    }

    @ApiOperation(value = "更新用户密码（已实现）", notes = "更新用户密码", tags = {"auth", "auth-user"})
    @PostMapping("/{id:\\d+}/change-pwd")
    public RespJson updatePassword(@PathVariable("id") Integer userId,
                                   @Validated @RequestBody UpdatePwdParam param,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        userService.updatePassword(userId, param);
        return RespJson.createSuccess("更新成功");
    }

    @ApiOperation(value = "删除用户（已实现）", notes = "删除用户", tags = {"auth", "auth-user"})
    @DeleteMapping("/{id:\\d+}")
    public RespJson deleteOne(@PathVariable("id") Integer id) {
        userService.removeById(id);
        return RespJson.createSuccess("删除成功");
    }

    @ApiOperation(value = "切换状态（已实现）", notes = "切换状态", tags = {"auth", "auth-user"})
    @GetMapping("/{id:\\d+}/change-status")
    public RespJson changeStatus(@PathVariable("id") Integer id) {
        userService.changeStatus(id);
        return RespJson.createSuccess("切换状态成功");
    }

    @ApiOperation(value = "保存用户角色（已实现）", notes = "保存用户角色", tags = {"auth", "auth-user"})
    @PostMapping("/{id:\\d+}/save-role")
    public RespJson saveUserRole(@PathVariable("id") Integer userId,
                                 @Validated @RequestBody UserRoleParam param,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeginException(bindingResult.getFieldError().getDefaultMessage());
        }
        userService.saveUserRole(userId, param.getRoleIds());
        return RespJson.createSuccess("保存成功");
    }
}
