package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 反馈图片
 * </p>
 *
 * @author syh
 * @since 2020-05-25
 */
@Data
public class FeedbackPicturesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图片ID")
    private String uuid;

    @ApiModelProperty(value = "反馈表ID")
    @TableField("feedback_id")
    private String feedbackId;

    @ApiModelProperty(value = "图片URL")
    @TableField("pic_url")
    private String picUrl;

    @ApiModelProperty(value = "图片位置")
    @TableField("pic_location")
    private String picLocation;

    @ApiModelProperty(value = "图片展示序号")
    @TableField("pic_no")
    private String picNo;

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
