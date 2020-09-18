package com.icss.newretail.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value = "GrantPassword对象", description = "终端授权改价密码")
public class GrantPasswordDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private String uuid;

	@ApiModelProperty(value = "组织编码")
	private String orgSeq;

	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "折扣(0-10)")
	private BigDecimal discount;

	@ApiModelProperty(value = "状态0禁用1启用")
	private Integer status;

	@ApiModelProperty(value = "创建人")
	private String createUser;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改人")
	private String updateUser;

	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "租户id")
	private String tenantId;
}
