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
 * 公众号模版信息
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_wechat_template")
@ApiModel(value="UserWechatTemplate对象", description="公众号模版信息")
public class WechatTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公众号模版uuid")
    @TableId("template_uuid")
    private String templateUuid;

    @ApiModelProperty(value = "微信公众号")
    @TableField("corp_id")
    private String corpId;

    @ApiModelProperty(value = "模板类型")
    @TableField("template_type")
    private String templateType;

    @ApiModelProperty(value = "模版ID")
    @TableField("template_id")
    private String templateId;

    @ApiModelProperty(value = "状态(0禁用/1启用)")
    @TableField("status")
    private Integer status;

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


}
