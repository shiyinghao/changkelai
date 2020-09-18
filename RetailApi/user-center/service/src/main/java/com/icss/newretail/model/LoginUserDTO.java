package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 登录用户session信息
 * 
 * @author zhangzhijia
 * @date Apr 16, 2019
 */
@Data
public class LoginUserDTO {

  @ApiModelProperty(value = "用户ID")
  private String userId; // 用户ID

  @ApiModelProperty(value = "用户名称")
  private String userName; // 用户名称
  
  @ApiModelProperty(value = "用户类型")
  private String userType; // 用户类型

  @ApiModelProperty(value = "用户账号")
  private String account; // 账号

  @ApiModelProperty(value = "所属组织结构代码")
  private String orgSeq; // 所属组织结构代码

  @ApiModelProperty(value = "所属区域")
  private String areaSeq; // 用户所属组织所在行政区域（省/市）

  @ApiModelProperty(value = "授权令牌")
  private String token;// token信息

  @ApiModelProperty(value = "授权令牌有效期限")
  //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date tokenExpTime;// token信息

  @ApiModelProperty(value = "登陆时间")
  private Date currLoginTime; // 当前登陆时间

  @ApiModelProperty(value = "密码错误次数")
  private Integer wrongCount;

  @ApiModelProperty(value = "是否锁定")
  private Integer isLock;

  @ApiModelProperty(value = "锁定时间")
  private Date lockTime;

  @ApiModelProperty(value = "姓名")
  private String realName;
  /**
   * update 20200401
   * @author  yanghu
   */
//-------------start------------------
  @ApiModelProperty(value = "手机号")
  private String tel;

  @ApiModelProperty(value = "邮箱")
  private String email;

  @ApiModelProperty(value = "组织集合")
  private List<OrgList> orgList;

  @ApiModelProperty(value = "角色集合")
  private List<RoleList> roleList;

  @ApiModelProperty(value = "组织名称")
  private String orgName;

  @ApiModelProperty(value = "组织类型")
  private Integer orgType;

//  @ApiModelProperty(value = "组织id")
//  private List orgIdList;
//
//  @ApiModelProperty(value = "组织name")
//  private List orgNameList;
//
//  @ApiModelProperty(value = "角色id")
//  private List roleIdList;
//
//  @ApiModelProperty(value = "角色name")
//  private List roleNameList;
//--------------end------------------
}
