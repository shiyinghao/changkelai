package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 买赠活动赠品库
 * </p>
 *
 * @author ydt
 * @since 2020-07-09
 */
@Data
public class TPromotionGiftLibraryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String uuid;

    @ApiModelProperty(value = "赠品名称")
    private String giftName;

    @ApiModelProperty(value = "赠品编码")
    private String giftCode;

    @ApiModelProperty(value = "赠品类型(1-实物赠品/2虚拟赠品)")
    private Integer giftType;

    @ApiModelProperty(value = "赠品属性(1-普通虚拟赠品/2-特殊虚拟赠品)")
    private Integer giftProperty;

    @ApiModelProperty(value = "赠品类型(1-实物/2-优惠券/3-抽奖次数/4-机场/5-积分)")
    private Integer giftStyle;

    @ApiModelProperty(value = "配送方式(1-总部配送)")
    private Integer deliveryType;

    @ApiModelProperty(value = "赠品图片")
    private String giftPic;

    @ApiModelProperty(value = "赠品描述")
    private String giftDesc;

    @ApiModelProperty(value = "卡券id")
    private String couponId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "卡券详情")
    private PromotionCouponInfoDTO couponInfo;

    @ApiModelProperty(value = "已选择得赠品id")
    private List<String> includeGifts;

    @ApiModelProperty(value = "所属活动id（应用场景：活动配置挑选赠品列表）")
    private String activityId;

    @ApiModelProperty(value = "所属活动商品主键id（应用场景：活动配置挑选赠品列表）")
    private String activityGoodsId;

    @ApiModelProperty(value = "所属买赠活动名称")
    private String activityName;


    @ApiModelProperty(value = "所属分享活动id（应用场景：分享活动配置挑选赠品列表）")
    private String shareActivityId;

    @ApiModelProperty(value = "所属注册有礼活动id（应用场景：注册有礼活动配置挑选赠品列表）")
    private String planActivityId;

    @ApiModelProperty(value = "活动类型（0-无 1-买赠活动 2-分享活动 3-注册有礼）")
    private Integer activityType;


    //++++++抽奖活动需要+++++++++++++++
    @ApiModelProperty(value = "本次消耗积分")
    private String score;

    @ApiModelProperty(value = "奖品等级")
    private String prizeLevel;

    @ApiModelProperty(value = "奖品等级")
    private String prizeLevelName;


}
