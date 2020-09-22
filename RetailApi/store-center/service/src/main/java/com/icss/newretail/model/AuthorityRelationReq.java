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
public class AuthorityRelationReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门店组织机构编码")
    private String orgSeq;

    @ApiModelProperty(value = "用户id")
    private String userId;

}
