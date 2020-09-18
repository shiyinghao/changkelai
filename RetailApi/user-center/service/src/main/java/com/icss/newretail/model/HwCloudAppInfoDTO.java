package com.icss.newretail.model;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 应用实例信息。客户购买商品后，服务商需要返回登录服务地址（网站地址）或免登地址供客户后续操作
 */
@Data
public class HwCloudAppInfoDTO {

  @ApiModelProperty(value = "前台地址。客户购买商品后，可以访问的网站地址。")
  private String frontEndUrl;

  @ApiModelProperty(value = "管理地址。客户购买商品后，可以访问的管理后台地址。")
  private String adminUrl;

  @ApiModelProperty(value = "加密后的管理员帐号。客户购买商品后，访问服务商管理后台的账号（一般为邮箱和手机号）。该值由16位iv加密向量和base编码后的用户名密文组成。")
  private String userName;

  @ApiModelProperty(value = "加密后的管理员初始密码。客户购买商品后，访问服务商管理后台的密码（一般由服务商生成）。该值由16位iv加密向量和base编码后的密码密文组成。")
  private String password;

  @ApiModelProperty(value = "网站的IP")
  private String ip;

  @ApiModelProperty(value = "备注")
  private String memo;

  public HwCloudAppInfoDTO() {
    super();
  }

  /**
   * 根据客户信息生成应用实例初始信息
   * 
   * @param customInfo
   */
  public HwCloudAppInfoDTO(Map<String, String[]> customInfo) {
    this.setFrontEndUrl("mt.wssaa.com");
    this.setAdminUrl("http://mt.wssaa.com/ui/index.html");
    this.setUserName("admin");
    this.setPassword("1");
  }

}
