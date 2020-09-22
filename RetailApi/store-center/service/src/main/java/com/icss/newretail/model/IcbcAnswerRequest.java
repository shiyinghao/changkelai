package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 工行应答参数
 * 
 * @author zhangliang
 * @date 2020-04-20
 */
@Data
public class IcbcAnswerRequest {

  @ApiModelProperty(value = "key")
  private String key;

  
  @ApiModelProperty(value = "开户申请单id/开票申请单id/工单申请单id")
  private String submitId;

  @ApiModelProperty(value = "开户审核状态/开票结果状态/反馈结果状态")
  private String submitStatus;

  @ApiModelProperty(value = "开户审核说明/开票结果说明/反馈结果说明")
  private String submitMsg;

  @ApiModelProperty(value = "工行商户编号")
  private String merId;

  @ApiModelProperty(value = "工行商户协议号")
  private String merPrtclNo;

  @ApiModelProperty(value = "微信识别码")
  private String icbcAppid;

  @ApiModelProperty(value = "建档日期")
  private String createDate;

  
  @ApiModelProperty(value = "实际开票金额")
  private BigDecimal invoiceMoney;

  @ApiModelProperty(value = "运单号")
  private String expressNo;

  @ApiModelProperty(value = "开票日期")
  private String invoiceDate;


  @ApiModelProperty(value = "工单处理时间")
  private LocalDateTime processTime;

}
