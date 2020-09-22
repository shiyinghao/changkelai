package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织机构编码信息
 *
 */
@Data
public class OrganizationChildDTO {

	@ApiModelProperty(value = "组织编码")
	private String orgSeq;

	@ApiModelProperty(value = "组织名称")
	private String orgName;

	@ApiModelProperty(value = "上级组织编码")
	private String upOrgSeq;

	@ApiModelProperty(value = "组织类型")
	private String orgType;

	@ApiModelProperty(value = "战区编码")
	private String zqOrgSeq;

}
