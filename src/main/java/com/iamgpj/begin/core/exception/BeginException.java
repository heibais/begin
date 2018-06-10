package com.iamgpj.begin.core.exception;

import com.iamgpj.begin.core.exception.enums.ServiceExceptionEnum;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/6/2 23:29
 */
public class BeginException extends RuntimeException{

    private Integer code;

    private String message;

    public BeginException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public BeginException(String message) {
        this.message = message;
        this.code = 400;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
