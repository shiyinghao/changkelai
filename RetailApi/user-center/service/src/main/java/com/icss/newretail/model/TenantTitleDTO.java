package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TenantTitleDTO {
	@ApiModelProperty(value = "uuid")
	private String uuid;

	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	@ApiModelProperty(value = "标题文字")
	private String loginRightNote;

	@ApiModelProperty(value = "标题图标")
	private String loginRightImg;

	@ApiModelProperty(value = "展示顺序")
	private Integer orderNo;

	@ApiModelProperty(value = "状态：1启用，0停用")
	private Integer status;

	@ApiModelProperty(value = "创建人")
	private String createUser;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "更新人")
	private String updateUser;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
