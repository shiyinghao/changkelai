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
public class AllMenuRequest {

    @ApiModelProperty(value = "应用ID")
    private String appId;
    
    @ApiModelProperty(value = "角色id")
    private String roleId;
}
