package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商家端小程序权限表
 * </p>
 *
 * @author Mc_Jc
 * @since 2020-04-19
 */

@Data
public class UserAuthorityRelationReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="门店组织机构编码")
	private String orgSeq;

	@ApiModelProperty(value = "权限id")
	private String authorityId;

	@ApiModelProperty(value = "人员集合")
	private List<String> userList;

}
