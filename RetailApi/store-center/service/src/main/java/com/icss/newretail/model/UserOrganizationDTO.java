package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 组织机构
 * </p>
 *
 * @author jc
 * @since 2020-03-17
 */
@Data
public class UserOrganizationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织编码")
    private String id;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "上级组织编码")
    private String upOrgId;

    @ApiModelProperty(value = "级别")
    private String level;

    @ApiModelProperty(value = "组织类型")
    private int orgType;

    @ApiModelProperty(value = "状态(停用0/启用1)")
    private Integer status;

    @ApiModelProperty(value = "组织显示顺序")
    private Integer orgNo;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;


}
