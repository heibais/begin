package com.iamgpj.begin.module.admin.auth.dto;

import com.iamgpj.begin.core.common.CommonTree;
import lombok.Data;

/**
 * @author: GPJ
 * @Description: 对应ant design 树形Tree控件
 * @Date Created in 16:14 2018/2/6
 * @Modified By:
 */
@Data
public class TreeDTO extends CommonTree {

    private String key;

    private String title;

}
