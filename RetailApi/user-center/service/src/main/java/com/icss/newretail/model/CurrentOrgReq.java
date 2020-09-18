package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CurrentOrgReq implements Serializable{

    @ApiModelProperty(value = "组织编码")
    private String orgSeq;

//    @ApiModelProperty(value = "等级 1:集团 2:战区 3:基地 4:门店")
//    private String level;
//
//    @ApiModelProperty(value = "类型 1:集团 2:战区 3:基地 4:门店")
//    private String orgType;

}
