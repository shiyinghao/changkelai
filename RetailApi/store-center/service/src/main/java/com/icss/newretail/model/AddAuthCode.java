package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddAuthCode implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "店铺申请码")
    private String authCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;
}
