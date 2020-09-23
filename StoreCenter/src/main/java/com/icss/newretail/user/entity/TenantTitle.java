package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.icss.newretail.model.TenantTitleDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 平台租户标题
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_tenant_title")
@ApiModel(value = "TenantTitle对象", description = "平台租户标题")
public class TenantTitle implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "uuid")
  @TableId("uuid")
  private String uuid;

  @ApiModelProperty(value = "租户ID")
  @TableField("tenant_id")
  private String tenantId;

  @ApiModelProperty(value = "标题文字")
  @TableField("login_right_note")
  private String loginRightNote;

  @ApiModelProperty(value = "标题图标")
  @TableField("login_right_img")
  private String loginRightImg;

  @ApiModelProperty(value = "展示顺序")
  @TableField("order_no")
  private Integer orderNo;

  @ApiModelProperty(value = "状态：1启用，0停用")
  @TableField("status")
  private Integer status;

  @ApiModelProperty(value = "创建人")
  @TableField("create_user")
  private String createUser;

  @ApiModelProperty(value = "创建时间")
  @TableField("create_time")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "更新人")
  @TableField("update_user")
  private String updateUser;

  @ApiModelProperty(value = "更新时间")
  @TableField("update_time")
  private LocalDateTime updateTime;

  public TenantTitle() {
  }

  public TenantTitle(TenantTitleDTO dto) {
    this.uuid = dto.getUuid();
    this.tenantId = dto.getTenantId();
    this.loginRightNote = dto.getLoginRightNote();
    this.loginRightImg = dto.getLoginRightImg();
    this.orderNo = dto.getOrderNo();
    this.status = dto.getStatus() == null ? 1 : dto.getStatus();
    this.updateTime = LocalDateTime.now();
  }
}
