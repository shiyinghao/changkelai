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
 * 战区信息表
 * </p>
 *
 * @author jc
 * @since 2020-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_war_zone_info")
@ApiModel(value="UserWarZoneInfo对象", description="战区信息表")
public class UserWarZoneInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织编码")
    @TableId("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "战区名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "战区地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

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
