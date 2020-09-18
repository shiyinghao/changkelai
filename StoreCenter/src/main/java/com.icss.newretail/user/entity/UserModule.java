package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.icss.newretail.model.ModuleDTO;
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
 * 模块
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_module")
@ApiModel(value="UserModule对象", description="模块")
public class UserModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用ID")
    @TableId("module_id")
    private String moduleId;

    @ApiModelProperty(value = "应用名称")
    @TableField("module_name")
    private String moduleName;

    @ApiModelProperty(value = "应用顺序")
    @TableField("module_number")
    private BigDecimal moduleNumber;

    @ApiModelProperty(value = "应用说明")
    @TableField("module_desc")
    private String moduleDesc;

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

    public UserModule(ModuleDTO module) {
        this.moduleId = module.getModuleId();
        this.moduleName = module.getModuleName();
        this.moduleNumber = module.getModuleNumber();
        this.moduleDesc = module.getModuleDesc();
        this.createTime = module.getCreateTime();
        this.createUser = module.getCreateUser();
        this.updateTime = module.getUpdateTime();
        this.updateUser = module.getUpdateUser();
    }
}
