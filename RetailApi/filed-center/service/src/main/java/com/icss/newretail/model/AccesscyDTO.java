package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AccesscyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请求方法")
    private String action;

    @ApiModelProperty(value = "入参JSON串")
    private String json;

}