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
 * 终端通知信息
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_message")
@ApiModel(value="UserStoreMessage对象", description="终端通知信息")
public class UserStoreMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织编码(一次只能推到摸个门店，不能指定具体终端)")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "设备ID")
    @TableField("device_id")
    private String deviceId;

    @ApiModelProperty(value = "消息ID")
    @TableId("message_id")
    private String messageId;

    @ApiModelProperty(value = "内容分类(1.商品；2活动；3告知)")
    @TableField("contents_type")
    private Integer contentsType;

    @ApiModelProperty(value = "推送方式(1.手工；2系统)")
    @TableField("pushmethod")
    private Integer pushmethod;

    @ApiModelProperty(value = "消息标题")
    @TableField("message_title")
    private String messageTitle;

    @ApiModelProperty(value = "消息内容")
    @TableField("message_contents")
    private String messageContents;

    @ApiModelProperty(value = "详细内容的URL")
    @TableField("detailedcontents_url")
    private String detailedcontentsUrl;

    @ApiModelProperty(value = "发布时间")
    @TableField("send_time")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "有效时间")
    @TableField("valid_time")
    private LocalDateTime validTime;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "是否阅读(1已经阅读，0未阅读)")
    @TableField("is_readed")
    private Integer isReaded;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

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


}
