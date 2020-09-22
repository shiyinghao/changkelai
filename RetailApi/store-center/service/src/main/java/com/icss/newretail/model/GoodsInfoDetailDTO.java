package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详细信息
 */
@Data
public class GoodsInfoDetailDTO {

    @ApiModelProperty(value = "商品id")
    private String uuid;

    @ApiModelProperty(value = "外部系统码")
    private String sysCode;

    @ApiModelProperty(value = "国际条码")
    private String barCode;

    @ApiModelProperty(value = "标签类型(1-婚宴 2-家宴 3-商务)")
    private String tagType;

    @ApiModelProperty(value = "总部标准价")
    private BigDecimal salePrice;

    @ApiModelProperty(value = "总部底价")
    private BigDecimal saleLowerPrice;

    @ApiModelProperty(value = "总部上限价")
    private BigDecimal saleUpperPrice;

    @ApiModelProperty(value = "商品编码")
    private String goodsSeq;

    @ApiModelProperty(value = "商品编码/条形码")
    private String goodsBarcode;

    @ApiModelProperty(value = "商品类型ID")
    private String goodsTypeId;

    @ApiModelProperty(value = "商品类型名称")
    private String goodsTypeName;

    @ApiModelProperty(value = "品牌编码")
    private String brandReq;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "是否最小单位(0:否/1:是)")
    private Integer isMinUnit = 1;

    @ApiModelProperty(value = "最小单位商品id")
    private String minUnitGoodsId;

    @ApiModelProperty(value = "最小单位商品名称")
    private String minUnitGoodsName;

    @ApiModelProperty(value = "最小单位")
    private String minUnit;

    @ApiModelProperty(value = "最小单位名称")
    private String minUnitName;

    @ApiModelProperty(value = "单位转换比例")
    private Integer unitConvertRate = 1;
    
    @ApiModelProperty(value = "商品描述")
    private String goodsDescribe;

    @ApiModelProperty(value = "图片缩略图")
    private String goodsPic;

    @ApiModelProperty(value = "战区上架状态(1-未上架(没有拉进战区的商品) 2战区下架3-战区上架)")
    private Integer isShelf;


    @ApiModelProperty(value = "活动商品id")
    private String activityGoodsId;

    @ApiModelProperty(value = "活动售价")
    private BigDecimal activityPrice;

}
