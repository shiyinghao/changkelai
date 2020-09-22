package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Response-返回单个结果
 * 
 * @param <T>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HwCloudResponseNewInstance extends HwCloudResponseBase {


  private static final long serialVersionUID = 487722698438469068L;

  @ApiModelProperty(value = "实例ID，服务商提供的唯一标识")
  private String instanceId;

  @ApiModelProperty(value = "敏感信息加密算法1：AES256_CBC_PKCS5Padding（默认值）2：AES128_CBC_PKCS5Padding")
  private String encryptType;

  @ApiModelProperty(value = "应用实例信息。客户购买商品后，服务商需要返回登录服务地址（网站地址）或免登地址供客户后续操作")
  private HwCloudAppInfoDTO appInfo;


  public HwCloudResponseNewInstance(HwCloudAppInfoDTO appInfo,String instanceId) {
    
    this.setInstanceId(instanceId);// 实例ID
    this.setEncryptType("2");// 实例类型
    this.setAppInfo(appInfo);
    this.setResultCode("000000");
    this.setResultMsg("创建实例成功");

  }

}
