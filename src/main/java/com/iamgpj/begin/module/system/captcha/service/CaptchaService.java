package com.iamgpj.begin.module.system.captcha.service;

import com.iamgpj.begin.module.system.captcha.dto.CaptchaDTO;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/5/1 20:54
 */
public interface CaptchaService {

    /**
     * 发送验证码
     * @return
     */
    void sendCaptcha(CaptchaDTO dto);


    /**
     * 匹配验证码
     * @return
     */
    boolean matchCaptcha(String sendTo, String matchCaptcha);
}
