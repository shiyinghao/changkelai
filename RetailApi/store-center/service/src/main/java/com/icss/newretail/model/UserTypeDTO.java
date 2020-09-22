package com.icss.newretail.model;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserType对象", description="用户类型")
public class UserTypeDTO {
  
  @ApiModelProperty(value = "用户类型")
  private String userType;

  @ApiModelProperty(value = "用户类型名称")
  private String userTypeName;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "创建人")
  private String createUser;

  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updateTime;

  @ApiModelProperty(value = "更新人")
  private String updateUser;

  
}
