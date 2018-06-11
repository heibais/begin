package com.iamgpj.begin.core.enums;


/**
 * @Description: 消息类型
 * @author: gpj
 * @Create: 2018/5/1 23:05
 *
 * <p>
 * Captcha 验证码：(CPT)
 * <p>
 */
public enum MessageType {

    REGISTER_CPT("register_cpt", "注册", "SMS_134090234", "您的注册码：${code}，如非本人操作，请忽略本短信！", 0),
    ;

    private String value;

    private String desc;

    private String tplId;

    private String tpl;

    private Integer toPush;

    MessageType(String value, String desc, String tplId, String tpl, Integer toPush) {
        this.value = value;
        this.desc = desc;
        this.tplId = tplId;
        this.tpl = tpl;
        this.toPush = toPush;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getTplId() {
        return tplId;
    }

    public String getTpl() {
        return tpl;
    }

    public Integer getToPush() {
        return toPush;
    }
}
