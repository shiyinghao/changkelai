package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GrantUserDTO {
    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "折扣(0-10)")
    private String discount;

    @ApiModelProperty(value = "员工编号")
    private String employeeNo;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "密码")
    private String password;
}

