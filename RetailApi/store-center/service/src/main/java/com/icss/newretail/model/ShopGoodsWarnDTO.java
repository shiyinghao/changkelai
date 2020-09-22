package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 门店商品排名
 * @author cym
 * @since 2020-05-07
 */
@Data
public class ShopGoodsWarnDTO  {
	
	@ApiModelProperty(value = "预警流水号")
    private String uuid;
	
	@ApiModelProperty(value = "商品编码")
    private String goodsId;
    
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    
    @ApiModelProperty(value = "商品码")
    private String goodsSeq;
    
    @ApiModelProperty(value = "商品图片")
    private String goodsPic;
    
    @ApiModelProperty(value = "计量单位")
    private String unit;
    
    @ApiModelProperty(value = "计量单位名称")
    private String unitName;

    @ApiModelProperty(value = "库存数量")
    private BigDecimal amount = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
    
    @ApiModelProperty(value = "预警阈值")
    private Integer warningValue = 0;
    
    @ApiModelProperty(value = "战区上架状态(1-未上架(没有拉进战区的商品) 2战区下架3-战区上架)")
    private Integer isShelf;
    
    @ApiModelProperty(value = "商品是否在会员价活动中  1 会员价    0不是会员价")
    private Integer whetherMemberPrice;

    @ApiModelProperty(value = "会员价--参加了活动-会员价格---没有为null")
    private BigDecimal memberPrice;

    @ApiModelProperty(value = "价格")
    private BigDecimal salePrice = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
}
