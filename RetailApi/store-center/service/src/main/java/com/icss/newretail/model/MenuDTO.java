package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统菜单-UserMenu
 * 
 * @author zhangzhijia
 * @date Apr 20, 2019
 */
@Data
public class MenuDTO {

    @ApiModelProperty(value = "菜单ID")
    private String menuId;

    @ApiModelProperty(value = "所属应用ID")
    private String appId;

    @ApiModelProperty(value = "模块应用关系Id")
    private String appModuleRelationId;

    @ApiModelProperty(value = "所属应用模块ID")
    private String moduleId;

    @ApiModelProperty(value = "资源编码(菜单编码，唯一确认某个资源)")
    private String resourceReq;

    @ApiModelProperty(value = "资源URL")
    private String resourceUrl;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "资源顺序")
    private BigDecimal resourceNumber;

    @ApiModelProperty(value = "资源图标")
    private String resourceLogo;

    @ApiModelProperty(value = "资源说明")
    private String resourceDesc;

    @ApiModelProperty(value = "资源级别")
    private Integer resourceLevel;

    @ApiModelProperty(value = "上级资源编码")
    private String upResourceReq;

    @ApiModelProperty(value = "是否默认展示")
    private Integer isDefaultDisplay;

    @ApiModelProperty(value = "是否基础菜单")
    private Integer isBaseMenu;

    @ApiModelProperty(value = "是否首页")
    private Integer isHomePage;

    @ApiModelProperty(value = "是否权限控制")
    private Integer isPrivilegeControl;

    @ApiModelProperty(value = "状态（是否启用）")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "上级菜单ID")
    private String upMenuId;

    @ApiModelProperty(value = "子集菜单")
    private List<MenuDTO> children;

    @ApiModelProperty(value = "按钮")
    private List<MenuButtonDTO> btnChildren;
}
