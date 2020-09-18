package com.icss.newretail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户操作记录
 * 
 * @author zhangzhijia
 * @date May 6, 2019
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "OperationLogDTO对象", description = "操作日志（菜单访问等）")
public class OperationLogDTO {

    @ApiModelProperty(value = "操作ID")
    private String operationId;

    @ApiModelProperty(value = "菜单ID")
    private String resoresourceId;

    @ApiModelProperty(value = "操作类型")
    private String operationType;

    @ApiModelProperty(value = "终端ID")
    private String devNumber;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operationTime;

    @ApiModelProperty(value = "操作说明")
    private String operationDesc;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;
}
