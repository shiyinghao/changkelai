package com.icss.newretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("OutsideCoreOrderID")
    private String OutsideCoreOrderID;

    @JsonProperty("StoreName")
    private String StoreName; // 门店名称	XXX上地店

    @JsonProperty("StoreCompanyName")
    private String StoreCompanyName; // 门店经营公司主体，（营业执照公司名称）	宜宾五粮液创艺酒产业有限公司

    @JsonProperty("RealName")
    private String RealName; // 收货人姓名

    @JsonProperty("MobileNumber")
    private String MobileNumber; // 收货人手机号

    @JsonProperty("Province")
    private String Province; // 省份

    @JsonProperty("City")
    private String City; // 城市

    @JsonProperty("County")
    private String County; // 区县

    @JsonProperty("Address")
    private String Address; // 详细地址

    // @JsonProperty("PutsideCoreOrderID")
    // private String PutsideCoreOrderID; // 云店订单号

    // @JsonProperty("PaymentTypeID")
    // private String PaymentTypeID; // 支付方式	1：线上支付   2：线下支付

    // @JsonProperty("PaymentAgencyID")
    // private String PaymentAgencyID; // 支付机构	1：微信支付 2：支付宝支付 3：线下支付

    @JsonProperty("OrderAmount")
    private String OrderAmount; // 订单总金额

    @JsonProperty("OrderGoods")
    private List<AddOrderGoodsDTO> OrderGoods; // 订单商品

    @JsonProperty("CallbackUrlList")
    private AddOrderCallbackDTO CallbackUrlList; // 回调地址域

}