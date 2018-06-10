package com.iamgpj.begin.module.admin.auth.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: gpj
 * @Description: 部门参数
 * @Date: Created in 17:51 2018/1/28
 * @Modified By:
 */
@Data
public class DeptParam {

    @ApiModelProperty(value = "部门id")
    private Integer id;

    @ApiModelProperty(value = "上级部门id", required = true)
    @NotNull(message = "部门上级栏目不能为空")
    private Integer pid;

    @ApiModelProperty(value = "部门名称", required = true)
    @NotEmpty(message = "部门名称不能为空")
    @Length(min = 2, max = 16, message = "部门名称应在2-16个字符直间")
    private String name;

    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;
}
