package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PromotionCouponInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
//    @ApiModelProperty(value = "主键id")
//    private String uuid;

    @ApiModelProperty(value = "卡卷编码")
    private String couponSeq;

    @ApiModelProperty(value = "卡券名称")
    private String couponName;

    @ApiModelProperty(value = "有效期类型(1-有效天数 2-有效日期)")
    private Integer validType;

    @ApiModelProperty(value = "适用范围(0通用、1指定商品)")
    private Integer useScope;

    @ApiModelProperty(value = "卡券有效天")
    private Integer validDate;

    @ApiModelProperty(value = "卡券使用金额限制")
    private BigDecimal conponLimitMoney;

    @ApiModelProperty(value = "卡券面额")
    private BigDecimal couponMoney;

    @ApiModelProperty(value = "折扣")
    private BigDecimal couponDiscount;

    @ApiModelProperty(value = "有效开始日期")
    private String validCreateDate;

    @ApiModelProperty(value = "有效截止日期")
    private String validEndDate;

    @ApiModelProperty(value = "卡券类型(1通用券、2定向券)")
    private Integer couponType;

    @ApiModelProperty(value = "卡券图片")
    private String couponPicture;

    @ApiModelProperty(value = "门店范围（0 通用，1指定门店）")
    private Integer isStore;

    @ApiModelProperty(value = "备注说明")
    private String comments;

    @ApiModelProperty(value = "使用状态（0停用/1可用/2已删除）")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    //------------------------

    @ApiModelProperty(value = "奖品库id")
    private String uuid;

    @ApiModelProperty(value = "奖品名称")
    private String prizeName;

    @ApiModelProperty(value = "奖品描述")
    private String prizeInfo;

    @ApiModelProperty(value = "奖品图片地址")
    private String prizePicture;

    @ApiModelProperty(value = "奖品编码")
    private String prizeCode;

    @ApiModelProperty(value = "奖品类型(1虚拟奖品2实物奖品)")
    private Integer prizeType;

    @ApiModelProperty(value = "卡券id")
    private String couponId;

//    @ApiModelProperty(value = "状态1-正在使用 0-不可使用")
//    private Integer status;
//
//    @ApiModelProperty(value = "创建时间")
//    private LocalDateTime createTime;
//
//    @ApiModelProperty(value = "创建人")
//    private String createUser;
//
//    @ApiModelProperty(value = "更新时间")
//    private LocalDateTime updateTime;
//
//    @ApiModelProperty(value = "更新人")
//    private String updateUser;


}
