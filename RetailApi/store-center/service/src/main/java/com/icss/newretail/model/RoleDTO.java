package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleDTO {
    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "角色编码")
    private String roleReq;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色类型（一些特殊的，代码特殊处理的角色类型，例如超级用户类型。）")
    private Integer roleType;

    @ApiModelProperty(value = "角色说明")
    private String roleDesc;

    @ApiModelProperty(value = "状态（启用/停用）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private Date upateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "备注")
    private String comments;

    @ApiModelProperty(value = "可分配的菜单menuIds")
    private List<String> menuIds;

}
