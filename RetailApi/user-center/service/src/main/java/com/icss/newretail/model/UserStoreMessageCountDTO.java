/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统消息数量
 * 
 * @author zhangzhijia
 * @date Jun 17, 2019
 */
@Data
public class UserStoreMessageCountDTO {
  
  @ApiModelProperty(value = "组织编码(一次只能推到摸个门店，不能指定具体终端)")
  private String orgSeq;

  @ApiModelProperty(value = "内容分类(1.商品；2活动；3告知)")
  private Integer contentsType;
  
  @ApiModelProperty(value = "是否阅读(1已经阅读，0未阅读)")
  private Integer isReaded;
  
  @ApiModelProperty(value = "消息数量")
  private Integer messCount;
  
}
