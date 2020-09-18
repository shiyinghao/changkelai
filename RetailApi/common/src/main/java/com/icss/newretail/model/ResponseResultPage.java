/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Response-返回分页结果（含合计数据）
 * 
 * @author zhangzhijia
 * @date Apr 17, 2019
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResponseResultPage<T> extends ResponseRecords<T> {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "总记录数")
  private long total;

  @ApiModelProperty(value = "每页显示数量")
  private long size = 10; // 每页显示数量 默认10

  @ApiModelProperty(value = "当前页码")
  private long current = 1; // 当前页码 默认1
  
  public ResponseResultPage() {
    super();
  }
  
  public ResponseResultPage(int code, String message) {
    super(code, message);
  }
  
}
