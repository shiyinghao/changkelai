/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织机构查询参数
 * 
 * @author zhangzhijia
 * @date Jun 20, 2019
 */
@Data
public class OrganizationRequest {
    @ApiModelProperty("组织名称")
    private String orgName;

    @ApiModelProperty(value = "组织类型(总店/分店/门店)")
    private String orgType;
}
