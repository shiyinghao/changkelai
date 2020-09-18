/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.api.user;

import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.WechatUserAuthDTO;
import com.icss.newretail.service.user.WechatUserAuthService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 微信相关配置服务
 * 
 * @author zhangzhijia
 * @date May 10, 2019
 */
@RestSchema(schemaId = "wechatUserAuth")
@RequestMapping(path = "/v1/wechatUserAuth")
public class WechatUserAuthApi {

    @Autowired
    private WechatUserAuthService wechatUserAuthService;

    /**
	 * 根据人员编码获取认证信息
	 */
    @GetMapping(path = "getWechatUserAuthByOpenId")
	public ResponseResult<WechatUserAuthDTO> getWechatUserAuthByOpenId(String authType){
    	return wechatUserAuthService.getWechatUserAuthByOpenId(authType);
	}
	/**
	 * 根据人员编码获取认证信息列表
	 */
    @PostMapping(path = "getWechatUserAuthListByOpenId")
	public ResponseRecords<WechatUserAuthDTO> getWechatUserAuthListByOpenId(@RequestBody(required = false) List<String> authTypes){
		return wechatUserAuthService.getWechatUserAuthListByOpenId(authTypes);
	}
	/**
	 * 效验当前微信人员是否认证过任一角色
	 */
	@PostMapping(path = "checkAuthAnyOfUser")
	public ResponseResult<Integer> checkAuthAnyOfUser(@RequestBody(required = false) List<String> authTypes){
		return wechatUserAuthService.checkAuthAnyOfUser(authTypes);
	}
	/**
	 * 根据认证账号获取认证信息
	 */
	@GetMapping(path = "getWechatUserAuthByAuthAccount")
	public ResponseResult<WechatUserAuthDTO> getWechatUserAuthByAuthAccount(String authType, String authAccount){
		return wechatUserAuthService.getWechatUserAuthByAuthAccount(authType, authAccount);
	}
	/**
	 * 根据认证账号及openId获取认证信息
	 */
	@GetMapping(path = "getWechatUserAuthByAuthAccountOpenId")
	public ResponseResult<WechatUserAuthDTO> getWechatUserAuthByAuthAccountOpenId(String authType, String authAccount){
		return wechatUserAuthService.getWechatUserAuthByAuthAccountOpenId(authType, authAccount);
	}
	/**
	 * 新增
	 * @author
	 * @createtime
	 */
	@PostMapping(path = "insertWechatUserAuth")
	public ResponseBase insertWechatUserAuth(@RequestBody WechatUserAuthDTO wechatUserAuthDTO) {
		return wechatUserAuthService.insertWechatUserAuth(wechatUserAuthDTO);
	}
	/**
	 * 修改
	 * @author
	 * @createtime
	 */
	@PutMapping(path = "updateWechatUserAuth")
	public ResponseBase updateWechatUserAuth(@RequestBody WechatUserAuthDTO wechatUserAuthDTO) {
		return wechatUserAuthService.updateWechatUserAuth(wechatUserAuthDTO);
	}
	/**
	 * 删除
	 * @author
	 * @createtime
	 */
	@DeleteMapping(path = "deleteWechatUserAuth")
	public ResponseBase deleteWechatUserAuth(String authUuid) {
		return wechatUserAuthService.deleteWechatUserAuth(authUuid);
	}
	/**
	 * 取消绑定
	 */
	@PutMapping(path = "updateWechatUserAuthStatus")
	public ResponseBase updateWechatUserAuthStatus(String authType){
		return wechatUserAuthService.updateWechatUserAuthStatus(authType);
	}
}
