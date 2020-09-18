/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 微信人员认证关系查询参数
 * 
 * @author zhangzhijia
 * @date May 8, 2019
 */
public class UserAuthRequest {
    
    @ApiModelProperty(value = "公众号id")
    private String corpId;

    @ApiModelProperty(value = "人员编码")
    private String userId;

    @ApiModelProperty(value = "认证类型(10002零售户10005客服10006配送)")
    private String authType;

    @ApiModelProperty(value = "认证账号")
    private String authAccount;

}
