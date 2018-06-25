package com.iamgpj.begin.module.shop.goods.gallery.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.iamgpj.begin.core.biz.mybatisPlus.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Description:
 * @author: GPJ
 * @Create: 2018/6/25 11:58
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "bg_shop_gallery")
public class Gallery extends SuperEntity {
    /** 用户id */
    private Integer userId;
    /** 商品id */
    private Integer goodsId;
    /** 商品图片 */
    private String imgUrl;
    /** 商品描述 */
    private String imgDesc;
    /** 商品缩略图 */
    private String thumbUrl;
    /** 商品原图 */
    private String imgOriginal;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
