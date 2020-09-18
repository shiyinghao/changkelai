/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户菜单查询参数
 * 
 * @author zhangzhijia
 * @date May 23, 2019
 */
@Data
public class UserMenuRequest {
  
  @ApiModelProperty(value = "用户ID")
  private String userId;

  @ApiModelProperty(value = "应用ID")
  private String appId;
  
  @ApiModelProperty(value = "模块ID")
  private String moduleId;

  @ApiModelProperty(value = "上级菜单ID")
  private String upResourceReq;
  
  @ApiModelProperty(value = "菜单级别")
  private String resourceLevel;

  @ApiModelProperty(value = "状态")
  private String status;
}
