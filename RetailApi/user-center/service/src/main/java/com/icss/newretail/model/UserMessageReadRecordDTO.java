package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息通知阅读记录表
 * </p>
 *
 * @author syh
 * @since 2020-08-20
 */
@Data
public class UserMessageReadRecordDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String uuid;

    @ApiModelProperty(value = "消息id")
    @TableField("message_id")
    private String messageId;

    @ApiModelProperty(value = "阅读时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "阅读人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "门店编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "基地名称")
    @TableField("base_name")
    private String baseName;

    @ApiModelProperty(value = "战区名称")
    @TableField("up_org_zone")
    private String upOrgZone;

    @ApiModelProperty(value = "授权码")
    @TableField("auth_code")
    private String authCode;

    @ApiModelProperty(value = "门店名称")
    @TableField("store_name")
    private String storeName;

    @ApiModelProperty(value = "开始时间")
    private String beginDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @ApiModelProperty(value = "消息主题")
    @TableField("message_title")
    private String messageTitle;

    @ApiModelProperty(value = "消息内容")
    @TableField("message_content")
    private String messageContent;

    private String fbsj;

}
