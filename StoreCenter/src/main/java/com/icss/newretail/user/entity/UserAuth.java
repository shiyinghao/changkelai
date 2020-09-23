package com.icss.newretail.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="登录验证bean", description="登录验证bean")
public class UserAuth {
  @ApiModelProperty(value = "userId")
  public String userId;
  
  @ApiModelProperty(value = "认证类型 1用户名/2手机号/3邮箱")
  public String authMethod;
  
  @ApiModelProperty(value = "账号")
  public String account;
  
  @ApiModelProperty(value = "密码")
  public String password;
  
  @ApiModelProperty(value = "租户ID")
  public String tenantId;
}
