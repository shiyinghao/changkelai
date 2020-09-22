package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class ShopUserInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户中文名字")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "电话")
    private String tel;


    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "用户类型(收银员/店长)")
    private String userType;

    @ApiModelProperty(value = "签到状态(是否在线)")
    private Integer signinStatus;


    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "员工编号")
    private String employeeNo;

    @ApiModelProperty(value = "身份证")
    private String identity;

}
