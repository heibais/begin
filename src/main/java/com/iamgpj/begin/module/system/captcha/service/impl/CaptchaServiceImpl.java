package com.iamgpj.begin.module.system.captcha.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.iamgpj.begin.core.enums.MessageType;
import com.iamgpj.begin.core.enums.RedisPrefixEnum;
import com.iamgpj.begin.core.util.RedisUtils;
import com.iamgpj.begin.core.util.ToolUtils;
import com.iamgpj.begin.module.system.captcha.dto.CaptchaDTO;
import com.iamgpj.begin.module.system.captcha.dto.EmailCaptchaDTO;
import com.iamgpj.begin.module.system.captcha.dto.SmsCaptchaDTO;
import com.iamgpj.begin.module.system.captcha.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/5/1 23:14
 */
@Service("captchaService")
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {
    // 开发者自己的AK(在阿里云访问控制台寻找)
    private static final String ACCESS_KEY_ID = "LTAIdKrFzPVJRoed";
    private static final String ACCESS_KEY_SECRET = "8pyPE6nrDtZQNdc66xn4f2eMhuvIS2";


    @Value("${spring.mail.username}")
    private String mailSender;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private RedisUtils redisUtils;


    @Override
    public void sendCaptcha(CaptchaDTO dto) {
        dto.setMessageType(MessageType.REGISTER_CPT);
        // 生成验证码
        String captcha = ToolUtils.getRandomNum(4);
        // 保存到缓存
        redisUtils.set(RedisPrefixEnum.CAPTCHA.getPrefix() + dto.getSendTo(), captcha, 300L);
        if (dto instanceof EmailCaptchaDTO) {
            // 发送邮箱
            EmailCaptchaDTO captchaDTO = (EmailCaptchaDTO) dto;
            // 解析消息模板
            String format = dto.getMessageType().getTpl().replace("${code}", captcha);
            captchaDTO.setContent(format);
            sendEmail(captchaDTO);
        } else {
            SmsCaptchaDTO captchaDTO = (SmsCaptchaDTO) dto;
            // 设置解析参数
            captchaDTO.setParams("{\"code\":\"" + captcha + "\"}");
            // 发送短信
            sendSms(captchaDTO);
        }
    }


    @Override
    public boolean matchCaptcha(String sendTo, String matchCaptcha) {
        if (!StringUtils.hasText(matchCaptcha)) {
            return false;
        }
        // 获取缓存中验证码
        String captcha = redisUtils.get(RedisPrefixEnum.CAPTCHA.getPrefix() + sendTo);
        if (captcha == null) {
            return false;
        }
        if (!captcha.equals(matchCaptcha)) {
           return false;
        }
        return true;
    }

    /**
     * 发送邮箱验证码
     * @param dto
     */
    private void sendEmail(EmailCaptchaDTO dto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 谁发的
        mailMessage.setFrom(mailSender);
        // 发给谁
        mailMessage.setTo(dto.getSendTo());
        // 标题
        mailMessage.setSubject(dto.getSubject());
        // 内容
        mailMessage.setText(dto.getContent());
        try {
            javaMailSender.send(mailMessage);
            log.info("【邮件验证码】已发送。发送至：{}",  dto.getSendTo());
        } catch (Exception e) {
            log.error("【邮件验证码】发送失败. 错误：{}", e);
        }

    }

    /**
     * 发送短信
     * @param dto
     * @return
     */
    private SendSmsResponse sendSms(SmsCaptchaDTO dto) {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(dto.getSendTo());
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("起风了");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(dto.getMessageType().getTplId());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(dto.getParams());

        SendSmsResponse acsResponse = null;
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
            IAcsClient acsClient = new DefaultAcsClient(profile);
            acsResponse = acsClient.getAcsResponse(request);
            log.info("【短信验证码】已发送。发送至：{}",  dto.getSendTo());
        } catch (ClientException e) {
            log.error("【短信验证码】发送失败. 错误：{}", e);
        }

        // 发送短信
        return acsResponse;
    }
}
