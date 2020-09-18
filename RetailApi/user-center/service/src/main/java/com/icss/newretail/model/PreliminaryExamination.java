package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 
 * @author Ydt
 *	@data	2020年3月25日
 */
@Data
public class PreliminaryExamination implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private String uuid;

	@ApiModelProperty(value = "审核意见")
	private String reviewOpinion;

	@ApiModelProperty(value = "复核意见")
	private String finalReviewOpinion;
	
    @ApiModelProperty(value = "审核状态（1通过，2不通过）")
    private Integer reviewStatus;


}
