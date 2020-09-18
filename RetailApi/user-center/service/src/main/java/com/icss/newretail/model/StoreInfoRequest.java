package com.icss.newretail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="查询门店条件信息", description="条件信息")
public class StoreInfoRequest {

    @ApiModelProperty(value = "门店名称")
    private String storeName;
    
    @ApiModelProperty(value = "设备分类")
    private String deviceType;

    @ApiModelProperty(value = "上机组织编码")
    private String upOrgSeq;

    @ApiModelProperty(value = "战区编码")
    private String zqOrgSeq;

    @ApiModelProperty(value = "等级id")
    private String gradelevelId;

    @ApiModelProperty(value = "方案id")
    private String planId;

    @ApiModelProperty(value = "卡卷id")
    private String couponId;

}
