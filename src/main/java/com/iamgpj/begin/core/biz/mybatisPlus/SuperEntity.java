package com.iamgpj.begin.core.biz.mybatisPlus;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/19 13:54
 */
@Data
public class SuperEntity implements Serializable {

    private Integer id;

    /**
     * 是否删除
     */
    /*@TableLogic
    @TableField(fill = FieldFill.INSERT)
    protected Boolean deleted;*/
}
