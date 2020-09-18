package com.icss.newretail.model;

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "loginIn接口入参")
public class LoginInRequest {

	
	@ApiModelProperty(value = "密码")
	public String password;
	
	@ApiModelProperty(value = "手机号码")
	public String tel;

}
