package com.iamgpj.begin.core.shiro.subject;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/11 17:53
 */
@Data
public class UserPrincipal implements Serializable {

    /**
     * 必有
     */
    /** 登录id */
    private Integer id;
    /** 登录用户名 */
    private String username;
    /** 昵称 */
    private String nickname;
    /** 头像 */
    private String avatar;
    /** 登录方式 */
    private String loginType;


    /**
     * 不一定有
     */
    /** 微信公众号 openId */
    private String wxOpenId;
    /** 微信公总号 unionId */
    private String wxUnionId;
}
