package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 租户信息
 * 
 * @author zhangzhijia
 * @date Jun 12, 2019
 */
@Data
public class TenantDTO {
  
	@ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "租户编码")
    private String tenantCode;

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "公司版权名称")
    private String corpName;

    @ApiModelProperty(value = "公司版权域名")
    private String corpWebsite;

    @ApiModelProperty(value = "公司简称")
    private String corpShortName;

    @ApiModelProperty(value = "公司网站title")
    private String corpTitle;

    @ApiModelProperty(value = "公司网站title图标")
    private String corpIco;

    @ApiModelProperty(value = "登录页logo")
    private String corpLogo;

    @ApiModelProperty(value = "登录后logo")
    private String corpLogoIn;

    @ApiModelProperty(value = "登录页图片")
    private String corpFaceImg;

    @ApiModelProperty(value = "登录页欢迎语图片")
    private String corpWelcomeImg;

    @ApiModelProperty(value = "状态1启用，0停用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "有效期限")
    private String expireTime;

    @ApiModelProperty(value = "前台地址")
    private String frontEndUrl;

    @ApiModelProperty(value = "管理地址")
    private String adminUrl;

    @ApiModelProperty(value = "网站IP")
    private String ip;

    @ApiModelProperty(value = "实例ID")
    private String instanceId;

    @ApiModelProperty(value = "平台租户标题")
    private List<TenantTitleDTO> tenantTitles;
    
}
