package com.iamgpj.begin.module.admin.auth.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: GPJ
 * @Description: 权限参数
 * @Date Created in 14:07 2018/2/6
 * @Modified By:
 */
@Data
public class PermissionParam {

    @ApiModelProperty(value = "权限id")
    private Integer id;

    @ApiModelProperty(value = "上级权限", required = true)
    @NotNull(message = "上级权限不能为空")
    private Integer pid;

    @ApiModelProperty(value = "权限名称", required = true)
    @NotEmpty(message = "权限名称不能为空")
    @Length(min = 2, max = 16, message = "权限名称应在2-16个字符直间")
    private String name;

    @ApiModelProperty(value = "权限类型", required = true)
    @NotNull(message = "权限类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "url地址")
    private String url = "";

    @ApiModelProperty(value = "权限点", required = true)
    private String permission;

    @ApiModelProperty(value = "图标")
    private String icon = "";

    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;
}
