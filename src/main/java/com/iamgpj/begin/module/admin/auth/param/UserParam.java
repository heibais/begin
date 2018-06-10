package com.iamgpj.begin.module.admin.auth.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: gpj
 * @Description: 用户参数校验类
 * @Date: Created in 19:13 2018/1/28
 * @Modified By:
 */
@Data
public class UserParam {

    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 2, max = 16, message = "用户名应在2-16个字符之间")
    private String username;

    @ApiModelProperty(value = "密码")
    @Length(min = 6, max = 64, message = "密码应在6-64个字符之间")
    private String password;

    @ApiModelProperty(value = "昵称")
    @Length(min = 2, max = 16, message = "昵称应在2-16个字符之间")
    private String nickname = "";

    @ApiModelProperty(value = "头像")
    private String avatar = "";

    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email = "";

    @ApiModelProperty(value = "手机")
    private String mobile = "";

    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;
}
