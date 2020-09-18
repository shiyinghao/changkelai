package com.icss.newretail.model;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门店信息
 * @author cym
 * @date 2020-05-16
 */
@Data
public class ShopInfoDTO {
	
	@ApiModelProperty(value = "门店编码")
	private String orgSeq;

	@ApiModelProperty(value = "门店名称")
	private String orgName;
	
	@ApiModelProperty(value = "授权码")
    private String authCode;

	@ApiModelProperty(value = "基地编码")
	private String jdOrgSeq;
	
	@ApiModelProperty(value = "基地组织名称")
	private String jdOrgName;
	
	@ApiModelProperty(value = "战区编码")
	private String zqOrgSeq;
	
	@ApiModelProperty(value = "战区组织名称")
	private String zqOrgName;

	@ApiModelProperty(value = "组织级别")
	private Integer level;

	@ApiModelProperty(value = "组织类型")
	private String orgType;
	
	@ApiModelProperty(value = "组织类型名称")
	private String orgTypeName;

	@ApiModelProperty(value = "区域ID(国家发布的行政区域代码)")
	private String areaId;
	
	@ApiModelProperty(value = "区域名称(国家发布的行政区域代码)")
	private String areaName; 

	@ApiModelProperty(value = "省")
	private String province; 
	
	@ApiModelProperty(value = "市")
	private String city;

	@ApiModelProperty(value = "店铺类型(1-一代店,二代店、、2-三代店，3-四代店，4-文化综合店)")
	private Integer storeType;
	
	@ApiModelProperty(value = "店铺地址")
	private String storeAddress;

	@ApiModelProperty(value = "店铺详细地址")
	private String storeAddressDetail;
	
	@ApiModelProperty(value = "店主名称")
	private String shopOwnerName;
	
	@ApiModelProperty(value = "店主手机号")
	private String shopOwnerTel;
	
	@ApiModelProperty(value = "法人名称")
	private String legalPerson;
	
	@ApiModelProperty(value = "法人手机号")
	private String legalPersonPhone;

	@ApiModelProperty(value = "组织显示顺序")
	private Integer orgNo;
	
	@ApiModelProperty(value = "状态(停用/启用)")
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
