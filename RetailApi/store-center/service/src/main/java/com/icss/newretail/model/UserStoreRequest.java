package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户信息-UserInfo
 *
 * @author zhangzhijia
 * @date Apr 15, 2019
 */
@Data
public class UserStoreRequest {

	@ApiModelProperty(value = "组织机构编码")
	private String orgSeq;

	@ApiModelProperty(value = "店铺名称")
	private String storeName;

	@ApiModelProperty(value = "店铺类型(1标准店、2精品店、3综合体验店)")
	private Integer storeType;

	@ApiModelProperty(value = "状态")
	private Integer openStatus;

	@ApiModelProperty(value = "店主名称")
	private String storeOwnerName;

	@ApiModelProperty(value = "店长名称")
	private String storeManagerName;

	@ApiModelProperty(value = "法人")
	private String legalPerson;

	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "店铺地址")
	private String storeAddressDetail;

	@ApiModelProperty(value = "银行解锁（1-已解锁，2-已锁定）")
	private Integer icbcSwitch;

	@ApiModelProperty(value = "店铺编码")
	private String storeCode;

	@ApiModelProperty(value = "店铺授权码")
	private String authCode;

	@ApiModelProperty(value = "所属省")
	private String province;

	@ApiModelProperty(value = "所属城市")
	private String city;

	@ApiModelProperty(value = "所属区")
	private String county;

	@ApiModelProperty(value = "经销商公司名")
	private String companyName;

	@ApiModelProperty(value = "排序字段")
	private List<SortDTO> sortList;

	@ApiModelProperty(value = "所属战区")
	private String upOrgZone;

	@ApiModelProperty(value = "所属基地")
	private String baseName;
}
