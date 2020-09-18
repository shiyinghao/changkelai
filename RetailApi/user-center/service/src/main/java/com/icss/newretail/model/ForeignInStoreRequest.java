/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ydt
 */
@Data
public class ForeignInStoreRequest {
    
    @ApiModelProperty(value = "门店id集合")
    private String[] ids;

    @ApiModelProperty(value = "门店等级集合")
    private String[] levels;

}
