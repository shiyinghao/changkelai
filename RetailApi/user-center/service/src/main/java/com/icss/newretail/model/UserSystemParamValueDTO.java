package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 系统配置
 *
 * @author wy
 * @date Apr 17, 2020
 */
@Data
public class UserSystemParamValueDTO {

    @ApiModelProperty(value = "主键ID")
    private String uuid;

    @ApiModelProperty(value = "参数id")
    private String paramId;

    @ApiModelProperty(value = "参数项编码")
    private String code;

    @ApiModelProperty(value = "参数项名称")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
