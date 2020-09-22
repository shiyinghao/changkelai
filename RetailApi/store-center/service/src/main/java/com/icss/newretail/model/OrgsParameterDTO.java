package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织id串-OrgsParameterDTO
 */
@Data
public class OrgsParameterDTO {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "组织编码串")
    private String orgSeqs;
	
	@ApiModelProperty(value = "组织类型")
    private String orgType;
	
	@ApiModelProperty(value = "战区编码")
    private String warzoneId;

	@ApiModelProperty(value = "战区名称")
    private String warzoneName;
	
	@ApiModelProperty(value = "基地编码")
    private String baseId;
	
	@ApiModelProperty(value = "基地名称")
    private String baseName;
	
	@ApiModelProperty(value = "门店编码")
    private String shopId;
	
	@ApiModelProperty(value = "门店授权码")
    private String authCode;
    
    @ApiModelProperty(value = "门店名称")
    private String shopName;
    
    @ApiModelProperty(value = "店主姓名")
    private String shopOwnerName;
	
}
