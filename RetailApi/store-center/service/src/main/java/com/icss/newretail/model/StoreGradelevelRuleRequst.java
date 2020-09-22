package com.icss.newretail.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StoreGradelevelRuleRequst {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private String uuid;

    @ApiModelProperty(value = "等级名称")
    private String gradelevelName;

    @ApiModelProperty(value = "等级序号")
    private Integer levelNumber;

    @ApiModelProperty(value = "等级开始分值")
    private BigDecimal beginScore;

    @ApiModelProperty(value = "等级结束分值")
    private BigDecimal endScore;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "状态(1-启用0-停用)")
    private Integer status;

}
