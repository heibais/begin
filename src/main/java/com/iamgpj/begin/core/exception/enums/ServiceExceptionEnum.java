package com.iamgpj.begin.core.exception.enums;

/**
 * @Description: 抽象接口
 * @author: gpj
 * @Create: 2018/6/2 23:31
 */
public interface ServiceExceptionEnum {

    /**
     * 获取异常编码
     */
    Integer getCode();

    /**
     * 获取异常信息
     */
    String getMessage();
}
