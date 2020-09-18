/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户配置参数信息-UserCustomizedParam
 * 
 * @author zhangzhijia
 * @date Apr 9, 2019
 */
@Data
public class UserCustomizedParamDTO {
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "参数类型")
    private String paramType;

    @ApiModelProperty(value = "参数ID")
    private String paramId;

    @ApiModelProperty(value = "参数类型取值ID")
    private String paramTypeValueId;

    @ApiModelProperty(value = "参数值")
    private String paramValue;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

}
