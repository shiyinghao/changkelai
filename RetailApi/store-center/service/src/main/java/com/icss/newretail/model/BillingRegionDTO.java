package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BillingRegionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    @ApiModelProperty(value = "区域")
    private String region;

    @ApiModelProperty(value = "电话号码")
    private String telNo;
}
