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

/**
 * <p>
 * 门店使用时长次数记录表
 * </p>
 *
 * @author jc
 * @since 2020-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_use_record")
@ApiModel(value="StoreUseRecord对象", description="门店使用时长次数记录表")
public class StoreUseRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "uuid", type = IdType.AUTO)
    private Long uuid;

    @ApiModelProperty(value = "门店编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "使用时长(分钟)")
    @TableField("use_time")
    private Long useTime;

    @ApiModelProperty(value = "启动次数")
    @TableField("start_times")
    private Integer startTimes;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
