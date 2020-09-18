package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 订单信息
 * </p>
 *
 * @author yht
 * @since 2020-05-25
 */
@Data
public class TradeOrderGoodsDTO {

    @ApiModelProperty(value = "商品ID")
    @TableField("uuid")
    private String itemUuid;
    private String orderNo;

    @ApiModelProperty(value = "订单ID")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty(value = "商品编码")
    @TableField("goods_id")
    private String goodsId;

    @ApiModelProperty(value = "商品名称")
    @TableField("goods_name")
    private String goodsName;


    @ApiModelProperty(value = "商品图片缩略图")
    private String goodsPic;

    @ApiModelProperty(value = "计量单位")
    @TableField("unit")
    private String unit;


    @ApiModelProperty(value = "计量单位名称")
    private String unitName;

    @ApiModelProperty(value = "销售价")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "数量")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "销售金额")
    @TableField("money")
    private BigDecimal money;

    @ApiModelProperty(value = "折扣")
    @TableField("discount")
    private BigDecimal discount;

    @ApiModelProperty(value = "销售金额-折扣=实际金额")
    @TableField("real_money")
    private BigDecimal realMoney;

    @ApiModelProperty(value = "换算率")
    @TableField("exchange_rate")
    private BigDecimal exchange_rate;

    @ApiModelProperty(value = "对应的小包装商品编码")
    @TableField("small_pkgname_goods")
    private String smallPkgnameGoods;

    @ApiModelProperty(value = "对应小包装数量")
    @TableField("small_pkgname_amount")
    private BigDecimal smallPkgnameAmount;

    @ApiModelProperty(value = "购进价")
    @TableField("buy_price")
    private BigDecimal buyPrice;

    @ApiModelProperty(value = "会员卡卷id")
    @TableField("member_coupon_id")
    private String memberCouponId;

    @ApiModelProperty(value = "商品条码")
    private String barCode;

    // --------mx-------
    @ApiModelProperty(value = "订单商品信息Id")
    @TableField("uuid")
    private String uuid; //itemUuid

}
