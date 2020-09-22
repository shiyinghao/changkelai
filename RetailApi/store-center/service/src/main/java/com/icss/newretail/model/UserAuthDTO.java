/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信人员认证关系
 * 
 */
@Data
public class UserAuthDTO {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "1用户名/2手机号/3邮箱")
    private String authMethod;

    @ApiModelProperty(value = "登录用的用户名")
    private String account;

    @ApiModelProperty(value = "个人OpenID:微信唯一标识符")
    private String openId;

}
