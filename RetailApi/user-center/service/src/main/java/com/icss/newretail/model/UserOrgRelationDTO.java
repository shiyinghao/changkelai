package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户组织机构关联表
 * </p>
 *
 * @author jc
 * @since 2020-04-13
 */
@Data
public class UserOrgRelationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String uuid;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "组织机构编码")
    private String orgSeq;

    @ApiModelProperty(value = "用户类型(1集团人员2战区人员3基地业务员4店主5店长6店员)")
    private Integer userType;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;


}
