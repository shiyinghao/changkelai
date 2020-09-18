package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class SearchStoreRequest implements Serializable{

    private static final long serialVersionUID = 12493574670283L;

    @ApiModelProperty(value = "页数")
    private Integer current;

    @ApiModelProperty(value = "每页条数")
    private Integer size;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "消费者经度")
    private BigDecimal lng;

    @ApiModelProperty(value = "消费者纬度")
    private BigDecimal lat;

    @ApiModelProperty(value = "访问消费者ID")
    private String memberId;

}
