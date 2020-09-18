package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 买赠活动商品赠品关联表
 * </p>
 *
 * @author ydt
 * @since 2020-07-09
 */
@Data
public class TPromotionBuyActivityGoodsGiftDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String uuid;

    @ApiModelProperty(value = "活动id")
    private String activityId;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "活动商品id")
    private String activityGoodsId;

    @ApiModelProperty(value = "赠品id")
    private String giftId;

    @ApiModelProperty(value = "是否限量(1-是/0-否)")
    private Integer isLimitQuantity;

    @ApiModelProperty(value = "赠送数量")
    private Integer giveCount;

    @ApiModelProperty(value = "赠品总量")
    private Integer giftSumQuantity;

    @ApiModelProperty(value = "赠品剩余数量")
    private Integer giftQuantity;

    @ApiModelProperty(value = "有效期类型(1-天数/2-日期)")
    private Integer validDateType;

    @ApiModelProperty(value = "有效天数")
    private Integer dayCount;

    @ApiModelProperty(value = "有效开始日期")
    private String validBeginDate;

    @ApiModelProperty(value = "有效截止日期")
    private String validEndDate;

    @ApiModelProperty(value = "状态(1-启用/0-弃用)")
    private Integer status;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "赠品详情")
    private TPromotionGiftLibraryDTO giftLibraryDTOList;

    @ApiModelProperty(value = "赠品名称")
    private String giftName;

    @ApiModelProperty(value = "赠品编码")
    private String giftCode;

    @ApiModelProperty(value = "赠品类型(1-实物赠品/2虚拟赠品)")
    private Integer giftType;

    @ApiModelProperty(value = "赠品属性(1-普通虚拟赠品/2-特殊虚拟赠品)")
    private Integer giftProperty;

    @ApiModelProperty(value = "赠品类型(1-实物/2-优惠券/3-抽奖次数/4-机场)")
    private Integer giftStyle;

    @ApiModelProperty(value = "配送方式(1-总部配送)")
    private Integer deliveryType;

    @ApiModelProperty(value = "赠品图片")
    private String giftPic;

    @ApiModelProperty(value = "赠品描述")
    private String giftDesc;

    @ApiModelProperty(value = "卡券id")
    private String couponId;

    @ApiModelProperty(value = "有效期时间跨度")
    private String[] validDateRange;
    
    @Autowired
	public String[] getValidDateRange() {
    	if(validDateType != null && validDateType == 2 && validBeginDate != null && validEndDate != null){
    		validDateRange = new String[]{validBeginDate, validEndDate};
    	}else{
    		validDateRange = new String[]{"", ""};
    	}
		return validDateRange;
	}
    
}
