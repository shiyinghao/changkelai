package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 组织机构编码信息
 *
 * @author fangxiaomin
 * @date 2019年10月23日
 * <p>Description: </p>
 */

@Data
public class OrganizationTypeDTO {

	@ApiModelProperty(value = "组织类型")
	private String orgType;

	@ApiModelProperty(value = "组织类型级别")
	private String orgLevel;

	@ApiModelProperty(value = "组织类型描述")
	private String description;

	@ApiModelProperty(value = "组织类型名称")
	private String orgTypeName;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "创建人")
	private String createUser;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "更新人")
	private String updateUser;


}
