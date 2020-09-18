package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UmsParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "第一个参数")
	private String parmA;

	@ApiModelProperty(value = "第二个参数")
	private String parmB;

	@ApiModelProperty(value = "第三个参数")
	private String parmC;

	@ApiModelProperty(value = "发送短信号码")
	private String phone;

	@ApiModelProperty(value = "短信模板id")
	private String code;

	@ApiModelProperty(value = "集团编码")
	private String spCode;

	@ApiModelProperty(value = "登录名称")
	private String loginName;

	@ApiModelProperty(value = "登录密码")
	private String passWord;

	@ApiModelProperty(value = "短信模板内容")
	private String messageContent;


	@ApiModelProperty(value = "发送短信类型 1登录 2发送订单")
	private Integer msgType;
}
