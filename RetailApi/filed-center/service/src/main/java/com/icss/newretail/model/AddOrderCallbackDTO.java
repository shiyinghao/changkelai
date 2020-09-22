package com.icss.newretail.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddOrderCallbackDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("WorksbackUrl")
    @JSONField(name="WorksbackUrl")
    private String WorksbackUrl; // 作品审核回调通知地址

    @JsonProperty("ProductionbackUrl")
    @JSONField(name="ProductionbackUrl")
    private String ProductionbackUrl; // 作品生产进度通知地址

    @JsonProperty("RoutebackUrl")
    @JSONField(name="RoutebackUrl")
    private String RoutebackUrl; // 物流发货节点通知地址

    @JsonProperty("OrderbackUrl")
    @JSONField(name="OrderbackUrl")
    private String OrderbackUrl; // 特殊原因下单失败后，创意酒修正重新下单将结果推送云店的地址

}