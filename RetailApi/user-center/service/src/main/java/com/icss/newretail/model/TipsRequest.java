/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 使用条款查询参数
 * 
 * @author zhangzhijia
 * @date Jul 10, 2019
 */
public class TipsRequest {
  
  @ApiModelProperty(value = "应用id")
  private String appId;

  @ApiModelProperty(value = "条款内容")
  private String content;// 模糊匹配

  @ApiModelProperty(value = "条款版本")
  private String version;

  @ApiModelProperty(value = "状态(0禁用/1启用)")
  private Integer status;


}
