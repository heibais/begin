package com.iamgpj.begin.module.admin.auth.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/9 2:01
 */
@Data
public class RolePermissionParam {

    @ApiModelProperty(value = "角色id")
    @NotNull(message = "角色id不能为空")
    private Integer roleId;

    @ApiModelProperty(value = "权限")
    @NotEmpty(message = "权限不能为空")
    private String permissionIds;
}
