package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class OrgList implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织id")
    private String orgSeq;

    @ApiModelProperty(value = "组织name")
    private String orgName;

    @ApiModelProperty(value = "组织name")
    private Integer userType;

    @ApiModelProperty(value = "组织类型")
    private Integer orgType;

    @ApiModelProperty(value = "门店照片")
    private String doorwayPic;

    private String storeAddress;

    private String storeManagerPhone;

    private Integer openStatus;

}
