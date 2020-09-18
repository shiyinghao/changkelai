/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 门店查询参数
 * 
 */

@Data
public class ShopGoodsParamDTO {

    @ApiModelProperty(value = "经度")
    private BigDecimal lng;

    @ApiModelProperty(value = "维度")
    private BigDecimal lat;
    
    @ApiModelProperty(value = "范围（米）")
    private int circle;// 查询半径
    
    @ApiModelProperty(value = "0:距离优先/1:推荐店铺优先/2:相同档位距离推荐店铺优先")
    private int orderType = 0;// 排序类型默认距离优先距离档位500米/1000米/2000米/5000米
    
    @ApiModelProperty(value = "返回数量")
    private int limit = 1000;// 限制返回数据默认最多100

    @ApiModelProperty(value = "区域编码")
    private String areaSeq;

    @ApiModelProperty(value = "门店组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "商品id")
    private String goodsId;
    
    private String memberId;
    
    private String zqOrgSeq;
    

}
