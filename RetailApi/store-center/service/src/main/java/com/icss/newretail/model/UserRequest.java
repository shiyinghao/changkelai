/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息查询参数
 * 
 * @date Apr 20, 2019
 */
@Data
public class UserRequest implements Serializable {

    private static final long serialVersionUID =1L;

    @ApiModelProperty(value = "组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "组织名称")
    private String  orgSeqName;

    @ApiModelProperty(value = "用户类型")
    private String userType;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "会员强绑定解除天数")
    private String bindDay;

    @ApiModelProperty(value = "弱绑定解除天数")
    private String weakBindDay;

    @ApiModelProperty(value = "解除绑定天数提醒")
    private String unbindDayWarn;

    @ApiModelProperty(value = "店员id")
    private String userId;

    @ApiModelProperty(value = "关键字")
    private String  keyWords;

    // //asc  升序   desc  降序
    @ApiModelProperty(value = "消费金额排序字段")
    private String sort;

    // //asc  升序   desc  降序
    @ApiModelProperty(value = "绑定状态排序字段")
    private String bdstatus;

    @ApiModelProperty(value = "用户类型初始化参数")
    private String initFlag;

}
