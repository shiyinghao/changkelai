package com.icss.newretail.user.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 店铺开票记录
 * </p>
 *
 * @author zhangzhijia
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_billing_record")
@ApiModel(value="BillingRecord对象", description="店铺开票记录")
public class BillingRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("bill_id")
    private String billId;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "店铺编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "税号")
    @TableField("duty_number")
    private String dutyNumber;

    @ApiModelProperty(value = "开户行")
    @TableField("opening_bank")
    private String openingBank;

    @ApiModelProperty(value = "开户行账号")
    @TableField("opening_bank_account")
    private String openingBankAccount;

    @ApiModelProperty(value = "公司名称")
    @TableField("company_name")
    private String companyName;

    @ApiModelProperty(value = "公司电话")
    @TableField("company_tel")
    private String companyTel;

    @ApiModelProperty(value = "公司地址")
    @TableField("company_address")
    private String companyAddress;

    @ApiModelProperty(value = "收货人")
    @TableField("receiver")
    private String receiver;

    @ApiModelProperty(value = "手机号")
    @TableField("receiver_tel")
    private String receiverTel;

    @ApiModelProperty(value = "收货地区")
    @TableField("receiver_region")
    private String receiverRegion;

    @ApiModelProperty(value = "详细收货地址")
    @TableField("receiver_address")
    private String receiverAddress;

    @ApiModelProperty(value = "手续费")
    @TableField("service_charge")
    private String serviceCharge;

    @ApiModelProperty(value = "开票金额")
    @TableField("billing_amount")
    private BigDecimal billingAmount;

    @ApiModelProperty(value = "物流单号")
    @TableField("logistics_no")
    private String logisticsNo;

    @ApiModelProperty(value = "备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty(value = "状态(/0-删除/1开票成功/2-开票中/3开票失败)")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
