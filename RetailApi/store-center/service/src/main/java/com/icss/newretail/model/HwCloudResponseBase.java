package com.icss.newretail.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * HwCloudResponseBase-响应消息
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HwCloudResponseBase implements Serializable {

  private static final long serialVersionUID = 8676511746535928167L;

  /**
   * 000000成功 
   * 000001鉴权失败 
   * 000002请求参数不合法 
   * 000003实例ID不存在（商品续费、过期、资源释放接口可能返回） 
   * 000004请求处理中 
   * 000005其它服务内部错误 
   * 000100无可用实例资源分配
   * 
   */
  @ApiModelProperty(value = "调用结果")
  private String resultCode = "000000";// 默认成功

  @ApiModelProperty(value = "调用结果描述")
  private String resultMsg;

  public HwCloudResponseBase() {
    super();
  }

  public HwCloudResponseBase(String resultCode, String resultMsg) {
    super();
    this.resultCode = resultCode;
    this.resultMsg = resultMsg;
  }

}
