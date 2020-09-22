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
 * 审核记录表
 * </p>
 *
 * @author jc
 * @since 2020-04-15
 */
@Data
public class UserReviewRecordDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String uuid;

    @ApiModelProperty(value = "审核id")
    private String reviewId;

    @ApiModelProperty(value = "门店组织机构")
    private String orgSeq;

    @ApiModelProperty(value = "审核状态（1-初审2-复审3-初审不通过4-复审不通过5-复审通过）")
    private Integer reviewStatus;

    @ApiModelProperty(value = "审核人")
    private String reviewUser;

    @ApiModelProperty(value = "审核意见")
    private String reviewOption;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime reviewTime;


}
