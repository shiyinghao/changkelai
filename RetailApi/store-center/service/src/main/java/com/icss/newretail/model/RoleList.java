package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleList {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String roleId; // 用户ID

    @ApiModelProperty(value = "用户ID")
    private String roleName; // 用户ID

    private Integer roleSort;

}
