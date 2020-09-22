package com.icss.newretail.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthorityParamsDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String orgSeq;
	private String userId;
	private String realName;
	private String nickName;
	private String tel; 
	@ApiModelProperty(value = "用户类型(1集团人员2战区人员3基地人员4店主5店长6店员)")
	private String userType;
	
	private String authorityId; //权限id
	private String authorityName; //权限名称
	private String authorityUri;//权限路径
	private String desc;// 权限描述







}
