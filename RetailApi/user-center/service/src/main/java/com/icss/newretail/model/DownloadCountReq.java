package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : yanghu
 * @date : Created in 2020/7/7 22:41
 * @description : 店铺下载排名查询---请求request  参数
 * @modified By :
 * @version: : 1.0.0
 */

@Data
public class DownloadCountReq implements Serializable {

    @ApiModelProperty("基地名称")
    private String baseName;

    @ApiModelProperty("战区名称")
    private String warZoneName;

    @ApiModelProperty("店铺授权码")
    private String authCode;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("排序字段，1 默认排序，2 活动名片下载次数升序，3 活动名片下载次数降序，" +
            "4商品名片下载次数升序，5商品名片下载次数降序，6店铺名片下载次数升序，7店铺名片下载次数降序，" +
            "8总下载次数升序，9总下载次数降序")
    private Integer sort;


}
