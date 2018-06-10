package com.iamgpj.begin.module.admin.auth.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: GPJ
 * @Description: 角色参数
 * @Date Created in 15:41 2018/2/6
 * @Modified By:
 */
@Data
public class RoleParam {

    @ApiModelProperty(value = "角色id")
    private Integer id;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotEmpty(message = "角色名称不能为空")
    @Length(min = 2, max = 16, message = "角色名称应在2-16个字符直间")
    private String name;

    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;
}
