package com.icss.newretail.user.entity;

import java.math.BigDecimal;
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
 * 终端授权改价密码
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_grant_password")
@ApiModel(value = "GrantPassword对象", description = "终端授权改价密码")
public class GrantPassword implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@TableId("uuid")
	private String uuid;

	@ApiModelProperty(value = "组织编码")
	@TableField("org_seq")
	private String orgSeq;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private String userId;

	@ApiModelProperty(value = "折扣(0-10)")
	@TableField("discount")
	private BigDecimal discount;

	@ApiModelProperty(value = "状态0禁用1启用")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "创建人")
	@TableField("create_user")
	private String createUser;

	@ApiModelProperty(value = "创建时间")
	@TableField("create_time")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改人")
	@TableField("update_user")
	private String updateUser;

	@ApiModelProperty(value = "修改时间")
	@TableField("update_time")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "租户id")
	@TableField("tenant_id")
	private String tenantId;

}
