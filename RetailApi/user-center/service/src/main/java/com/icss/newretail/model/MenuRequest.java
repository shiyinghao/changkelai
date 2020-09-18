/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangzhijia
 * @date May 17, 2019
 */
@Data
public class MenuRequest {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "应用ID")
    private String appId;
    
    @ApiModelProperty(value = "模块ID")
    private String moduleId;

    @ApiModelProperty(value = "上级菜单ID")
    private String upResourceReq;
    
    @ApiModelProperty(value = "一级或二级菜单ID或模块ID")
    private String id;

    @ApiModelProperty(value = "菜单级别")
    private String resourceLevel;

    @ApiModelProperty(value = "租户ID", hidden = true)
    private String tenantId;

    @ApiModelProperty(value = "菜单状态")
    private Integer status;

    @ApiModelProperty(value = "操作类型")
    private String operate;

    @ApiModelProperty(value = "菜单主键")
    private String UUID;
}
