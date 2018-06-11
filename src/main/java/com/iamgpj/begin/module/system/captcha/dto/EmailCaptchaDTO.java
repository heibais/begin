package com.iamgpj.begin.module.system.captcha.dto;

import lombok.Data;

/**
 * @Description: 邮箱验证码数据传输对象
 * @author: gpj
 * @Create: 2018/5/1 23:19
 */
@Data
public class EmailCaptchaDTO extends CaptchaDTO {

    /** 邮箱主体 */
    private String subject;

    /**发送的消息内容 */
    private String content;
}
