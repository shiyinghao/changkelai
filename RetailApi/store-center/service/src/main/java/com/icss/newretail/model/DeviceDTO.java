package com.icss.newretail.model;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门店终端设备-UserStoreDevice
 * 
 * @author zhangzhijia
 * @date Apr 20, 2019
 */
@Data
public class DeviceDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备ID")
    private String deviceId;

    @ApiModelProperty(value = "组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "设备类型名称")
    private String deviceTypeName;

    @ApiModelProperty(value = "设备归属门店")
    private String deviceStore;

    @ApiModelProperty(value = "设备编号")
    private String deviceNumber;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备说明")
    private String deviceDesc;

    @ApiModelProperty(value = "设备显示宽度")
    private BigDecimal width;

    @ApiModelProperty(value = "设备显示高度")
    private BigDecimal hight;

    @ApiModelProperty(value = "显示方式（L横屏/P竖屏）")
    private String displayMode;

    @ApiModelProperty(value = "分屏显示（Y是/N否）")
    private String multiDisplay;

    @ApiModelProperty(value = "分屏数量")
    private Integer multiDisplayNum;

    @ApiModelProperty(value = "每屏行数")
    private Integer multiDisplayRowNum;

    @ApiModelProperty(value = "每屏列数")
    private Integer multiDisplayColumnNum;

    @ApiModelProperty(value = "显示内容（A图片/G商品）")
    private String contentType;

    @ApiModelProperty(value = "默认显示内容")
    private String defaultContent;

    @ApiModelProperty(value = "备注")
    private String comments;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
