package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthorityList {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限表主键")
    private String uuid;

    @ApiModelProperty(value = "权限名称")
    private String authorityName;

    @ApiModelProperty(value = "权限路径")
    private String authorityUri;

}
