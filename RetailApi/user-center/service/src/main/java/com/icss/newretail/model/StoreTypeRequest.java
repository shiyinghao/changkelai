/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangzhijia
 * @date Jun 20, 2019
 */
@Data
public class StoreTypeRequest {

    @ApiModelProperty(value = "门店类型名称")
    private String storeTypeName;
}
