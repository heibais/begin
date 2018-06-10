package com.iamgpj.begin.module.admin.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * @author: gpj
 * @Description: 用户传输数据
 * @Date: Created in 21:48 2018/2/5
 * @Modified By:
 */
@Data
public class UserDTO {

    /** react 推荐返回 */
    private Integer key;

    private Integer id;
    /** 用户名 */
    private String username;;
    /** 密码 */
    @JsonIgnore
    private String password;
    /** 盐 */
    @JsonIgnore
    private String salt;
    /** 昵称 */
    private String nickname;
    /** 头像 */
    private String avatar;
    /** 邮箱 */
    private String email;
    /** 手机 */
    private String mobile;
    /** 状态 */
    private Integer status;

    /** 角色id集合 */
    private List<String> roleIdList;

    public Integer getKey() {
        return this.getId();
    }
}
