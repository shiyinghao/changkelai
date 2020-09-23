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
 * 消息通知阅读记录表
 * </p>
 *
 * @author syh
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_message_read_record")
@ApiModel(value="UserMessageReadRecord对象", description="消息通知阅读记录表")
public class UserMessageReadRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId("uuid")
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


}
