package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.icss.newretail.model.TipsDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 微信应用使用条款
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_wechat_tips")
@ApiModel(value="WechatTips对象", description="微信应用使用条款")
public class WechatTips implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "uuid")
    @TableId(value = "uuid", type = IdType.AUTO)
    private Integer uuid;

    @ApiModelProperty(value = "应用id")
    @TableField("app_id")
    private String appId;

    @ApiModelProperty(value = "条款内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "条款版本")
    @TableField("version")
    private String version;

    @ApiModelProperty(value = "状态(0禁用/1启用)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

    public WechatTips(){}

    public WechatTips(TipsDTO tips){
        this.appId = tips.getAppId();
        this.content = tips.getContent();
        this.status = tips.getStatus();
        this.version = tips.getVersion();
    }

}
