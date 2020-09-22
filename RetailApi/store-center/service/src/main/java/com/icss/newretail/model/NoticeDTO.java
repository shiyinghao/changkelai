package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知公告
 * 
 * @author zhangzhijia
 * @date Jul 11, 2019
 */
@Data
public class NoticeDTO {

  @ApiModelProperty(value = "uuid")
  private Integer uuid;

  @ApiModelProperty(value = "所属组织编码")
  private String orgSeq;

  @ApiModelProperty(value = "通知标题")
  private String title;

  @ApiModelProperty(value = "通知内容")
  private String content;

  @ApiModelProperty(value = "发布人")
  private String publisher;

  @ApiModelProperty(value = "发布时间")
  private LocalDateTime publishTime;

  @ApiModelProperty(value = "发布状态:1/0")
  private Integer status;

  @ApiModelProperty(value = "创建人")
  private String createUser;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "修改人")
  private String updateUser;

  @ApiModelProperty(value = "修改时间")
  private LocalDateTime updateTime;

}
