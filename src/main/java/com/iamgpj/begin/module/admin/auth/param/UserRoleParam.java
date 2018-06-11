package com.iamgpj.begin.module.admin.auth.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/9 18:52
 */
@Data
public class UserRoleParam {

    @ApiModelProperty(value = "角色")
    @NotEmpty(message = "角色不能为空")
    private String roleIds;
}
