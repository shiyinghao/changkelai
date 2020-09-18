package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 查询门店信息 专用
 * ydt
 */
@Data
public class UserStoreExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "授权码")
    @TableField("auth_code")
    private String authCode;

    @ApiModelProperty(value = "店铺姓名")
    @TableField("store_name")
    private String storeName;

    @ApiModelProperty(value = "店铺类型")
    private String storeTypeName;

    @ApiModelProperty(value = "经营面积")
    @TableField("manager_area")
    private BigDecimal managerArea;

    @ApiModelProperty(value = "营业状态")
    private String openStatusName;

    @ApiModelProperty(value = "营业时间")
    @TableField("open_time")
    private String openTime;

    @ApiModelProperty(value = "开业日期")
    @TableField("open_date")
    private String openDate;

    @ApiModelProperty(value = "所属省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "所属城市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "所属区")
    @TableField("county")
    private String county;

    @ApiModelProperty(value = "经销商公司名")
    @TableField("company_name")
    private String companyName;

    @ApiModelProperty(value = "法人")
    @TableField("legal_person")
    private String legalPerson;

    @ApiModelProperty(value = "法人手机号")
    @TableField("legal_person_phone")
    private String legalPersonPhone;

    @ApiModelProperty(value = "店主姓名")
    @TableField("store_owner_name")
    private String storeOwnerName;

    @ApiModelProperty(value = "店主手机号")
    @TableField("store_owner_phone")
    private String storeOwnerPhone;

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

    @ApiModelProperty(value = "店铺申请时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "店铺审核时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "营业开始时间")
    @TableField("open_begin_time")
    private String openBeginTime;

    @ApiModelProperty(value = "营业结束时间")
    @TableField("open_end_time")
    private String openEndTime;

    @ApiModelProperty(value = "工行Merid(12位识别码)")
    private String merid;

    @ApiModelProperty(value = "工行协议号")
    private String agreement;

    @ApiModelProperty(value = "微信识别码")
    private String wechatId;

    @ApiModelProperty(value = "所属战区")
    private String upOrgZone;

    @ApiModelProperty(value = "所属基地名称")
    private String baseName;

//    @ApiModelProperty(value = "店铺编码")
//    @TableField("store_code")
//    private String storeCode;

    @ApiModelProperty(value = "建档日期")
    private String createdDate;

    @ApiModelProperty(value = "是否是工行对公账户")
    private String isIcbcName;


    @ApiModelProperty(value = "支付信息状态")
    private String icbcSwitchName;

    @ApiModelProperty(value = "档案更新时间")
    @TableField("icbc_update_time")
    private LocalDateTime icbcUpdateTime;

    @ApiModelProperty(value = "银行账号")
    @TableField("bank_account")
    private String bankAccount;


    @ApiModelProperty(value = "银行名称")
    @TableField("bank_name")
    private String bankName;

}
