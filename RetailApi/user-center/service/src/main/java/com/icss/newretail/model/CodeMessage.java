/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编码字典查询参数
 * 
 */

@Data
public class CodeMessage {

    @ApiModelProperty(value = "编码表的uuid")
    private String uuid;

    @ApiModelProperty(value = "编码子表对应的id")
    private String dictId;

    @ApiModelProperty(value = "字典编码")
    private String code;

    @ApiModelProperty(value = "字典名称")
    private String name;

    @ApiModelProperty(value = "字典项值")
    private String value;

}
