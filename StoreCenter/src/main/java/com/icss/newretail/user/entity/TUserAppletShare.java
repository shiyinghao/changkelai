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
 * 小程序分享码保存表
 * </p>
 *
 * @author ydt
 * @since 2020-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_applet_share")
@ApiModel(value = "TUserAppletShare对象", description = "小程序分享码保存表")
public class TUserAppletShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "分享码类型（1-小程序分享码）")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "微信识别码")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "门店编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "小程序分享码")
    @TableField("share_applet_pic")
    private String shareAppletPic;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备用字段1(电话号码)")
    @TableField("spare_one")
    private String spareOne;

    @ApiModelProperty(value = "备用字段2")
    @TableField("spare_two")
    private String spareTwo;


}
