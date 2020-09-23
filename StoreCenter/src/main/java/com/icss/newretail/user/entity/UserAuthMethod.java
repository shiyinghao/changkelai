package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 认证方式
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_auth_method")
@ApiModel(value="UserAuthMethod对象", description="认证方式")
public class UserAuthMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "用户名/手机号/邮箱")
    @TableField("auth_method")
    private Integer authMethod;

    @ApiModelProperty(value = "登录用的用户名")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "登陆UUID")
    @TableId("uuid")
    private String uuid;

}
