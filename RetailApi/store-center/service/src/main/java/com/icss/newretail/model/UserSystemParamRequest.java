/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 系统配置查询参数
 *
 * @author wy
 * @date Apr 17, 2020
 */

@Data
public class UserSystemParamRequest {

    @ApiModelProperty(value = "参数名称")
    private String name;

    @ApiModelProperty(value = "参数编码")
    private String code;

}
