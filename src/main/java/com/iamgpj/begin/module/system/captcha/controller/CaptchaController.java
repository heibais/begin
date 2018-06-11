package com.iamgpj.begin.module.system.captcha.controller;

import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.module.system.captcha.dto.EmailCaptchaDTO;
import com.iamgpj.begin.module.system.captcha.dto.SmsCaptchaDTO;
import com.iamgpj.begin.module.system.captcha.service.CaptchaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: 验证码控制器
 * @author: gpj
 * @Create: 2018/5/1 20:49
 */
@RestController
@RequestMapping("/v1/sys/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @ApiOperation(value = "发送邮件验证码", notes = "发送邮件验证码", tags = {"sys", "sys: captcha"})
    @GetMapping("/email")
    public RespJson sendEmail(@RequestParam("sendTo") String emailTo) {
        EmailCaptchaDTO captchaDTO = new EmailCaptchaDTO();
        captchaDTO.setSendTo(emailTo);
        captchaDTO.setSubject("邮箱验证码");
        captchaService.sendCaptcha(captchaDTO);

        return RespJson.createSuccess(true);
    }

    @ApiOperation(value = "发送手机验证码", notes = "发送手机验证码", tags = {"sys", "sys: captcha"})
    @GetMapping("/mobile")
    public RespJson sendSms(@RequestParam("sendTo") String sendTo) {
        SmsCaptchaDTO captchaDTO = new SmsCaptchaDTO();
        captchaDTO.setSendTo(sendTo);
        captchaService.sendCaptcha(captchaDTO);
        return RespJson.createSuccess(true);
    }
}
