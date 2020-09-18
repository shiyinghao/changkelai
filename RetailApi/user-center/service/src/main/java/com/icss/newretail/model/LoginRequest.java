package com.icss.newretail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@ApiModel(value = "login接口入参", description = "login接口入参")
public class LoginRequest {

	@NotBlank(message = "认证ID不能为空")
	@ApiModelProperty(value = "认证ID")
	public String authId;

	@NotBlank(message = "密码不能为空")
	@ApiModelProperty(value = "密码")
	public String password;
	
}
