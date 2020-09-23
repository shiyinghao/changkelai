package com.icss.newretail.user.entity;

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
 * 平台租户订单信息（华为云平台）
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_tenant_saasorder")
@ApiModel(value="TenantSaasorder对象", description="平台租户订单信息（华为云平台）")
public class TenantSaasorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    @TableId("order_id")
    private String orderId;

    @ApiModelProperty(value = "产品规格")
    @TableField("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "产品ID")
    @TableField("product_id")
    private String productId;

    @ApiModelProperty(value = "是否开通试用实例（1是0否）")
    @TableField("trial_flag")
    private String trialFlag;

    @ApiModelProperty(value = "过期时间")
    @TableField("expire_time")
    private LocalDateTime expireTime;

    @ApiModelProperty(value = "计费模式：3按次购买")
    @TableField("charging_mode")
    private Integer chargingMode;

    @ApiModelProperty(value = "扩展参数")
    @TableField("saas_extend_params")
    private String saasExtendParams;

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
