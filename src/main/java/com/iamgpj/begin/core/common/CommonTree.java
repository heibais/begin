package com.iamgpj.begin.core.common;

import lombok.Data;

import java.util.List;

/**
 * @author: GPJ
 * @Description:
 * @Date Created in 10:57 2018/2/7
 * @Modified By:
 */
@Data
public class CommonTree<T> {

    private Integer id;

    private Integer pid;

    private List<T> children;

}
