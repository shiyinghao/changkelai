package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户id串-UsersParameterDTO
 */
@Data
public class UsersParameterDTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "会员编码串")
	private String users;
	
	@ApiModelProperty(value = "组织编码")
	private String orgSeq;
	
	@ApiModelProperty("店员名称")
    public String userName;

}
