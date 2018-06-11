package com.iamgpj.begin.module.system.captcha.dto;

import lombok.Data;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/5/3 23:20
 */
@Data
public class SmsCaptchaDTO extends CaptchaDTO {

    /** 替换的参数，json */
    private String params;
}
