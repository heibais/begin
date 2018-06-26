package com.iamgpj.begin.core.enums;

/**
 * @Description: 单位枚举
 * @author: GPJ
 * @Create: 2018/6/26 11:55
 */
public enum  UnitEnum {
    /**
     * 重量单位
     */
    G(1, "克"),
    KG(2, "千克")
    ;

    private Integer code;

    private String unitName;

    UnitEnum(Integer code, String unitName) {
        this.code = code;
        this.unitName = unitName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
