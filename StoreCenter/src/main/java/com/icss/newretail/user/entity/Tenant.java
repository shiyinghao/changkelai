package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.icss.newretail.model.TenantDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 平台租户
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_tenant")
@ApiModel(value = "Tenant对象", description = "平台租户")
public class Tenant implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "租户ID")
  @TableId("tenant_id")
  private String tenantId;

  @ApiModelProperty(value = "租户编码")
  @TableField("tenant_code")
  private String tenantCode;

  @ApiModelProperty(value = "租户名称")
  @TableField("tenant_name")
  private String tenantName;

  @ApiModelProperty(value = "公司版权名称")
  @TableField("corp_name")
  private String corpName;

  @ApiModelProperty(value = "公司版权域名")
  @TableField("corp_website")
  private String corpWebsite;

  @ApiModelProperty(value = "公司简称")
  @TableField("corp_short_name")
  private String corpShortName;

  @ApiModelProperty(value = "公司网站title")
  @TableField("corp_title")
  private String corpTitle;

  @ApiModelProperty(value = "公司网站title图标")
  @TableField("corp_ico")
  private String corpIco;

  @ApiModelProperty(value = "登录页logo")
  @TableField("corp_logo")
  private String corpLogo;

  @ApiModelProperty(value = "登录后logo")
  @TableField("corp_logo_in")
  private String corpLogoIn;

  @ApiModelProperty(value = "登录页图片")
  @TableField("corp_face_img")
  private String corpFaceImg;

  @ApiModelProperty(value = "登录页欢迎语图片")
  @TableField("corp_welcome_img")
  private String corpWelcomeImg;

  @ApiModelProperty(value = "有效期限")
  @TableField("expire_time")
  private LocalDateTime expireTime;

  @ApiModelProperty(value = "状态1正常/2冻结/3释放")
  @TableField("status")
  private Integer status;

  @ApiModelProperty(value = "备注")
  @TableField("remark")
  private String remark;

  @ApiModelProperty(value = "实例ID")
  @TableField("instance_id")
  private String instanceId;

  @ApiModelProperty(value = "前台地址")
  @TableField("front_end_url")
  private String frontEndUrl;

  @ApiModelProperty(value = "管理地址")
  @TableField("admin_url")
  private String adminUrl;

  @ApiModelProperty(value = "网站IP")
  @TableField("ip")
  private String ip;

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

  public Tenant() {
    
  }

  public Tenant(TenantDTO para) {
    this.tenantId = para.getTenantId();
    this.tenantCode = para.getTenantCode();
    this.tenantName = para.getTenantName();
    this.corpName = para.getCorpName();
    this.corpWebsite = para.getCorpWebsite();
    this.corpShortName = para.getCorpShortName();
    this.corpTitle = para.getCorpTitle();
    this.corpIco = para.getCorpIco();
    this.corpLogo = para.getCorpLogo();
    this.corpLogoIn = para.getCorpLogoIn();
    this.corpFaceImg = para.getCorpFaceImg();
    this.corpWelcomeImg = para.getCorpWelcomeImg();
    this.status = para.getStatus() == null ? 1 : para.getStatus();
    this.remark = para.getRemark();
    this.updateTime = LocalDateTime.now();
    this.instanceId = para.getInstanceId();
    this.frontEndUrl = para.getFrontEndUrl();
    this.adminUrl = para.getAdminUrl();
    this.ip = para.getIp();
  }
}
