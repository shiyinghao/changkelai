package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StoreBonuspointRuleDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private String uuid;

    @ApiModelProperty(value = "消费积分比例")
    private BigDecimal consumeRate;

    @ApiModelProperty(value = "绑定门店返分比例")
    private BigDecimal backBindRate;

    @ApiModelProperty(value = "非绑定门店积分比例")
    private BigDecimal backNobindRate;

    @ApiModelProperty(value = "分享得分")
    private BigDecimal shareScore;
}
