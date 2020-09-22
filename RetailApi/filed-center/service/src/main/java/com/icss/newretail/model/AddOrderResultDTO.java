package com.icss.newretail.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddOrderResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("CoreOrderID")
    @JSONField(name="CoreOrderID")
    private String CoreOrderID;

    @JsonProperty("WorksID")
    @JSONField(name="WorksID")
    private String WorksID;

}