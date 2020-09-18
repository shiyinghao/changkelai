package com.icss.newretail.model;

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
 * 
 * </p>
 *
 * @author jc
 * @since 2020-04-26
 */
@Data
public class UserUmsTemplateDTO implements Serializable {

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
