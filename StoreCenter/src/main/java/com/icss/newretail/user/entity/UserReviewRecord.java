package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 审核记录表
 * </p>
 *
 * @author jc
 * @since 2020-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_review_record")
@ApiModel(value="UserReviewRecord对象", description="审核记录表")
public class UserReviewRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "审核id")
    @TableField("review_id")
    private String reviewId;

    @ApiModelProperty(value = "门店组织机构")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "审核状态（1-初审2-复审3-初审不通过4-复审不通过5-复审通过）")
    @TableField("review_status")
    private Integer reviewStatus;

    @ApiModelProperty(value = "审核人")
    @TableField("review_user")
    private String reviewUser;

    @ApiModelProperty(value = "审核意见")
    @TableField("review_option")
    private String reviewOption;

    @ApiModelProperty(value = "审核时间")
    @TableField("review_time")
    private LocalDateTime reviewTime;


}
