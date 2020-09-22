/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用使用条款-WechatTips
 * 
 * @author zhangzhijia
 * @date Jul 10, 2019
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TipsDTO对象", description = "应用使用条款")
public class TipsDTO {

  @ApiModelProperty(value = "uuid")
  private Integer uuid;

  @ApiModelProperty(value = "应用id")
  private String appId;

  @ApiModelProperty(value = "条款内容")
  private String content;

  @ApiModelProperty(value = "条款版本")
  private String version;

  @ApiModelProperty(value = "状态(0禁用/1启用)")
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
