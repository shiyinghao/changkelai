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
 * 门店等级规则
 * </p>
 *
 * @author zhangzhijia
 * @since 2020-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_gradelevel_rule")
@ApiModel(value="StoreGradelevelRule对象", description="门店等级规则")
public class StoreGradelevelRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "等级名称")
    @TableField("gradelevel_name")
    private String gradelevelName;


    @ApiModelProperty(value = "等级序号")
    @TableField("level_number")
    private Integer levelNumber;

    @ApiModelProperty(value = "等级开始分值")
    @TableField("begin_score")
    private BigDecimal beginScore;

    @ApiModelProperty(value = "等级结束分值")
    @TableField("end_score")
    private BigDecimal endScore;

    @ApiModelProperty(value = "图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "状态(1-启用0-停用)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "开始时间")
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