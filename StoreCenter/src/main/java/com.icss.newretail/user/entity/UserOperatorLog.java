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
 * 操作日志（菜单访问等）
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_operator_log")
@ApiModel(value="UserOperatorLog对象", description="操作日志（菜单访问等）")
public class UserOperatorLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "操作ID")
    @TableId("operation_id")
    private String operationId;

    @ApiModelProperty(value = "菜单ID")
    @TableField("resoresource_id")
    private String resoresourceId;

    @ApiModelProperty(value = "操作类型")
    @TableField("operation_type")
    private String operationType;

    @ApiModelProperty(value = "终端ID")
    @TableField("dev_number")
    private String devNumber;

    @ApiModelProperty(value = "操作人")
    @TableField("operator")
    private String operator;

    @ApiModelProperty(value = "操作时间")
    @TableField("operation_time")
    private LocalDateTime operationTime;

    @ApiModelProperty(value = "操作说明")
    @TableField("operation_desc")
    private String operationDesc;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

    public UserOperatorLog(){}
    public UserOperatorLog(UserOperatorType userOperatorType, String userId, String menuId, String tenantId) {
        this.resoresourceId = menuId;
        this.operationType = userOperatorType.getOperationType();
        this.operator = userId;
        this.operationTime = LocalDateTime.now();
        this.operationDesc = userOperatorType.getOperationTypeDesc();
        this.tenantId = tenantId;
    }
}
