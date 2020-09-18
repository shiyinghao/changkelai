/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class UserAndStoreInfoRequest {

    @ApiModelProperty(value = "人员id")
    private String userId;

    @ApiModelProperty(value = "店铺组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "人员信息查询")
    private UserInfoDTO userInfoDTO;

    @ApiModelProperty(value = "店铺信息详情")
    private UserStoreInfoDTO storeInfoDTO;

    @ApiModelProperty(value = "商品信息详情")
    private List<GoodsInfoDetailDTO> goodsList;

}
