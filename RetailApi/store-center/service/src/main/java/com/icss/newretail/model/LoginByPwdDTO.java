package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class LoginByPwdDTO {
    @ApiModelProperty(value = "小程序ID")
    private String corpId;

    @ApiModelProperty(value = "openId")
    private String openId;

    @ApiModelProperty(value = "unionId")
    private String unionId;

    @ApiModelProperty(value = "授权令牌")
    private String token;// token信息

    @ApiModelProperty(value = "授权令牌有效期限")
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tokenExpTime;// token信息

    @ApiModelProperty(value = "登陆时间")
    private Date currLoginTime; // 当前登陆时间

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "门店ID")
    private String orgSeq;
}
