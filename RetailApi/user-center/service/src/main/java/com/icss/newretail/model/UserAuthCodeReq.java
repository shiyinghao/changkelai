package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserAuthCodeReq implements Serializable{

    private static final long serialVersionUID = 128743089L;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendTime;
}
