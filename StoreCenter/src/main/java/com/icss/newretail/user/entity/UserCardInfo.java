package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 名片信息表
 * </p>
 *
 * @author yanghu
 * @since 2020-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_card_info")
@ApiModel(value="UserCardInfo对象", description="名片信息表")
public class UserCardInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "名片类型(1-商品2-活动3-店铺)")
    @TableField("card_type")
    private Integer cardType;

    @ApiModelProperty(value = "名片名称")
    @TableField("card_name")
    private String cardName;

    @ApiModelProperty(value = "名片图片")
    @TableField("card_pic")
    private String cardPic;

    @ApiModelProperty(value = "字体颜色")
    @TableField("font_color")
    private String fontColor;

    @ApiModelProperty(value = "开始时间")
    @TableField("begin_time")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "发布状态(1-发布/0-不发布)")
    @TableField("publish_status")
    private Integer publishStatus;

    @ApiModelProperty(value = "状态(1-启用/0-弃用)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     *  2020-08-05  add
     */
    @ApiModelProperty(value = "背景底色")
    @TableField("background")
    private String background;

    @ApiModelProperty(value = "是否特殊(1-是/0-否)")
    @TableField("is_special")
    private Integer isSpecial;


}
