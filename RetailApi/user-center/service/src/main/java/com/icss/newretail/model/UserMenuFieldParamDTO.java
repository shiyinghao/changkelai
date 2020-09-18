package com.icss.newretail.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserMenuFieldParamDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户编码")
    private String userId;
    
    @ApiModelProperty(value = "菜单编码")
    private String menuCode;

    @ApiModelProperty(value = "菜单字段主键")
    private String fieldId;

    @ApiModelProperty(value = "字段列表")
    private List<String> fieldList;

}
