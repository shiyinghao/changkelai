package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StoreMessageRequest {

    @ApiModelProperty(value = "门店id")
    private String storeId;

    @ApiModelProperty(value = "消息是否已读")
    private String isReaded;

    @ApiModelProperty(value = "推送方式")
    private String contentsType;

}
