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
 * 组织机构参数配置
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_org_param_type_value")
@ApiModel(value="UserOrgParamTypeValue对象", description="组织机构参数配置")
public class UserOrgParamTypeValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数类型取值ID")
    @TableId("param_type_value_id")
    private String paramTypeValueId;

    @ApiModelProperty(value = "参数类型")
    @TableField("param_type")
    private String paramType;

    @ApiModelProperty(value = "参数取值")
    @TableField("param_value")
    private String paramValue;

    @ApiModelProperty(value = "参数取值描述")
    @TableField("param_value_desc")
    private String paramValueDesc;

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

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;


}
