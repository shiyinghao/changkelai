package com.icss.newretail.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商家端小程序权限表
 * </p>
 *
 * @author Mc_Jc
 * @since 2020-04-19
 */
@Data
public class ShopAppletAuthortiryDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "权限名称")
    private String authorityName;

    @ApiModelProperty(value = "权限路径")
    private String authorityUri;

    @ApiModelProperty(value = "首页展示(1-是0-否)")
    private Integer isShow;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "权限描述")
    private String description;

    @ApiModelProperty(value = "状态(1-启用0-禁用)")
    private Integer status;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    public Boolean addCheckArgs(){
    if(authorityName==null||authorityName.equals("")||authorityUri==null||authorityUri.equals("")
        ||icon==null||icon.equals("")||sort==null||sort.equals("")
    ){
        return true;
    }
    return false;
    }

    public Boolean updateCheckArgs(){
        if(uuid==null||uuid.equals("")){
            return true;
        }
        return  false;
    }
}
