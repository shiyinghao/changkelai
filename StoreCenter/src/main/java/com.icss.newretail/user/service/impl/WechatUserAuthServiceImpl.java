package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.WechatUserAuthDTO;
import com.icss.newretail.service.user.WechatUserAuthService;
import com.icss.newretail.user.dao.WechatUserAuthMapper;
import com.icss.newretail.user.entity.WechatUserAuth;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
@Service  
@Transactional
public class WechatUserAuthServiceImpl implements WechatUserAuthService {
	@Autowired
	private WechatUserAuthMapper wechatUserAuthMapper;

	/**
	 * 根据人员编码获取认证信息
	 */
	public ResponseResult<WechatUserAuthDTO> getWechatUserAuthByOpenId(String authType){
		String tenantId = JwtTokenUtil.currTenant();
		String openId = JwtTokenUtil.currUser();
		if (StringUtils.isEmpty(openId)) {
			throw new RuntimeException();
		}
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("authType", authType);
		query.put("tenantId", tenantId);
		query.put("openId", openId);
		WechatUserAuthDTO wechatInfoDTO = wechatUserAuthMapper.getWechatUserAuthByOpenId(query);
		ResponseResult<WechatUserAuthDTO> responseResult = new ResponseResult<WechatUserAuthDTO>();
		responseResult.setCode(1);
		responseResult.setResult(wechatInfoDTO);
		return responseResult;
	}
	/**
	 * 根据人员编码获取认证信息列表
	 */
	public ResponseRecords<WechatUserAuthDTO> getWechatUserAuthListByOpenId(List<String> authTypes){
		String tenantId = JwtTokenUtil.currTenant();
		String openId = JwtTokenUtil.currUser();
		if (StringUtils.isEmpty(openId)) {
			throw new RuntimeException();
		}
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("authTypes", new StringBuilder().append("'").append(StringUtils.join(authTypes.toArray(), "','")).append("'").toString());
		query.put("tenantId", tenantId);
		query.put("openId", openId);
		List<WechatUserAuthDTO> list = wechatUserAuthMapper.getWechatUserAuthListByOpenId(query);
		ResponseRecords<WechatUserAuthDTO> responseResult = new ResponseRecords<WechatUserAuthDTO>();
		responseResult.setCode(1);
		responseResult.setRecords(list);
		return responseResult;
	}
	/**
	 * 效验当前微信人员是否认证过任一角色
	 */
	public ResponseResult<Integer> checkAuthAnyOfUser(List<String> authTypes){
		String tenantId = JwtTokenUtil.currTenant();
		String openId = JwtTokenUtil.currUser();
		if (StringUtils.isEmpty(openId)) {
			throw new RuntimeException();
		}
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("authTypes", new StringBuilder().append("'").append(StringUtils.join(authTypes.toArray(), "','")).append("'").toString());
		query.put("tenantId", tenantId);
		query.put("openId", openId);
		Integer result = wechatUserAuthMapper.checkAuthAnyOfUser(query);
		ResponseResult<Integer> responseResult = new ResponseResult<Integer>();
		responseResult.setCode(1);
		responseResult.setResult(result);
		return responseResult;
	}
	/**
	 * 根据认证账号获取认证信息
	 */
	public ResponseResult<WechatUserAuthDTO> getWechatUserAuthByAuthAccount(String authType, String authAccount){
		String tenantId = JwtTokenUtil.currTenant();
		String openId = JwtTokenUtil.currUser();
		if (StringUtils.isEmpty(openId)) {
			throw new RuntimeException();
		}
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("authType", authType);
		query.put("tenantId", tenantId);
		query.put("authAccount", authAccount);
		WechatUserAuthDTO wechatUserAuthDTO = wechatUserAuthMapper.getWechatUserAuthByAuthAccount(query);
		ResponseResult<WechatUserAuthDTO> responseResult = new ResponseResult<WechatUserAuthDTO>();
		responseResult.setCode(1);
		responseResult.setResult(wechatUserAuthDTO);
		return responseResult;
	}
	/**
	 * 根据认证账号及openId获取认证信息
	 */
	public ResponseResult<WechatUserAuthDTO> getWechatUserAuthByAuthAccountOpenId(String authType, String authAccount){
		String tenantId = JwtTokenUtil.currTenant();
		String openId = JwtTokenUtil.currUser();
		if (StringUtils.isEmpty(openId)) {
			throw new RuntimeException();
		}
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("authType", authType);
		query.put("tenantId", tenantId);
		query.put("openId", openId);
		query.put("authAccount", authAccount);
		WechatUserAuthDTO wechatUserAuthDTO = wechatUserAuthMapper.getWechatUserAuthByAuthAccountOpenId(query);
		ResponseResult<WechatUserAuthDTO> responseResult = new ResponseResult<WechatUserAuthDTO>();
		responseResult.setCode(1);
		responseResult.setResult(wechatUserAuthDTO);
		return responseResult;
	}
	/**
	 * 新增
	 * @author
	 * @createtime
	 */
	public ResponseBase insertWechatUserAuth(WechatUserAuthDTO wechatUserAuthDTO) {
		String tenantId = JwtTokenUtil.currTenant();
		String openId = JwtTokenUtil.currUser();
		if (StringUtils.isEmpty(openId)) {
			throw new RuntimeException();
		}
		WechatUserAuth wechatUserAuth = Object2ObjectUtil.parseObject(wechatUserAuthDTO, WechatUserAuth.class);
		wechatUserAuth.setAuthUuid(UUID.randomUUID().toString());
		wechatUserAuth.setTenantId(tenantId);
		wechatUserAuth.setOpenId(openId);
		wechatUserAuth.setStatus(1);
		wechatUserAuth.setCreateUser(openId);
		wechatUserAuth.setCreateTime(LocalDateTime.now());
		wechatUserAuthMapper.insert(wechatUserAuth);
		return new ResponseBase(1, "成功");
	}
	/**
	 * 修改
	 * @author
	 * @createtime
	 */
	public ResponseBase updateWechatUserAuth(WechatUserAuthDTO wechatUserAuthDTO) {
		String tenantId = JwtTokenUtil.currTenant();
		String openId = JwtTokenUtil.currUser();
		if (StringUtils.isEmpty(openId)) {
			throw new RuntimeException();
		}
		WechatUserAuth wechatUserAuth = Object2ObjectUtil.parseObject(wechatUserAuthDTO,WechatUserAuth.class);
		wechatUserAuth.setTenantId(tenantId);
		wechatUserAuth.setOpenId(openId);
		wechatUserAuth.setUpdateUser(openId);
		wechatUserAuth.setUpdateTime(LocalDateTime.now());
		wechatUserAuthMapper.update(wechatUserAuth,new UpdateWrapper<WechatUserAuth>().eq("status",1)
				.eq("tenant_id",wechatUserAuth.getTenantId()).eq("open_id",wechatUserAuth.getOpenId())
				.eq("auth_type",wechatUserAuth.getAuthType()));
		return new ResponseBase(1, "成功");
	}
	/**
	 * 删除
	 * @author
	 * @createtime
	 */
	public ResponseBase deleteWechatUserAuth(String authUuid) {
		WechatUserAuth wechatUserAuth = new WechatUserAuth();
		wechatUserAuth.setAuthUuid(authUuid);
		wechatUserAuthMapper.deleteById(wechatUserAuth);
		return new ResponseBase(1, "成功");
	}
	/**
	 * 取消绑定
	 */
	public ResponseBase updateWechatUserAuthStatus(String authType){
		String tenantId = JwtTokenUtil.currTenant();
		String openId = JwtTokenUtil.currUser();
		if (StringUtils.isEmpty(openId)) {
			throw new RuntimeException();
		}
		WechatUserAuth wechatUserAuth = new WechatUserAuth();
		wechatUserAuth.setTenantId(tenantId);
		wechatUserAuth.setOpenId(openId);
		wechatUserAuth.setAuthType(authType);
		wechatUserAuth.setStatus(0);
		wechatUserAuth.setUpdateUser(openId);
		wechatUserAuth.setUpdateTime(LocalDateTime.now());
		wechatUserAuthMapper.update(wechatUserAuth,new UpdateWrapper<WechatUserAuth>().eq("status",1)
				.eq("tenant_id",wechatUserAuth.getTenantId()).eq("open_id",wechatUserAuth.getOpenId())
				.eq("auth_type",wechatUserAuth.getAuthType()));
		return new ResponseBase(1, "成功");
	}
}

