package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 门店/终端参数-UserStoreParam
 * 
 * @author zhangzhijia
 * @date Apr 20, 2019
 */
@Data
public class StoreParamDTO {
    
    @ApiModelProperty(value = "门店参数ID")
    private String paramId;
    
    @ApiModelProperty(value = "参数类型")
    private String paramType;

    @ApiModelProperty(value = "参数取值")
    private String paramValue;

    @ApiModelProperty(value = "组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "参数类型取值ID")
    private String paramTypeValueId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;
    
    @ApiModelProperty(value = "租户ID")
    private String tenantId;

}
