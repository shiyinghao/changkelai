package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 登录用户session信息
 * 
 * @author zhangzhijia
 * @date Apr 16, 2019
 */
@Data
public class LoginUserPasswordDTO {

  @ApiModelProperty(value = "用户密码")
  private String password; // 用户ID
  //
  @ApiModelProperty(value = "巡店系统识别码")
 private String flag;
 
  @ApiModelProperty(value = "手机号码")
 private String tel;
 
}
