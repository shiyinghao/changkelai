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
 * 角色菜单关系
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_role_menu_relation")
@ApiModel(value="UserRoleMenuRelation对象", description="角色菜单关系")
public class UserRoleMenuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty(value = "菜单ID")
    @TableField("menu_id")
    private String menuId;

    @ApiModelProperty(value = "菜单角色关系ID")
    @TableId("role_menu_relation")
    private String roleMenuRelation;

    @ApiModelProperty(value = "角色编码")
    @TableField("role_req")
    private String roleReq;

    @ApiModelProperty(value = "菜单资源ID")
    @TableField("resource_id")
    private String resourceId;

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

    @ApiModelProperty(value = "备注")
    @TableField("comments")
    private String comments;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;


}
