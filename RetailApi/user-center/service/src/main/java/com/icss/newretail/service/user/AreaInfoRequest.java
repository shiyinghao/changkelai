/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.service.user;

import io.swagger.annotations.ApiModelProperty;

/**
 * 行政区划查询参数
 * 
 * @author zhangzhijia
 * @date Apr 29, 2019
 */
public class AreaInfoRequest {
    
    
    @ApiModelProperty(value = "上级区域编码")
    private String upAreaSeq;// 
    
    @ApiModelProperty(value = "区域编码")
    private String areaSeq;// 模糊查询

    @ApiModelProperty(value = "区域名称")
    private String areaName;// 模糊查询

}
