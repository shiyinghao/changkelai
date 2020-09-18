package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询门店信息 专用
 * ydt
 */
@Data
public class UserStoreReviewExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺姓名")
    @TableField("store_name")
    private String storeName;

    @ApiModelProperty(value = "店铺类型")
    private String storeTypeName;

    @ApiModelProperty(value = "所属省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "所属城市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "经销商公司名")
    @TableField("company_name")
    private String companyName;

    @ApiModelProperty(value = "法人")
    @TableField("legal_person")
    private String legalPerson;

    @ApiModelProperty(value = "法人手机号")
    @TableField("legal_person_phone")
    private String legalPersonPhone;


    @ApiModelProperty(value = "店铺电话")
    @TableField("store_phone")
    private String storePhone;

    @ApiModelProperty(value = "店铺详细地址")
    @TableField("store_address_detail")
    private String storeAddressDetail;

    @ApiModelProperty(value = "店铺经度")
    @TableField("store_lng")
    private String storeLng;

    @ApiModelProperty(value = "店铺纬度")
    @TableField("store_lat")
    private String storeLat;

    @ApiModelProperty(value = "所属战区")
    private String upOrgZone;

    @ApiModelProperty(value = "所属基地名称")
    private String baseName;

    @ApiModelProperty(value = "'审核状态(1-申请已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过，6-银行审核，7-银行审核通过，8-银行审核不通过)'")
    private String reviewStatusName;

    @ApiModelProperty(value = "'下一步审核状态'")
    private String reviewStatusNameNext;

}
