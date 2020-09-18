package com.icss.newretail.user.entity;

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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_organization")
@ApiModel(value = "UserOrganization对象", description = "组织机构")
public class UserOrganization implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "组织编码")
  @TableId("org_seq")
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
  @TableField("area_id")
  private String areaId;

  @ApiModelProperty(value = "状态(停用/启用)")
  @TableField("status")
  private Integer status;

  @ApiModelProperty(value = "组织显示顺序")
  @TableField("org_no")
  private Integer orgNo;

  @ApiModelProperty(value = "创建时间")
  @TableField("create_time")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "创建人")
  @TableField("create_user")
  private String createUser;

  @ApiModelProperty(value = "更新时间")
  @TableField("update_time")
  private LocalDateTime updateTime;

  @ApiModelProperty(value = "更新人")
  @TableField("update_user")
  private String updateUser;

  public UserOrganization() {
  }

}
