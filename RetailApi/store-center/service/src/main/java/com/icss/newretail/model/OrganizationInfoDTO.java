package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织机构信息
 * 
 * @author zhangzhijia
 * @date Jun 21, 2019
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrganizationInfoDTO extends OrganizationDTO {

  @ApiModelProperty(value = "组织编码")
  private String orgSeq;

  @ApiModelProperty(value = "公司名称")
  private String companyName;

  @ApiModelProperty(value = "公司地址")
  private String companyAddress;

  @ApiModelProperty(value = "公司电话")
  private String companyTel;

  @ApiModelProperty(value = "公司类型(连锁/加盟)")
  private String companyType;

  @ApiModelProperty(value = "地市区域编码")
  private String areaSeq;

  @ApiModelProperty(value = "公司简介")
  private String companyDesc;

  @ApiModelProperty(value = "是否启用")
  private Integer status;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @ApiModelProperty(value = "更新人")
  private String updateUser;

  @ApiModelProperty(value = "经度")
  private BigDecimal longitude;

  @ApiModelProperty(value = "维度")
  private BigDecimal lat;

  @ApiModelProperty(value = "店铺logo")
  private String logoUrl;

  @ApiModelProperty(value = "是否连锁")
  private Integer isChain;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "创建人")
  private String createUser;

  @ApiModelProperty(value = "距离")
  private Integer distance = 0;

  @ApiModelProperty(value = "距离说明")
  private String distanceStr = "";

  @ApiModelProperty(value = "距离单位")
  private String distanceUnit = "";

  @ApiModelProperty(value = "起送金额")
  private BigDecimal minMoney;

  @ApiModelProperty(value = "配送范围")
  private BigDecimal deliveryRangle;

  @ApiModelProperty(value = "配送金额")
  private BigDecimal deliveryMoney;
  
  @ApiModelProperty(value = "租户ID")
  private String tenantId;
  
 
  
  List<StoreParamDTO> params = new ArrayList<StoreParamDTO>();

  public void setDistance(Integer distance) {
    this.distance = distance;
    if (distance != null && distance >= 0) {
      if (distance >= 1000) {
        DecimalFormat numDf = new DecimalFormat("#.##");
        this.distanceStr = numDf.format(distance * 0.001);
        this.distanceUnit = "km";
      } else {
        this.distanceStr = String.valueOf(distance);
        this.distanceUnit = "m";
      }
    }
  }
}
