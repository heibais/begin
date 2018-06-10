package com.iamgpj.begin.module.admin.auth.param;

import lombok.Data;

/**
 * @Description: 用户查询
 * @author: gpj
 * @Create: 2018/6/9 18:22
 */
@Data
public class UserSearchParam {

    private String username;

    private String mobile;

    private String email;
}
