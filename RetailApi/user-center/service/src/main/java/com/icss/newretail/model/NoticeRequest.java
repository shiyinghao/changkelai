/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知公告查询参数
 * 
 * @author zhangzhijia
 * @date Jul 11, 2019
 */
@Data
public class NoticeRequest {

  @ApiModelProperty(value = "所属组织编码")
  private String orgSeq;

  @ApiModelProperty(value = "通知标题")
  private String title;//模糊匹配

  @ApiModelProperty(value = "通知内容")
  private String content;//模糊匹配

  @ApiModelProperty(value = "发布人")
  private String publisher;

  @ApiModelProperty(value = "开始时间")
  private LocalDateTime beginTime;//发布时间

  @ApiModelProperty(value = "结束时间")
  private LocalDateTime endTime;

  @ApiModelProperty(value = "发布状态:1/0")
  private Integer status;


}
