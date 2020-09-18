package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统配置
 * 
 * @author wy
 * @date Apr 17, 2020
 */
@Data
public class UserSystemParamDTO implements Serializable {

  private static final long serialVersionUID = 1L;


  @ApiModelProperty(value = "主键ID")
  private String uuid;

  @ApiModelProperty(value = "参数编码")
  private String code;

  @ApiModelProperty(value = "参数名称")
  @TableField("name")
  private String name;

  @ApiModelProperty(value = "参数值")
  private String value;

  @ApiModelProperty(value = "状态")
  private Integer status;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "创建人")
  private String createUser;

  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updateTime;

  @ApiModelProperty(value = "更新人")
  private String updateUser;

}
