/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 门店查询参数
 *
 * @author zhangzhijia
 * @date Apr 17, 2019
 */

@Data
public class UserReviewRequest {

    @ApiModelProperty(value = "店铺姓名")
    private String storeName;

    @ApiModelProperty(value = "审核状态")
    private Integer reviewStatus;

    @ApiModelProperty(value = "审核类型")
    private Integer reviewType;

    @ApiModelProperty(value = "店铺类型(1标准店、2精品店、3综合体验店)")
    private Integer storeType;

    @ApiModelProperty(value = "所属战区组织编码")
    private String upOrgSeq;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;


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


}
