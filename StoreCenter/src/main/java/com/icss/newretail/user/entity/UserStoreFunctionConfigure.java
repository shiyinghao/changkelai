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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 终端功能配置
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_function_configure")
@ApiModel(value="UserStoreFunctionConfigure对象", description="终端功能配置")
public class UserStoreFunctionConfigure implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID")
    @TableId("menu_id")
    private String menuId;

    @ApiModelProperty(value = "组织编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "设备ID")
    @TableField("device_id")
    private String deviceId;

    @ApiModelProperty(value = "菜单名称")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty(value = "菜单序号")
    @TableField("menu_seq")
    private BigDecimal menuSeq;

    @ApiModelProperty(value = "菜单图片")
    @TableField("menu_pic")
    private String menuPic;

    @ApiModelProperty(value = "菜单级别（主菜单/1级/2级）")
    @TableField("menu_level")
    private Integer menuLevel;

    @ApiModelProperty(value = "菜单类型（APP/URL/ACTIVITY/Menu/路由）")
    @TableField("menu_type")
    private String menuType;

    @ApiModelProperty(value = "下载地址")
    @TableField("download_url")
    private String downloadUrl;

    @ApiModelProperty(value = "包名")
    @TableField("pkgname")
    private String pkgname;

    @ApiModelProperty(value = "菜单资源")
    @TableField("menu_resource")
    private String menuResource;

    @ApiModelProperty(value = "是否是根节点")
    @TableField("is_root_node")
    private Integer isRootNode;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

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


}
