package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jc
 * @since 2020-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_ums_template")
@ApiModel(value="UserUmsTemplate对象", description="")
public class UserUmsTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "集团编码")
    @TableField("spCode")
    private String spCode;

    @ApiModelProperty(value = "登录名称")
    @TableField("loginName")
    private String loginName;

    @ApiModelProperty(value = "登录密码")
    @TableField("passWord")
    private String passWord;

    @ApiModelProperty(value = "短信模板内容")
    @TableField("messageContent")
    private String messageContent;

    @ApiModelProperty(value = "短信模板id")
    @TableField("umsCode")
    private String umsCode;


}
