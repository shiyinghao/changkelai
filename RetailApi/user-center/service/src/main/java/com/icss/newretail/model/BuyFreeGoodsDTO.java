package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 买赠活动 商品库表查询dto
 * </p>
 *
 * @author ydt
 * @since 2020-04-19
 */

@Data
public class BuyFreeGoodsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动id")
    private String activityId;

    @ApiModelProperty(value = "已选择商品id")
    private List<String> includeGoods;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品编码")
    private String goodsSeq;

    @ApiModelProperty(value = "商品分类id")
    private String goodsTypeId;

    @ApiModelProperty(value = "商品id（查询商品所属活动id）")
    private String goodsId;

}