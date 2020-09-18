package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class ConsumerInfoRequest implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消费者经度")
    private String lng;

    @ApiModelProperty(value = "消费者纬度")
    private String lat;

//    @ApiModelProperty(value = "范围（米）")
//    private int circle;// 查询半径

    @ApiModelProperty(value = "访问消费者ID")
    private String consumerId;
}
