package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.icss.newretail.model.RoleDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_role")
@ApiModel(value = "UserRole对象", description = "角色")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色ID")
	@TableId("role_id")
	private String roleId;

	@ApiModelProperty(value = "角色编码")
	@TableField("role_req")
	private String roleReq;

	@ApiModelProperty(value = "角色名称")
	@TableField("role_name")
	private String roleName;

	@ApiModelProperty(value = "角色类型（一些特殊的，代码特殊处理的角色类型，例如超级用户类型。）")
	@TableField("role_type")
	private Integer roleType;

	@ApiModelProperty(value = "角色说明")
	@TableField("role_desc")
	private String roleDesc;

	@ApiModelProperty(value = "状态（启用/停用）")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	@TableField("create_time")
	private Date createTime;

	@ApiModelProperty(value = "创建人")
	@TableField("create_user")
	private String createUser;

	@ApiModelProperty(value = "更新时间")
	@TableField("upate_time")
	private Date upateTime;

	@ApiModelProperty(value = "更新人")
	@TableField("update_user")
	private String updateUser;

	@ApiModelProperty(value = "备注")
	@TableField("comments")
	private String comments;

	public UserRole(RoleDTO role) {

		this.roleId = role.getRoleId();
		this.roleReq = role.getRoleReq();
		this.roleName = role.getRoleName();
		this.roleType = role.getRoleType();
		this.roleDesc = role.getRoleDesc();
		this.comments = role.getComments();
		this.status = role.getStatus();

		this.createUser = role.getCreateUser();
		this.createTime = role.getCreateTime();
		this.updateUser = role.getUpdateUser();
		this.upateTime = role.getUpateTime();

	}

	public UserRole() {
	}
}
