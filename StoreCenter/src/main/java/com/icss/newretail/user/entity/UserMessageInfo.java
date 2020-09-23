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
 * 消息通知表
 * </p>
 *
 * @author syh
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_message_info")
@ApiModel(value="UserMessageInfo对象", description="消息通知表")
public class UserMessageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "推送编号")
    @TableField("message_seq")
    private String messageSeq;

    @ApiModelProperty(value = "消息主题")
    @TableField("message_title")
    private String messageTitle;

    @ApiModelProperty(value = "消息内容")
    @TableField("message_content")
    private String messageContent;

    @ApiModelProperty(value = "状态(0-未发布1-发布)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "发布时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "发布人")
    @TableField("update_user")
    private String updateUser;


}
