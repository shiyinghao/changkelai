package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : yanghu
 * @date : Created in 2020/7/7 21:05
 * @description : 战区统计DTO
 * @modified By :
 * @version: : 1.0.0
 */
@Data
public class WarZoneDownloadCountDTO {

    @ApiModelProperty(value = "ID")
    private String uuid;

    @ApiModelProperty(value = "排名")
    private Integer grade;

    @ApiModelProperty(value = "战区名称")
    private String upOrgZone;

    @ApiModelProperty(value = "基地名称")
    private String baseName;

    @ApiModelProperty(value = "店铺授权编码")
    private String authCode;

    @ApiModelProperty(value = "店铺组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "活动名片下载次数")
    private Integer activityCardDownloadCount;

    @ApiModelProperty(value = "商品名片下载次数")
    private Integer goodsCardDownloadCount;

    @ApiModelProperty(value = "店铺名片下载次数")
    private Integer storeCardDownloadCount;

    @ApiModelProperty(value = "总下载次数")
    private Integer sumCount;

}
