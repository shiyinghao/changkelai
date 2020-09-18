package com.icss.newretail.model;

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
@ApiModel(value = "CameraInfo对象", description = "摄像头配置信息")
public class CameraInfoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "UUID")
  private String uuid;

  @ApiModelProperty(value = "组织编码")
  private String orgSeq;

  @ApiModelProperty(value = "视像头ID")
  private String cameraId;

  @ApiModelProperty(value = "视像头位置(1进店 2POS 3出店)")
  private Integer cameraLocation;

  @ApiModelProperty(value = "状态")
  private Integer status;

  @ApiModelProperty(value = "创建人")
  private String createUser;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "更新人")
  private String updateUser;

  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updateTime;

}
