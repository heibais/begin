package com.iamgpj.begin.core.enums;

/**
 * @Description: redis 缓存前缀
 * @author: gpj
 * @Create: 2018/6/11 22:15
 */
public enum  RedisPrefixEnum {
    CAPTCHA("captcha:", "验证码缓存", 300L);


    private String prefix;

    private String desc;

    private Long expire;

    RedisPrefixEnum(String prefix, String desc, Long expire) {
        this.prefix = prefix;
        this.desc = desc;
        this.expire = expire;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
