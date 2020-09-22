package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * @author yanghu
 * @date  2020.04.14
 */
@Data
public class StoreBonuspointReq {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门店组织机构编码")
    @NotEmpty(message = "组织不能为空")
    private String orgSeq;

    /**
     * 会员购买商品  --增加会员积分之后  增加门店积分   传入的参数是  消费金额
     */
    @ApiModelProperty(value = "积分")
    @NotEmpty(message = "分数不能为空")
    private BigDecimal score;

    @ApiModelProperty(value = "绑定门店组织机构编码")
    private String BindOrgSeq;

    @ApiModelProperty(value = "积分关联订单id----可以不传")
    private String orderId;

    @ApiModelProperty(value = "积分来源类型--1分享所得---2用户消费所得")
    @NotEmpty(message = "积分来源不能为空")
    private Integer sourceType;



}
