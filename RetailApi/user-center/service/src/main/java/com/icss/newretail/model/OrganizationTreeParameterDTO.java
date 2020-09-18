package com.icss.newretail.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 组织机构
 * </p>
 *
 * @author
 * @since 2019-04-10
 */
@Data
public class OrganizationTreeParameterDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "地区编码")
	private String orgSeq;

	@ApiModelProperty(value = "编码类型")
	private String orgType;

	@ApiModelProperty(value = "战区名称")
	private String theaterName;
	
	@ApiModelProperty(value = "基地名称")
	private String baseName;

	@ApiModelProperty(value = "门店名称")
	private String shopName;
	
	@ApiModelProperty(value = "店主姓名")
    private String shopOwnerName;
    
    @ApiModelProperty(value = "店主电话")
    private String shopOwnerTel;

}
