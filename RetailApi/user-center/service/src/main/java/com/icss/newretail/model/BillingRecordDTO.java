package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BillingRecordDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String billId;

    private String type;

    @ApiModelProperty(value = "店铺编码")
    private String orgSeq;

    @ApiModelProperty(value = "税号")
    private String dutyNumber;

    @ApiModelProperty(value = "开户行")
    private String openingBank;

    @ApiModelProperty(value = "开户行账号")
    private String openingBankAccount;

    @ApiModelProperty(value = "工行Merid(12位识别码")
    private String merid;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司电话")
    private String companyTel;

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "收货人")
    private String receiver;

    @ApiModelProperty(value = "手机号")
    private String receiverTel;

    @ApiModelProperty(value = "收货地区")
    private String receiverRegion;

    @ApiModelProperty(value = "详细收货地址")
    private String receiverAddress;

    @ApiModelProperty(value = "手续费")
    private String serviceCharge;

    @ApiModelProperty(value = "开票金额")
    private BigDecimal billingAmount;

    @ApiModelProperty(value = "物流单号")
    private String logisticsNo;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "状态(/0-删除/1开票成功/2-开票中/3开票失败)")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    private List<BillingOrderShipDTO> billingOrderShipDTOList;

    private UserStoreInfoDTO userStoreInfoDTO;
}
