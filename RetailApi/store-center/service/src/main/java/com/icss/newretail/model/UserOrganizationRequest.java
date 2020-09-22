package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 组织机构
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
public class UserOrganizationRequest {

  @ApiModelProperty(value = "组织编码")
  private String orgSeq;

  @ApiModelProperty(value = "组织名称")
  @TableField("org_name")
  private String orgName;

  @ApiModelProperty(value = "上级组织编码")
  @TableField("up_org_seq")
  private String upOrgSeq;

  @ApiModelProperty(value = "组织级别")
  @TableField("level")
  private Integer level;

  @ApiModelProperty(value = "组织类型(总店/分店/门店/)")
  @TableField("org_type")
  private String orgType;

  @ApiModelProperty(value = "区域ID(国家发布的行政区域代码)")
  private String areaId;

  @ApiModelProperty(value = "状态(停用/启用)")
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
