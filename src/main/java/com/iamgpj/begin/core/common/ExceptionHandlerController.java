package com.iamgpj.begin.core.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: gpj
 * @Description: 统一异常处理
 * @Date: Created in 18:46 2018/1/28
 * @Modified By:
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public RespJson exceptionHandler(Exception e) {
        return RespJson.createError(e.getMessage());
    }
}
