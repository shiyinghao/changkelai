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

    @ApiModelProperty(value = "大小")
    private Integer width = 300;

    @ApiModelProperty(value = "人员id")
    private String userId;

    @ApiModelProperty(value = "门店编码")
    private String orgSeq;

    @ApiModelProperty(value = "分享码类型 1-店铺小程序分享码  2-商品分享码 3-活动分享")
    private Integer type;

    @ApiModelProperty(value = "电话号码")
    private String phone;

}
