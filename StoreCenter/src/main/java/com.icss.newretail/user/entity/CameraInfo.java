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
 * 摄像头配置信息
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_camera_info")
@ApiModel(value="CameraInfo对象", description="摄像头配置信息")
public class CameraInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "UUID")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "组织编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "视像头ID")
    @TableField("camera_id")
    private String cameraId;

    @ApiModelProperty(value = "视像头位置(1进店 2POS 3出店)")
    @TableField("camera_location")
    private Integer cameraLocation;

    @ApiModelProperty(value = "状态")
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
