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
 * 门店积分规则表
 * </p>
 *
 * @author zhangzhijia
 * @since 2020-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_bonuspoint_rule")
@ApiModel(value="StoreBonuspointRule对象", description="门店积分规则表")
public class StoreBonuspointRule implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键ID")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "消费积分比例")
    @TableField("consume_rate")
    private BigDecimal consumeRate;

    @ApiModelProperty(value = "绑定门店返分比例")
    @TableField("back_bind_rate")
    private BigDecimal backBindRate;

    @ApiModelProperty(value = "非绑定门店积分比例")
    @TableField("back_nobind_rate")
    private BigDecimal backNobindRate;

    @ApiModelProperty(value = "分享得分")
    @TableField("share_score")
    private BigDecimal shareScore;

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
