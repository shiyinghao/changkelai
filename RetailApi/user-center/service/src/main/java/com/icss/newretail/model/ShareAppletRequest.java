/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ShareAppletRequest {

    @ApiModelProperty(value = "页面路径")
    private String page;
    
    @ApiModelProperty(value = "参数")
    private String sceneStr;
    
    @ApiModelProperty(value = "唯一标识")
    private String appid;
   
    @ApiModelProperty(value = "密钥")
    private String secret;
    
    @ApiModelProperty(value = "密钥")
    private Integer width = 300;
}
