package com.icss.newretail.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserMenuFieldDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private String uuid;

    @ApiModelProperty(value = "菜单编码")
    private String menuCode;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "字段编码")
    private String fieldCode;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "状态(1-启用0-弃用)")
    private Integer status;
    
    @ApiModelProperty(value = "是否显示(1-是0-否)")
    private Integer isShow;
    
    @ApiModelProperty(value = "关联关系主键")
    private String relationId;
    
    @ApiModelProperty(value = "关联人")
    private String userId;

}
