package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.icss.newretail.model.MenuDTO;
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
 * 菜单
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_menu")
@ApiModel(value = "UserMenu对象", description = "菜单")
public class UserMenu implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "菜单ID")
  @TableId("menu_id")
  private String menuId;

  @ApiModelProperty(value = "所属应用ID")
  @TableField("app_id")
  private String appId;

  @ApiModelProperty(value = "模块应用关系Id")
  @TableField("app_module_relation_id")
  private String appModuleRelationId;

  @ApiModelProperty(value = "所属应用模块ID")
  @TableField("module_id")
  private String moduleId;

  @ApiModelProperty(value = "源编码(菜单编码，唯一确认某个资源)")
  @TableField("resource_req")
  private String resourceReq;

  @ApiModelProperty(value = "资源URL")
  @TableField("resource_url")
  private String resourceUrl;

  @ApiModelProperty(value = "资源名称")
  @TableField("resource_name")
  private String resourceName;

  @ApiModelProperty(value = "资源顺序")
  @TableField("resource_number")
  private BigDecimal resourceNumber;

  @ApiModelProperty(value = "资源图标")
  @TableField("resource_logo")
  private String resourceLogo;

  @ApiModelProperty(value = "资源说明")
  @TableField("resource_desc")
  private String resourceDesc;

  @ApiModelProperty(value = "资源级别")
  @TableField("resource_level")
  private Integer resourceLevel;

  @ApiModelProperty(value = "上级资源编码")
  @TableField("up_resource_req")
  private String upResourceReq;

  @ApiModelProperty(value = "上级菜单ID")
  @TableField("up_menu_id")
  private String upMenuId;

  @ApiModelProperty(value = "是否默认展示")
  @TableField("is_default_display")
  private Integer isDefaultDisplay;

  @ApiModelProperty(value = "是否基础菜单")
  @TableField("is_base_menu")
  private Integer isBaseMenu;

  @ApiModelProperty(value = "是否首页")
  @TableField("is_home_page")
  private Integer isHomePage;

  @ApiModelProperty(value = "是否权限控制")
  @TableField("is_privilege_control")
  private Integer isPrivilegeControl;

  @ApiModelProperty(value = "状态（是否启用）")
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

  public UserMenu() {
  }
  
  public UserMenu(MenuDTO para) {

    this.menuId = para.getMenuId();
    this.appId = para.getAppId();
    this.appModuleRelationId = para.getAppModuleRelationId();
    this.moduleId = para.getModuleId();
    this.resourceReq = para.getResourceReq();
    this.resourceUrl = para.getResourceUrl();
    this.resourceName = para.getResourceName();
    this.resourceNumber = para.getResourceNumber();
    this.resourceLogo = para.getResourceLogo();
    this.resourceDesc = para.getResourceDesc();
    this.resourceLevel = para.getResourceLevel();
    this.upResourceReq = para.getUpResourceReq();
    this.upMenuId = para.getUpMenuId();
    this.isDefaultDisplay = para.getIsDefaultDisplay();
    this.isBaseMenu = para.getIsBaseMenu();
    this.isHomePage = para.getIsHomePage();
    this.isPrivilegeControl = para.getIsPrivilegeControl();
    this.status = para.getStatus();

    this.createUser = para.getCreateUser();
    this.createTime = para.getCreateTime();
    this.updateUser = para.getUpdateUser();
    this.updateTime = para.getUpdateTime();

  }
  
}
