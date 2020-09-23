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
 * 门店参数
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_param")
@ApiModel(value="UserStoreParam对象", description="门店参数")
public class UserStoreParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数类型")
    @TableField("param_type")
    private String paramType;

    @ApiModelProperty(value = "参数取值")
    @TableField("param_value")
    private String paramValue;

    @ApiModelProperty(value = "组织编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "参数类型取值ID")
    @TableField("param_type_value_id")
    private String paramTypeValueId;

    @ApiModelProperty(value = "门店参数ID")
    @TableId("param_id")
    private String paramId;

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
