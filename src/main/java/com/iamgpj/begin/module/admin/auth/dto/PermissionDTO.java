package com.iamgpj.begin.module.admin.auth.dto;

import com.iamgpj.begin.core.common.CommonTree;
import lombok.Data;

/**
 * @author: GPJ
 * @Description:
 * @Date Created in 14:21 2018/2/6
 * @Modified By:
 */
@Data
public class PermissionDTO extends CommonTree {

    private Integer key;

    /** 权限名称 */
    private String name;
    /** 权限类型 */
    private Integer type;
    /** url地址 */
    private String url;
    /** 权限点 */
    private String permission;
    /** 图标 */
    private String icon;
    /** 状态 */
    private Integer status;
    /** 排序 */
    private Integer sort;

    public Integer getKey() {
        return this.getId();
    }
}
