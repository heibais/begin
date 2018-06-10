package com.iamgpj.begin.module.admin.auth.dto;

import com.iamgpj.begin.core.common.CommonTree;
import lombok.Data;

/**
 * @author: gpj
 * @Description: 部门传输数据
 * @Date: Created in 20:05 2018/1/30
 * @Modified By:
 */
@Data
public class DeptDTO extends CommonTree {

    /** react 推荐返回 */
    private Integer key;

    /** 部门名称 */
    private String name;

    /** 状态 */
    private Integer status;

    /** 排序 */
    private Integer sort;

    public Integer getKey() {
        return this.getId();
    }
}
