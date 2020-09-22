package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页查询参数
 * 
 * @author wangjie
 * @date Apr 15, 2019
 */
@Data
public class UserInfoParamDTO {

    @ApiModelProperty(value = "门店编码")
    private List<String> orgList;

    @ApiModelProperty(value = "用户姓名")
    private String realName;

}
