package com.iamgpj.begin.core.exception.enums;

/**
 * @Description: 所有业务异常的枚举
 * @author: gpj
 * @Create: 2018/6/2 23:33
 */
public enum ExceptionEnum implements ServiceExceptionEnum {
    /**
     * 权限
     */
    EXIST_CHILDREN(1, "存在子栏目"),
    DATA_EXISTED(2, "数据已存在"),
    MOBILE_EXISTED(3, "手机已存在"),
    EMAIL_EXISTED(4, "邮箱已存在"),
    PASSWORD_NULL(5, "密码不能为空"),
    PASSWORD_ERROE(6, "密码输入有误"),
    USER_LOCKED(7, "用户已被禁用"),

    /**
     * 文件
     */
    FILE_UPLOAD_FAIL(11, "上传文件失败"),

    /**
     * 错误的请求
     */
    REQUEST_ERROR(400, "请求有错误"),
    SESSION_TIMEOUT(400, "会话超时"),
    SERVER_ERROR(500, "服务器异常");

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
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
