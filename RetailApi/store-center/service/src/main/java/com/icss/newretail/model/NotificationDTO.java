package com.icss.newretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 定制酒相关 通知参数
 */
@Data
public class NotificationDTO {


  //6	审核结果通知接口
  @JsonProperty("WorksID")
  @ApiModelProperty(value = "作品编号")
  private Integer WorksID;

  @JsonProperty("OutsideWorksID")
  @ApiModelProperty(value = "外部作品编号")
  private String OutsideWorksID;

  @JsonProperty("ResultID")
  @ApiModelProperty(value = "作品审核结果（1：通过；2：驳回）")
  private Integer ResultID;

  @JsonProperty("Reason")
  @ApiModelProperty(value = "审核备注")
  private String Reason;


  //8	订单定制通知接口 ----9 物流路由通知
  @JsonProperty("CoreOrderID")
  @ApiModelProperty(value = "订单编号//物流 订单编号")
  private Integer CoreOrderID;

  @JsonProperty("OutsideCoreOrderID")
  @ApiModelProperty(value = "外部订单编号//物流 外部订单编号")
  private String OutsideCoreOrderID;

  @JsonProperty("NodeCode")
  @ApiModelProperty(value = "节点编码（start:开始定制生产；finish:结束定制生产） // 物流 操作节点 分拣中心发货")
  private String NodeCode;


  //9 物流路由通知
  @JsonProperty("NodeMemo")
  @ApiModelProperty(value = "节点描述")
  private String NodeMemo;

  @JsonProperty("WaybillNumber")
  @ApiModelProperty(value = "物流路由通知 运单号")
  private String WaybillNumber;

  @JsonProperty("NodeTime")
  @ApiModelProperty(value = "节点操作时间")
  private LocalDateTime NodeTime;

  //共有参数
  @JsonProperty("Time")
  @ApiModelProperty(value = "作品审核时间//定制开始时间//路由推送时间")
  private LocalDateTime Time;

  @JsonProperty("Sign")
  @ApiModelProperty(value = "认证参数")
  private String Sign;


}
