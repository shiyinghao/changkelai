package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetTokenDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请求方法")
    private String action;

    @ApiModelProperty(value = "KEY")
    private String key;

}