package com.icss.newretail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 租户信息查询参数
 * 
 * @author zhangzhijia
 * @date Jun 12, 2019
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "登录页信息接口入参", description = "登录页信息接口入参")
public class TenantRequest {
  
  @NotBlank(message = "租户ID")
  @ApiModelProperty(value = "租户ID",hidden = true)
  public String tenantId;
}
