package com.iamgpj.begin.module.system.captcha.dto;

import com.iamgpj.begin.core.enums.MessageType;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 验证码
 * @author: gpj
 * @Create: 2018/5/1 23:03
 */
@Data
public class CaptchaDTO implements Serializable {

    /**
     * 获取消息类型
     * @return
     */
    private MessageType messageType;


    /**
     * 发送给谁
     */
    private String sendTo;
}
