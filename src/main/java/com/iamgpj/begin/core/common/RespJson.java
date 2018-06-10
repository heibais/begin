package com.iamgpj.begin.core.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author: GPJ
 * @Description: 通用json返回类
 * @Date Created in 17:18 2017/12/27
 * @Modified By:
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespJson<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private RespJson(int code) {
        this.code = code;
    }

    private RespJson(String msg) {
        this.msg = msg;
    }

    private RespJson(T data) {
        this.data = data;
    }

    private RespJson(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private RespJson(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private RespJson(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == HttpStatus.OK.value();
    }

    public static RespJson createSuccess() {
        return new RespJson(HttpStatus.OK.value());
    }

    public static RespJson createSuccess(String msg) {
        return new RespJson(HttpStatus.OK.value(), msg);
    }

    public static <T> RespJson<T> createSuccess(T data) {
        return new RespJson<>(HttpStatus.OK.value(), data);
    }

    public static <T> RespJson<T> createSuccess(String msg, T data) {
        return new RespJson<>(HttpStatus.OK.value(), msg, data);
    }

    public static RespJson createError() {
        return new RespJson(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static RespJson createError(String msg) {
        return new RespJson(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static RespJson createError(int code, String msg) {
        return new RespJson(code, msg);
    }
}