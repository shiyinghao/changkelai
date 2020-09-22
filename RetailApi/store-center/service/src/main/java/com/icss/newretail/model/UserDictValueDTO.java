package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author syh
 * @since 2020-03-30
 */
@Data
public class UserDictValueDTO implements Serializable {


    @ApiModelProperty(value = "主键id")
    private String uuid;

    @ApiModelProperty(value = "字典主表id")
    @TableField("dict_id")
    private String dictId;

    @ApiModelProperty(value = "字典项id")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "字典项值")
    @TableField("value")
    private String value;

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
