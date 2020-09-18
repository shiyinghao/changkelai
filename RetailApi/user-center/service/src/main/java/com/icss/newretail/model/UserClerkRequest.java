package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户信息-UserInfo
 * 
 * @author zhangzhijia
 * @date Apr 15, 2019
 */
@Data
public class UserClerkRequest {

    @ApiModelProperty(value = "用户中文名字")
    private String userName;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "身份证")
    private String identity;
    
    @ApiModelProperty(value = "状态(启用/停用)")
    private Integer status;
    
    @ApiModelProperty(value = "用户类型(1集团人员2战区人员3店主4店长5店员)")
    private String userType;
    
}
