package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 注册有礼活动赠品关联表
 * </p>
 *
 * @author ydt
 * @since 2020-07-21
 */
@Data
public class TPromotionPlanActivityGiftDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "活动id")
    @TableField("activity_id")
    private String activityId;

    @ApiModelProperty(value = "赠品id")
    @TableField("gift_id")
    private String giftId;

    @ApiModelProperty(value = "是否限量(1-是/0-否)")
    @TableField("is_limit_quantity")
    private Integer isLimitQuantity;

    @ApiModelProperty(value = "赠品数量")
    @TableField("gift_count")
    private Integer giftCount;

    @ApiModelProperty(value = "赠送数量")
    @TableField("give_count")
    private Integer giveCount;

    @ApiModelProperty(value = "有效期类型(1-天数/2-日期)")
    @TableField("valid_date_type")
    private Integer validDateType;

    @ApiModelProperty(value = "有效天数")
    @TableField("day_count")
    private Integer dayCount;

    @ApiModelProperty(value = "有效开始日期")
    @TableField("valid_begin_date")
    private String validBeginDate;

    @ApiModelProperty(value = "有效结束日期")
    @TableField("valid_end_date")
    private String validEndDate;

    @ApiModelProperty(value = "状态(1-启用/0-弃用)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建日期")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
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

    @ApiModelProperty(value = "卡卷详情")
    private PromotionCouponInfoDTO couponInfo;

    @ApiModelProperty(value = "有效期时间跨度")
    private String[] validDateRange;

    @Autowired
    public String[] getValidDateRange() {
        if (validDateType != null && validDateType == 2 && validBeginDate != null && validEndDate != null) {
            validDateRange = new String[]{validBeginDate, validEndDate};
        } else {
            validDateRange = new String[]{"", ""};
        }
        return validDateRange;
    }


}
