package com.icss.newretail.model;

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
@ApiModel(value="AppModuleRelation对象", description="应用模块关系")
public class AppModuleRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模块ID")
    private String moduleId;

    @ApiModelProperty(value = "所属应用ID")
    private String appId;

    @ApiModelProperty(value = "模块名称")
    private BigDecimal moduleNumber;

    @ApiModelProperty(value = "模块应用关系Id")
    private String appModuleRelationId;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;


}
