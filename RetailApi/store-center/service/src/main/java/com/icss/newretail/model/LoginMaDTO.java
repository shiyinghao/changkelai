package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 登录用户session信息
 * 
 * @author zhangzhijia
 * @date Apr 16, 2019
 */
@Data
public class LoginMaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "小程序ID")
	private String corpId;
	
    @ApiModelProperty(value = "openId")
    private String openId;

    @ApiModelProperty(value = "登录状态 0 退出 1 登录")
    private Integer flag;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "授权令牌")
    private String token;// token信息
    
    @ApiModelProperty(value = "授权令牌有效期限")
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tokenExpTime;// token信息
    
    @ApiModelProperty(value = "登陆时间")
    private Date currLoginTime; // 当前登陆时间
    
    @ApiModelProperty(value = "门店ID")
    private String orgSeq;

    @ApiModelProperty(value = "手机号")
    private String cellPhoneNo;

    @ApiModelProperty(value = "登录状态")
    private int status;

    @ApiModelProperty(value = "店主的门店名称列表集合")
    private List<UserStoreNameDTO> userStoreInfoList;

    @ApiModelProperty(value = "用户组织机构关联表中的用户类型集合")
    private List<UserOrgRelationDTO> userTypeList;


    @ApiModelProperty(value = "userinfo表详细信息")
    private ShopUserInfoDTO shopUserInfoDTO ;
}
