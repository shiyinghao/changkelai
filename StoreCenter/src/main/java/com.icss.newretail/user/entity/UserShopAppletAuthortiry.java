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
 * 商家端小程序权限表
 * </p>
 *
 * @author mx
 * @since 2020-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_shop_applet_authority")
@ApiModel(value="UserShopAppletAuthortiry对象", description="商家端小程序权限表")
public class UserShopAppletAuthortiry implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "权限名称")
    @TableField("authority_name")
    private String authorityName;

    @ApiModelProperty(value = "权限路径")
    @TableField("authority_uri")
    private String authorityUri;

    @ApiModelProperty(value = "是否展示")
    @TableField("is_show")
    private Integer isShow;

    @ApiModelProperty(value = "权限描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "状态(1-启用0-禁用)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "图标")
    @TableField("icon")
    private String icon;


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
