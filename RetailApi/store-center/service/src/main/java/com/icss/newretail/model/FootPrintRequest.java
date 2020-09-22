package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FootPrintRequest implements Serializable{

    private static final long serialVersionUID = 1658776L;

    @ApiModelProperty(value = "页数")
    private Integer current;

    @ApiModelProperty(value = "每页条数")
    private Integer size;

    @ApiModelProperty(value = "访问消费者ID")
    private String memberId;

    @ApiModelProperty(value = "消费者经度")
    private BigDecimal lng;

    @ApiModelProperty(value = "消费者纬度")
    private BigDecimal lat;

}
