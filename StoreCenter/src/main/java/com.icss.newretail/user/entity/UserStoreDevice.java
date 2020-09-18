package com.icss.newretail.user.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 门店设备
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_device")
@ApiModel(value="StoreDevice对象", description="门店设备")
public class UserStoreDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备ID")
    @TableId("device_id")
    private String deviceId;

    @ApiModelProperty(value = "组织编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "设备归属门店")
    @TableField("device_store")
    private String deviceStore;

    @ApiModelProperty(value = "设备编号")
    @TableField("device_number")
    private String deviceNumber;

    @ApiModelProperty(value = "设备类型")
    @TableField("device_type")
    private String deviceType;

    @ApiModelProperty(value = "设备名称")
    @TableField("device_name")
    private String deviceName;

    @ApiModelProperty(value = "设备说明")
    @TableField("device_desc")
    private String deviceDesc;

    @ApiModelProperty(value = "设备显示宽度")
    @TableField("width")
    private BigDecimal width;

    @ApiModelProperty(value = "设备显示高度")
    @TableField("hight")
    private BigDecimal hight;

    @ApiModelProperty(value = "显示方式（L横屏/P竖屏）")
    @TableField("display_mode")
    private String displayMode;

    @ApiModelProperty(value = "分屏显示（Y是/N否）")
    @TableField("multi_display")
    private String multiDisplay;

    @ApiModelProperty(value = "分屏数量")
    @TableField("multi_display_num")
    private Integer multiDisplayNum;

    @ApiModelProperty(value = "每屏行数")
    @TableField("multi_display_row_num")
    private Integer multiDisplayRowNum;

    @ApiModelProperty(value = "每屏列数")
    @TableField("multi_display_column_num")
    private Integer multiDisplayColumnNum;

    @ApiModelProperty(value = "显示内容（A图片/G商品）")
    @TableField("content_type")
    private String contentType;

    @ApiModelProperty(value = "默认显示内容")
    @TableField("default_content")
    private String defaultContent;

    @ApiModelProperty(value = "备注")
    @TableField("comments")
    private String comments;

    @ApiModelProperty(value = "状态")
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


}
