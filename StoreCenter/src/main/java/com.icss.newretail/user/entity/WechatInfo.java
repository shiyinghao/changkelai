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
 * 店铺公众号信息
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_wechat_info")
@ApiModel(value="UserWechatInfo对象", description="店铺公众号信息")
public class WechatInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信公众号UUID")
    @TableId("wechat_uuid")
    private String wechatUuid;

    @ApiModelProperty(value = "组织编码")
    @TableField("org_seq")
    private String orgSeq;

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

    @ApiModelProperty(value = "公众号ID")
    @TableField("corp_id")
    private String corpId;

    @ApiModelProperty(value = "开发者凭证")
    @TableField("corp_secret")
    private String corpSecret;

    @ApiModelProperty(value = "公众号原始id")
    @TableField("corp_code")
    private String corpCode;

    @ApiModelProperty(value = "公众号名称")
    @TableField("corp_name")
    private String corpName;

    @ApiModelProperty(value = "公众号类型'")
    @TableField("corp_type")
    private Integer corpType;

    @ApiModelProperty(value = "应用id")
    @TableField("agent_id")
    private String agentId;

    @ApiModelProperty(value = "应用名称")
    @TableField("agent_name")
    private String agentName;

    @ApiModelProperty(value = "账号")
    @TableField("user_code")
    private String userCode;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "二维码")
    @TableField("qr_code")
    private String qrCode;

    @ApiModelProperty(value = "关键字")
    @TableField("corp_key")
    private String corpKey;

    @ApiModelProperty(value = "公众号图片")
    @TableField("wechat_pic")
    private String wechatPic;

    @ApiModelProperty(value = "状态(0禁用/1启用)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "备注")
    @TableField("comments")
    private String comments;
    
    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;



}
