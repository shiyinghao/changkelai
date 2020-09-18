package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.icss.newretail.model.NoticeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 通知公告表
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_notice")
@ApiModel(value="Notice对象", description="通知公告表")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "uuid")
    @TableId(value = "uuid", type = IdType.AUTO)
    private Integer uuid;

    @ApiModelProperty(value = "所属组织编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "通知标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "通知内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "发布人")
    @TableField("publisher")
    private String publisher;

    @ApiModelProperty(value = "发布时间")
    @TableField("publish_time")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "发布状态:1/0")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    @TableField("update_user")
    private String updateUser;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;


    public Notice(NoticeDTO noticeDTO) {
        this.uuid = noticeDTO.getUuid();
        this.orgSeq = noticeDTO.getOrgSeq();
        this.title = noticeDTO.getTitle();
        this.content = noticeDTO.getContent();
        this.publisher = noticeDTO.getPublisher();
        this.publishTime = noticeDTO.getPublishTime();
        this.status = noticeDTO.getStatus();
        this.updateTime = LocalDateTime.now();
    }
}
