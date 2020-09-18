package com.icss.newretail.user.entity;

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
 * 系统参数项表
 * </p>
 *
 * @author jc
 * @since 2020-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_system_param_value")
@ApiModel(value="UserSystemParamValue对象", description="系统参数项表")
public class UserSystemParamValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "参数id")
    @TableField("param_id")
    private String paramId;

    @ApiModelProperty(value = "参数项编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "参数项名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
