package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleRequest {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色类型")
    private String roleType;
}
