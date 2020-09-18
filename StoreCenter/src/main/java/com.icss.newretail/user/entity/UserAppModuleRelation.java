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
 * 应用模块关系
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_app_module_relation")
@ApiModel(value="UserAppModuleRelation对象", description="应用模块关系")
public class UserAppModuleRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模块ID")
    @TableField("module_id")
    private String moduleId;

    @ApiModelProperty(value = "所属应用ID")
    @TableField("app_id")
    private String appId;

    @ApiModelProperty(value = "模块名称")
    @TableField("module_number")
    private BigDecimal moduleNumber;

    @ApiModelProperty(value = "模块应用关系Id")
    @TableId("app_module_relation_id")
    private String appModuleRelationId;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

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
