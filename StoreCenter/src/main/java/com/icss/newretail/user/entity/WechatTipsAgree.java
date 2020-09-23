package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_wechat_tips_agree")
@ApiModel(value="WechatTipsAgree对象", description="微信应用使用条款同意记录")
public class WechatTipsAgree implements Serializable {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "uuid")
  @TableId(value = "uuid",type = IdType.AUTO)
  private Integer uuid;

  @ApiModelProperty(value = "条款ID")
  @TableField("tips_id")
  private Integer tipsId;

  @ApiModelProperty(value = "条款内容")
  @TableField("content")
  private String content;

  @ApiModelProperty(value = "条款版本")
  @TableField("version")
  private String version;

  @ApiModelProperty(value = "会员openID")
  @TableField("open_id")
  private String openId;

  @ApiModelProperty(value = "是否同意（Y/N）")
  @TableField("agree")
  private String agree;

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

  @ApiModelProperty(value = "租户ID")
  @TableField("tenant_id")
  private String tenantId;

  public WechatTipsAgree(){};

  public WechatTipsAgree(WechatTips tips,String agree,String openId){
    this.agree = agree;
    this.openId = openId;
    this.tipsId = tips.getUuid();
    this.content = tips.getContent();
    this.version =tips.getVersion();
  }

}
