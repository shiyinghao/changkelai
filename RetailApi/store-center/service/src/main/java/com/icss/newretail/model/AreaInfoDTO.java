package com.icss.newretail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 行政区划信息-UserArea
 *
 * @author
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AreaInfoDTO", description = "行政区划信息")
public class AreaInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "区域ID")
    private String areaId;

    @ApiModelProperty(value = "区域编码")
    private String areaSeq;

    @ApiModelProperty(value = "区域名称")
    private String areaName;
    
    @ApiModelProperty(value = "上级区域编码")
    private String upAreaSeq;// 

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "门店数量")
    private int storeConut; // 统计行政区划内的门店数量 
}
