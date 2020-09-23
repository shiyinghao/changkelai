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
 * 问题反馈表
 * </p>
 *
 * @author syh
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_feedback")
@ApiModel(value="Feedback对象", description="问题反馈表")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "问题编号")
    @TableField("problem_seq")
    private String problemSeq;

    @ApiModelProperty(value = "问题分类1、银行相关问题 2、其他软件问题 3、硬件相关问题")
    @TableField("question_type")
    private Integer questionType;

    @ApiModelProperty(value = "问题主题")
    @TableField("question_topic")
    private String questionTopic;

    @ApiModelProperty(value = "问题描述")
    @TableField("question_description")
    private String questionDescription;

    @ApiModelProperty(value = "门店编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "处理进度 1、未处理 2、已处理")
    @TableField("processing_progress")
    private Integer processingProgress;

    @ApiModelProperty(value = "银行反馈")
    @TableField("feedback")
    private String feedback;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

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


}
