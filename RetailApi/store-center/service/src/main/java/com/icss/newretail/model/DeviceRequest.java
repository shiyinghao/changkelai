/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 终端设备查询参数
 * 
 * @author zhangzhijia
 * @date Apr 20, 2019
 */
@Data
public class DeviceRequest {
    
    @ApiModelProperty(value = "组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "设备ID")
    private String deviceId;

    @ApiModelProperty(value = "设备归属门店")
    private String deviceStore;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "门店名称")
    private String storeName;
}
