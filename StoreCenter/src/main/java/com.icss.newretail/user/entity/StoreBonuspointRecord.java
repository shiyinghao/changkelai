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
 * 门店积分记录表
 * </p>
 *
 * @author jc
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_bonuspoint_record")
@ApiModel(value="StoreBonuspointRecord对象", description="门店积分记录表")
public class StoreBonuspointRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "门店组织机构编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "关联订单id")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty(value = "来源类型(1分享2用户消费)")
    @TableField("source_type")
    private Integer sourceType;

    @ApiModelProperty(value = "积分值")
    @TableField("bonuspoint")
    private BigDecimal bonuspoint;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;


}
