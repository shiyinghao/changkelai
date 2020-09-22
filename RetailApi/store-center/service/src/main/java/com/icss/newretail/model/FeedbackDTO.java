package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeedbackDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    @ApiModelProperty(value = "问题编号")
    private String problemSeq;

    @ApiModelProperty(value = "工行商户号")
    private String merchantNo;

    @ApiModelProperty(value = "商户名称")
    private String merchantName;

    @ApiModelProperty(value = "联系人姓名")
    private String contactPerson;

    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;

    @ApiModelProperty(value = "问题分类1、银行相关问题 2、其他软件问题 3、硬件相关问题")
    private Integer questionType;

    @ApiModelProperty(value = "问题主题")
    private String questionTopic;

    @ApiModelProperty(value = "问题描述")
    private String questionDescription;

    @ApiModelProperty(value = "门店编码")
    private String orgSeq;

    @ApiModelProperty(value = "处理进度 1、未处理 2、已处理")
    private Integer processingProgress;

    @ApiModelProperty(value = "银行反馈")
    private String feedback;

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

    private List<FeedbackPicturesDTO> feedbackPictures;

    private UserStoreInfoDTO userStoreInfoDTO;
}
