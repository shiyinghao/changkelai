package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date Apr 16, 2019
 */
@Data
public class MemberUserDTO {
	
    @ApiModelProperty(value = "业务员id")
    private String userId;

    @ApiModelProperty(value = "员工编号")
    private String employeeNo;

    @ApiModelProperty(value = "维信昵称")
    private String nickName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "用户类型(1集团人员2战区人员3基地人员4店主5店长6店员)")
    private String userType;

    @ApiModelProperty(value = "头像地址")
    private String headPicUrl;

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "所属组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "手机号")
    private String tel;

}
