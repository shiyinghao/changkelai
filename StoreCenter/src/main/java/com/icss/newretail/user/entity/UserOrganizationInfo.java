package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 组织机构信息
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_organization_info")
@ApiModel(value = "UserOrganizationInfo对象", description = "组织机构信息")
public class UserOrganizationInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "组织编码")
	@TableId("org_seq")
	private String orgSeq;

	@ApiModelProperty(value = "公司名称")
	@TableField("company_name")
	private String companyName;

	@ApiModelProperty(value = "公司地址")
	@TableField("company_address")
	private String companyAddress;

	@ApiModelProperty(value = "公司电话")
	@TableField("company_tel")
	private String companyTel;

	@ApiModelProperty(value = "公司类型(连锁/加盟)")
	@TableField("company_type")
	private String companyType;

	@ApiModelProperty(value = "地市区域编码")
	@TableField("area_seq")
	private String areaSeq;

	@ApiModelProperty(value = "公司简介")
	@TableField("company_desc")
	private String companyDesc;

	@ApiModelProperty(value = "是否启用")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "更新时间")
	@TableField("update_time")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "更新人")
	@TableField("update_user")
	private String updateUser;

	@ApiModelProperty(value = "经度")
	@TableField("longitude")
	private BigDecimal longitude;

	@ApiModelProperty(value = "维度")
	@TableField("lat")
	private BigDecimal lat;

	@ApiModelProperty(value = "店铺logo")
	@TableField("logo_url")
	private String logoUrl;

	@ApiModelProperty(value = "是否连锁")
	@TableField("is_chain")
	private Integer isChain;

	@ApiModelProperty(value = "创建时间")
	@TableField("create_time")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "创建人")
	@TableField("create_user")
	private String createUser;

	@TableField(exist = false)
	private double range;

	public UserOrganizationInfo() {

	}

}
