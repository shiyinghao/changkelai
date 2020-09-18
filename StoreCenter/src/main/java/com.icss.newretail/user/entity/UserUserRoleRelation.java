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
 * 用户角色关系
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_user_role_relation")
@ApiModel(value="UserUserRoleRelation对象", description="用户角色关系")
public class UserUserRoleRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户角色关系编码")
    @TableId("user_role_relation_id")
    private String userRoleRelationId;

    @ApiModelProperty(value = "角色编码")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

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
