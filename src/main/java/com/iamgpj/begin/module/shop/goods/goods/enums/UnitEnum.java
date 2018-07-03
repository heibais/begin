package com.iamgpj.begin.module.shop.goods.goods.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * @Description: 单位枚举
 * @author: GPJ
 * @Create: 2018/6/26 11:55
 */
public enum  UnitEnum implements IEnum {
    /**
     * 重量单位
     */
    G(1, "克"),
    KG(2, "千克")
    ;

    private Integer value;

    private String unitName;

    UnitEnum(Integer value, String unitName) {
        this.value = value;
        this.unitName = unitName;
    }

    public void setValue(Integer code) {
        this.value = code;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public Serializable getValue() {
        return value;
    }
}
