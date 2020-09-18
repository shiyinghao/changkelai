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
import java.time.LocalDateTime;

/**
 * <p>
 * 微信人员认证关系表
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_wechat_user_auth")
@ApiModel(value="UserWechatUserAuth对象", description="微信人员认证关系表")
public class WechatUserAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private String updateUser;

    @ApiModelProperty(value = "认证关系UUID")
    @TableId("auth_uuid")
    private String authUuid;

    @ApiModelProperty(value = "公众号id")
    @TableField("corp_id")
    private String corpId;

    @ApiModelProperty(value = "人员编码")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty(value = "认证类型(10001门店10002客服10003配送)")
    @TableField("auth_type")
    private String authType;

    @ApiModelProperty(value = "认证账号")
    @TableField("auth_account")
    private String authAccount;

    @ApiModelProperty(value = "状态(0禁用1启用)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "备注")
    @TableField("comments")
    private String comments;


}
