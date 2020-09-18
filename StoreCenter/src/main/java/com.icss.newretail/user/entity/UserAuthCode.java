package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_authcode")
@ApiModel(value = "UserAuthCode对象", description = "用户手机验证码")
public class UserAuthCode {

    @ApiModelProperty(value = "手机号")
    @TableId("phone")
    private String phone;

    @ApiModelProperty(value = "验证码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "发送时间")
    @TableField("send_time")
    private LocalDateTime sendTime;
}
