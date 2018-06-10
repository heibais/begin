package com.iamgpj.begin.module.admin.auth.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: GPJ
 * @Description:
 * @Date Created in 17:05 2018/2/7
 * @Modified By:
 */
@Data
public class RoleDTO {

    private Integer key;

    private Integer id;
    /** 角色名称 */
    private String name;
    /** 排序 */
    private Integer sort;
    /** 角色状态 */
    private Integer status;

    private List<Integer> permissionIdList;

    public Integer getKey() {
        return this.getId();
    }
}
