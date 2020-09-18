package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户所属组织信息
 * 
 * @author wangjie
 * @date 2020-05-23
 */
@Data
public class UserOrganizationInfoDTO implements Serializable {

    private static final long serialVersionUID =1L;

    @ApiModelProperty(value = "基地业务经理ID")
    private String baseUserId;

    @ApiModelProperty(value = "基地业务经理姓名")
    private String baseUserName;

    @ApiModelProperty(value = "所属基地orgSeq")
    private String baseOrgSeq;

    @ApiModelProperty(value = "所属基地组织名称")
    private String baseOrgName;

    @ApiModelProperty(value = "所属战区orgSeq")
    private String warZoneOrgSeq;

    @ApiModelProperty(value = "所属战区组织名称")
    private String warZoneOrgName;

}
