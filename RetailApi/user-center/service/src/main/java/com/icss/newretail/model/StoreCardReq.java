package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : yanghu
 * @date : Created in 2020/7/17 11:32
 * @description : 各种卡片  接收参数Req
 * @modified By :
 * @version: : 1.0.0
 */
@Data
public class StoreCardReq {

    @ApiModelProperty(value = "店铺组织编码")
    private String orgSeq;

    private String userId;
    private String headPicUrl;

    @ApiModelProperty(value = "门头照片地址")
    private String doorwayPic;

    @ApiModelProperty(value = "店铺介绍")
    private String storeDesc;

    @ApiModelProperty(value = "店铺对应卡片风格id")
    private String cardId;

}
