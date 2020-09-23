package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 平台租户交互记录（华为云平台）
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_tenant_saascall")
@ApiModel(value="TenantSaascall对象", description="平台租户交互记录（华为云平台）")
public class TenantSaascall implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "uuid")
    @TableId(value = "uuid", type = IdType.AUTO)
    private Integer uuid;

    @ApiModelProperty(value = "请求类型newInstanc/refreshInstance/expireInstance/releaseInstance")
    @TableField("activity")
    private String activity;

    @ApiModelProperty(value = "实例ID")
    @TableField("instance_id")
    private String instanceId;

    @ApiModelProperty(value = "是否调试请求（1是0否）")
    @TableField("test_flag")
    private String testFlag;

    @ApiModelProperty(value = "请求时间")
    @TableField("send_time")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "请求json")
    @TableField("request_data")
    private String requestData;

    @ApiModelProperty(value = "返回结果")
    @TableField("result_code")
    private String resultCode;

    @ApiModelProperty(value = "结果描述")
    @TableField("result_msg")
    private String resultMsg;

    @ApiModelProperty(value = "返回结果json")
    @TableField("response_data")
    private String responseData;

    @ApiModelProperty(value = "订单id")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
