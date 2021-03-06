package com.iamgpj.begin.module.shop.goods.category.dto;

import com.iamgpj.begin.core.common.CommonTree;
import lombok.Data;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/18 18:06
 */
@Data
public class CategoryDTO extends CommonTree {

    private Integer key;
    /** 用户id */
    private Integer userId;
    /** 分类名称 */
    private String name;
    /** 排序 */
    private Integer sort;
    /** 是否显启用 */
    private Integer status;
    /** 是否推荐 */
    private Integer recommend;

    public Integer getKey() {
        return this.getId();
    }
}
