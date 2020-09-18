package com.icss.newretail.model;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门店类型
 * 
 * @author zhangzhijia
 * @date Apr 17, 2019
 */
@Data
public class StoreTypeDTO {

  @ApiModelProperty(value = "门店类型")
  private String storeType;

  @ApiModelProperty(value = "门店类型名称")
  private String storeTypeName;

  @ApiModelProperty(value = "创建人")
  private String createUser;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "更新人")
  private String updateUser;

  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updateTime;

}
