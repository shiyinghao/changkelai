package com.icss.newretail.user.entity;

import java.math.BigDecimal;
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
 * 门店积分表
 * </p>
 *
 * @author jc
 * @since 2020-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_bonuspoint")
@ApiModel(value="StoreBonuspoint对象", description="门店积分表")
public class StoreBonuspoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "门店组织机构编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "当前积分")
    @TableField("current_score")
    private BigDecimal currentScore;

    @ApiModelProperty(value = "总积分")
    @TableField("total_score")
    private BigDecimal totalScore;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("creater_user")
    private String createrUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private String updateUser;


}
