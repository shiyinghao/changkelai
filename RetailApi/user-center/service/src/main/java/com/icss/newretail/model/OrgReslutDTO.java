package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrgReslutDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "上级组织编码")
    private String upOrgSeq;

    @ApiModelProperty(value = "组织类型(1总部/2战区/3基地/4门店)")
    private String orgType;

    @ApiModelProperty(value = "区域ID(国家发布的行政区域代码)")
    private String areaId;

    @ApiModelProperty(value = "状态(停用0/启用1)")
    private Integer status;

    @ApiModelProperty(value = "组织显示顺序")
    private Integer orgNo;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;
}

