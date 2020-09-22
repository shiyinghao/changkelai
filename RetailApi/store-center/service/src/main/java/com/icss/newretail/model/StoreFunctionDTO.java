/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 终端资源配置信息-UserStoreFunctionConfigure
 * 
 * @author zhangzhijia
 * @date Apr 9, 2019
 */
@Data
public class StoreFunctionDTO {

    @ApiModelProperty(value = "组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "设备ID")
    private String deviceId;

    @ApiModelProperty(value = "菜单ID")
    private String menuId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单序号")
    private BigDecimal menuSeq;

    @ApiModelProperty(value = "菜单图片")
    private String menuPic;

    @ApiModelProperty(value = "菜单级别（主菜单/1级/2级）")
    private Integer menuLevel;

    @ApiModelProperty(value = "菜单类型（APP/URL/ACTIVITY/Menu/路由）")
    private String menuType;

    @ApiModelProperty(value = "下载地址")
    private String downloadUrl;

    @ApiModelProperty(value = "包名")
    private String pkgname;

    @ApiModelProperty(value = "菜单资源")
    private String menuResource;

    @ApiModelProperty(value = "是否是根节点")
    private Integer isRootNode;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
