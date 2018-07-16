package com.iamgpj.begin.core.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: gpj
 * @Description: 统一异常处理
 * @Date: Created in 18:46 2018/1/28
 * @Modified By:
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public RespJson exceptionHandler(Exception e) {
        log.error("【异常捕获】 error => {}", e);
        return RespJson.createError(e.getMessage());
    }
}
