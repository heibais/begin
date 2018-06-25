package com.iamgpj.begin.core.biz.mybatisPlus;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/19 13:54
 */
public class SuperEntity implements Serializable {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 是否删除
     */
    /*@TableLogic
    @TableField(fill = FieldFill.INSERT)
    protected Boolean deleted;*/
}
