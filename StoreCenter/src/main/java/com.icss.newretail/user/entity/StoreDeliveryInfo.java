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
 * 门店配送信息表
 * </p>
 *
 * @author Mc_Jc
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_delivery_info")
@ApiModel(value="StoreDeliveryInfo对象", description="门店配送信息表")
public class StoreDeliveryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "门店组织机构")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "最远启配距离")
    @TableField("distance")
    private BigDecimal distance;

    @ApiModelProperty(value = "免费配送距离")
    @TableField("free_distance")
    private BigDecimal freeDistance;

    @ApiModelProperty(value = "配送服务时间(开始)")
    @TableField("start_time")
    private String startTime;

    @ApiModelProperty(value = "配送服务时间(结束)")
    @TableField("delivery_time")
    private String deliveryTime;

    @ApiModelProperty(value = "配送计费方式(1:固定值  2:按公里计费 )")
    @TableField("delivery_type")
    private Integer deliveryType;

    @ApiModelProperty(value = "配送费")
    @TableField("delivery_money")
    private BigDecimal deliveryMoney;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private String updateUser;


}
