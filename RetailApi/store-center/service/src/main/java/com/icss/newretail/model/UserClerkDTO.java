package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户信息-UserInfo
 * 
 * @author zhangzhijia
 * @date Apr 15, 2019
 */
@Data
public class UserClerkDTO {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户中文名字")
    private String realName;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "用户类型(1集团人员2战区人员3基地4店主5店长6店员)")
    private String userType;

    @ApiModelProperty(value = "所属组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "身份证")
    private String identity;

    @ApiModelProperty(value = "状态(1启用/0停用/2删除)")
    private Integer status;

    private String headPicUrl;

    private String idCard;

    @ApiModelProperty(value = "信息是否完整(1-是/0-否)")
    private Integer isInfoComplete;

    @ApiModelProperty(value = "员工编号")
    private String employeeNo;

    @ApiModelProperty(value = "学历(0-高中以下 1-大专 2-本科 3-硕士")
    @TableField("academic")
    private Integer academic;

    @ApiModelProperty(value = "工作年限")
    @TableField("work_year")
    private Integer workYear;

    @ApiModelProperty(value = "姓别(1-男0-女")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "上岗日期")
    private String onboardDate;

}