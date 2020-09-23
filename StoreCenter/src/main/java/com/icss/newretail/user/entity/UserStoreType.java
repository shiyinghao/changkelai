package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.icss.newretail.model.StoreTypeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 门店类型
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_type")
@ApiModel(value="UserStoreType对象", description="门店类型")
public class UserStoreType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门店类型")
    @TableId("store_type")
    private String storeType;

    @ApiModelProperty(value = "门店类型名称")
    @TableField("store_type_name")
    private String storeTypeName;

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

    public UserStoreType(){}
    public UserStoreType(StoreTypeDTO storeType,String tenantId) {
        this.storeType = storeType.getStoreType();
        this.storeTypeName = storeType.getStoreTypeName();
        this.tenantId = tenantId;
        this.updateTime = storeType.getUpdateTime();
    }
}
