package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * <p>
 * 开票记录与订单绑定表
 * </p>
 *
 * @author syh
 * @since 2020-05-25
 */
@Data
public class BillingOrderShipDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    @ApiModelProperty(value = "开票ID")
    private String billId;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "支付ID")
    private String payRecordId;

    @ApiModelProperty(value = "银行支付流水ID")
    private String payStatementId;

    @ApiModelProperty(value = "0支付/1退款")
    private Integer orderType;

    @ApiModelProperty(value = "清分申请时间")
    private String payTime;

    @ApiModelProperty(value = "订单日期")
    private String orderDate;

    @ApiModelProperty(value = "收货日期")
    private String receivDate;

    @ApiModelProperty(value = "清分账号")
    private String thawCard;

    @ApiModelProperty(value = "清分到账日期")
    private String thawDate;

    @ApiModelProperty(value = "订单销售量")
    private BigDecimal totalAmount = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);

    @ApiModelProperty(value = "订单金额")
    private BigDecimal totalMoney = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountMoney = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);

    @ApiModelProperty(value = "实收金额")
    private BigDecimal receivedMoney = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);

    @ApiModelProperty(value = "手续费")
    private BigDecimal feeMoney = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);

    @ApiModelProperty(value = "清分到账金额")
    private BigDecimal thawMoney = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);

    @ApiModelProperty(value = "支付状态：0已支付，1待清分(已收货)，2待确认(已申请清分)，3银行清分，4确认到账 ，5打印发票")
    private Integer status;

    @ApiModelProperty(value = "交易时间")
    private String orderTime;

    @ApiModelProperty(value = "交易时间格式化")
    private String orderTimeFmt;

    @ApiModelProperty(value = "支付完成时间")
    private String finishedPayTime;

    @ApiModelProperty(value = "支付完成时间格式化")
    private String finishedPayTimeFmt;

    @ApiModelProperty(value = "买家编号")
    private String memberId;

    @ApiModelProperty(value = "买家姓名")
    private String memberName;

    @ApiModelProperty(value = "买家电话")
    private String tel;

    @ApiModelProperty(value = "会员卡号")
    private String membercardId;

    @ApiModelProperty(value = "支付宝唯一识别号")
    private String alipayId;

    @ApiModelProperty(value = "微信唯一识别号")
    private String wechatId;

    @ApiModelProperty(value = "业务员姓名")
    private String UserName;

    @ApiModelProperty(value = "业务员头像")
    private String UserHeadPic;

    @ApiModelProperty(value = "配送方式(1门店自提/2配送服务/3快递服务)")
    private Integer deliveryMethod;

    @ApiModelProperty(value = "订单状态(0待支付/1待发货/2待收货/3已完成/4已取消)")
    private Integer orderStatus;

    @ApiModelProperty(value = "详细商品")
    private List<TradeOrderGoodsDTO> goodsList;

    @Autowired
    public String getOrderTimeFmt(){
        if (orderTime != null && orderTime.length() > 10) {
            orderTimeFmt = orderTime.substring(0, 4) + "年" + orderTime.substring(5, 7) + "月" + orderTime.substring(8, 10) + "日";
        }
        return orderTimeFmt;
    }

    @Autowired
    public String getFinishedPayTimeFmt(){
        if (finishedPayTime != null && finishedPayTime.length() > 10) {
            finishedPayTimeFmt = finishedPayTime.substring(0, 4) + "年" + finishedPayTime.substring(5, 7) + "月" + finishedPayTime.substring(8, 10) + "日";
        }
        return finishedPayTimeFmt;
    }
}
