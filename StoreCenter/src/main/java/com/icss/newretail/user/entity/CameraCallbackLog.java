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
 * 视像头回写日志
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_camera_callback_log")
@ApiModel(value="CameraCallbackLog对象", description="视像头回写日志")
public class CameraCallbackLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "回写编码")
    @TableId("msg_id")
    private String msgId;

    @ApiModelProperty(value = "同步内容")
    @TableField("msg_content")
    private String msgContent;

    @ApiModelProperty(value = "错误信息")
    @TableField("error_msg")
    private String errorMsg;

    @ApiModelProperty(value = "状态1成功0失败")
    @TableField("status")
    private Integer status;

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
