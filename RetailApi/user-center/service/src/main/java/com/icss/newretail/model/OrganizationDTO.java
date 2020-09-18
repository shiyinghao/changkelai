package com.icss.newretail.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织机构编码信息
 *
 * @author zhangzhijia
 * @date Jun 21, 2019
 */
@Data
public class OrganizationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "组织编码")
	private String orgSeq;

	@ApiModelProperty(value = "组织名称")
	private String orgName;

	@ApiModelProperty(value = "上级组织编码")
	private String upOrgSeq;

	@ApiModelProperty(value = "组织级别")
	private Integer level;

	@ApiModelProperty(value = "上级组织名称")
	private String upOrgSeqName;

	@ApiModelProperty(value = "组织类型")
	private String orgType;

	@ApiModelProperty(value = "区域ID(国家发布的行政区域代码)")
	private String areaId;

	@ApiModelProperty(value = "区域名称(国家发布的行政区域代码)")
	private String areaName; // 区域名称

	@ApiModelProperty(value = "状态(停用/启用)")
	private Integer status;

	@ApiModelProperty(value = "组织显示顺序")
	private Integer orgNo;

	@ApiModelProperty(value = "创建人")
	private String createUser;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "更新人")
	private String updateUser;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	@ApiModelProperty(value = "组织类型名称")
	private String orgTypeName;

	@ApiModelProperty(value = "子结点")
	private List<OrganizationDTO> children;

	@ApiModelProperty(value = "店主名称")
	private String shopOwnerName;

	@ApiModelProperty(value = "店主手机号")
	private String shopOwnerTel;

	@ApiModelProperty(value = "是否有子节点")
	private Boolean hasChildren;
	
	@ApiModelProperty(value = "战区名称")
    private String warzoneName;
    
    @ApiModelProperty(value = "基地名称")
    private String baseName;
    
    @ApiModelProperty(value = "店铺名称")
    private String storeName;
    
    @ApiModelProperty(value = "经销商名称")
    private String companyName;

	@ApiModelProperty(value = "授权码")
	private String authCode;

}
