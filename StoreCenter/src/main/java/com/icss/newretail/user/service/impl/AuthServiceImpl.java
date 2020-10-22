package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.icss.newretail.model.LoginInRequest;
import com.icss.newretail.model.LoginRequest;
import com.icss.newretail.model.LoginUserDTO;
import com.icss.newretail.model.LoginUserPasswordDTO;
import com.icss.newretail.model.OrgList;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.RoleList;
import com.icss.newretail.model.UmsParameter;
import com.icss.newretail.service.user.AuthService;
import com.icss.newretail.user.dao.LoginMapper;
import com.icss.newretail.user.dao.UserInfoMapper;
import com.icss.newretail.user.dao.UserLoginInfoMapper;
import com.icss.newretail.user.entity.UserInfo;
import com.icss.newretail.user.entity.UserLoginInfo;
import com.icss.newretail.user.entity.UserStoreInfo;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.apache.servicecomb.swagger.invocation.context.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	private static final String CODE = "1011012092218";

	@Autowired
	private LoginMapper loginMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserLoginInfoMapper userLoginInfoMapper;

	@Autowired
	private StoreMapper storeMapper;

	@Override
	public String generateVerifyCode() {
		return null;
	}

	@Override
	public Integer verifyVerifyCode() {
		return null;
	}

	private static final String FLAG = "608bdfc0-f20b-4b59-90d1-085bbd1b6159";

	/**
	 * 登录 锁定逻辑：密码连续输错5次则锁定账户10分钟，非锁定时间密码输对了清零密码输错次数
	 */
	@Override
	@Transactional
	public ResponseResult<LoginUserDTO> loginIn(LoginInRequest loginInRequest) {
		ResponseResult<LoginUserDTO> responseResult = new ResponseResult<LoginUserDTO>();
		loginInRequest.setPassword(MD5Util.toMD5(loginInRequest.getPassword()));// 对密码进行md5加密
		LoginUserDTO loginUser = loginMapper.loginIn(loginInRequest);// 根据账号密码查询数据库
		if (loginUser != null && !StringUtils.isEmpty(loginUser.getUserId())) {
			String userId = loginUser.getUserId();
			// 查询部门
			List<OrgList> orgList = loginMapper.findOrgByUserId(userId);
			// 查询角色
			List<RoleList> roleList = loginMapper.findRoleByUserId(userId);
			loginUser.setOrgList(orgList);
			loginUser.setRoleList(roleList);
			Map<String, Object> map = new HashMap<>();
			map.put("userId", userId);
			map.put("orgSeq", loginUser.getOrgSeq());
			//map.put("userName", loginUser.getUserName());
			//map.put("realName", loginUser.getRealName());
			// map.put("org",orgList);
			// map.put("role",roleList);
			String token = JwtTokenUtil.generateToken(map);// 生成JWTtoken
			loginUser.setToken(token);
			loginUser.setTokenExpTime(JwtTokenUtil.getExpirationDateFromToken(token));
			loginUser.setCurrLoginTime(JwtTokenUtil.getIssuedAtDateFromToken(token));
			UserInfo userInfo = userInfoMapper.selectById(loginUser.getUserId());// 查询到数据，查询用户信息
			userInfo.setLoginTime(loginUser.getCurrLoginTime());
			loginMapper.updateLoginTime(userInfo); // 修改登录时间
			// 返回结果
			responseResult.setCode(1);
			responseResult.setResult(loginUser);
			responseResult.setMessage("登录成功");
			log.info("用户登录成功！账号:{} 登录类型:{} ", loginInRequest.getTel());
		} else {
			responseResult.setCode(0).setMessage("账号不存在或密码错误");
		}
		return responseResult;
	}

	@Override
	@Transactional
	public ResponseResult<LoginUserDTO> login(LoginRequest loginRequest) {
		ResponseResult<LoginUserDTO> responseResult = new ResponseResult<>();
		loginRequest.setPassword(MD5Util.toMD5(loginRequest.getPassword()));
		LoginUserDTO loginUser = loginMapper.login(loginRequest);
		if (loginUser != null && !StringUtils.isEmpty(loginUser.getUserId())) {
			List<OrgList> list = new ArrayList<>();
			UserStoreInfo userStoreInfo = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("org_seq",loginUser.getOrgSeq()));
			OrgList orgList = new OrgList().setOrgSeq(loginUser.getOrgSeq()).setOrgName(loginUser.getOrgName()).
					setUserType(Integer.parseInt(loginUser.getUserType())).setOrgType(loginUser.getOrgType());
			if (userStoreInfo != null){
				orgList.setDoorwayPic(userStoreInfo.getDoorwayPic())
						.setStoreAddress(userStoreInfo.getStoreAddressDetail())
						.setStoreManagerPhone(userStoreInfo.getStoreManagerPhone());
			}
			list.add(orgList);
			List<RoleList> roleList = loginMapper.findRoleByUserId(loginUser.getUserId());
			loginUser.setOrgList(list);
			loginUser.setRoleList(roleList);
			Map<String, Object> map = new HashMap<>();
			map.put("userId", loginUser.getUserId());
			map.put("orgSeq", loginUser.getOrgSeq());
			map.put("userName", loginUser.getUserName());
			map.put("realName", loginUser.getRealName());
			String token = JwtTokenUtil.generateToken(map);
			loginUser.setToken(token);
			loginUser.setTokenExpTime(JwtTokenUtil.getExpirationDateFromToken(token));
			loginUser.setCurrLoginTime(new Date());
			UserLoginInfo userLoginInfo = new UserLoginInfo().setLoginTime(LocalDateTime.now());
			userLoginInfoMapper.update(userLoginInfo, new UpdateWrapper<UserLoginInfo>().eq("auth_code", loginRequest.getAuthId()));
			responseResult.setCode(1);
			responseResult.setResult(loginUser);
			responseResult.setMessage("登录成功");
			log.info("用户登录成功！账号:{} ", loginRequest.getAuthId());
		} else {
			responseResult.setCode(0).setMessage("账号不存在或密码错误");
		}
		return responseResult;
	}


	@Override
	public ResponseResult<LoginUserDTO> loginWithVerifyCode(String authType, String authId, String password,
	                                                        String verifyCode) {
		return null;
	}

	/**
	 * 退出
	 */
	@Override
	public ResponseBase logout(String token) {
		// 两种方式获取token
		/*
		 * 第一种在API层用@RequestHeader String token获取token。
		 * 好处是使用网关方式和不使用网关方式都能获取到token 弊端是需要将token传递到service层，可能需要修改接口参数
		 */
		System.out.println("token: " + token);
		/*
		 * 第二种用ContextUtils获取。 好处是代码侵入性小 弊端是只支持使用网关方式访问restful接口
		 * 原理：通过网关方式访问restful接口时， 网关会把token放进context中，传递到后方。
		 *
		 * 建议用这种方式获取token
		 */
		String token2 = ContextUtils.getInvocationContext().getContext("token");
		System.out.println("token2: " + token2);
		String subject = JwtTokenUtil.getClaimFromToken(token2).getSubject();
		if (!StringUtils.isEmpty(subject)) {
			String[] temp = subject.split(",");
			String userId = temp[0];
			String tenantId = temp[1];
			System.out.println("userId: " + userId);
			System.out.println("tenantId: " + tenantId);
		}
		return null;
	}

	/**
	 * 调用微服务示例
	 */
	@RpcReference(microserviceName = "user-service", schemaId = "auth")
	private AuthService authService;

	@SuppressWarnings("unchecked")
	public ResponseBase callApi() {
		RestTemplate restTemplate = RestTemplateBuilder.create();
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setAuthId("1");
		loginRequest.setPassword("test");
		// rpc方式
		ResponseResult<LoginUserDTO> aBase2 = authService.login(loginRequest);
		System.out.println(aBase2);
		// restTemplate方式
		String serviceName = "user-service";
		ResponseResult<LoginUserDTO> aBase1 = (ResponseResult<LoginUserDTO>) restTemplate
				.postForObject("cse://" + serviceName + "/v1/auth/login", loginRequest, ResponseResult.class);
		System.out.println(aBase1);
		return null;
	}

	@Override
	public ResponseBase checkPassword(LoginUserPasswordDTO para) {
		ResponseBase base = new ResponseBase();
		try {
			String password = MD5Util.toMD5(para.getPassword());// 对密码进行md5加密
			System.out.println("--------------->" + password);
			String userId = JwtTokenUtil.currUserInfo().get("userId").toString();
			String orgSeq = JwtTokenUtil.currUserInfo().get("orgSeq").toString();// 门店编码
			// 根据userId查询所属门店的 店长密码
			String[] user_type = {"4", "5"};// 4店主5是门店的店长
			List<UserInfo> userInfoList = userInfoMapper
					.selectList(new QueryWrapper<UserInfo>().in("user_type", user_type).eq("org_seq", orgSeq).eq("status", 1));

			if (!CollectionUtils.isEmpty(userInfoList)) {
				for (UserInfo ui : userInfoList) {
					if (password.equals(ui.getPassword())) {
						base.setCode(1).setMessage("密码校验通过");
						break;
					} else {
						base.setCode(0).setMessage("密码校验不通过");
						continue;
					}
				}
				return base;

			}
			base.setCode(0).setMessage("店长信息为null");
		} catch (Exception ex) {
			log.error("AuthServiceImpl|checkPassword->" + ex.getLocalizedMessage());
			base.setMessage(ex.getMessage());
			base.setCode(0);
		}
		return base;
	}

	@Override
	public ResponseResult<LoginUserDTO> telLogin(LoginUserPasswordDTO para) {
		ResponseResult<LoginUserDTO> responseResult = new ResponseResult<>();
		if (!FLAG.equals(para.getFlag())) {
			responseResult.setCode(0).setMessage("巡店系统识别码错误");
			return responseResult;
		}
		LoginUserDTO loginUser = loginMapper.telLogin(para);
		if (loginUser != null && !StringUtils.isEmpty(loginUser.getUserId())) {
			List<OrgList> list = new ArrayList<>();
			OrgList orgList = new OrgList().setOrgSeq(loginUser.getOrgSeq()).setOrgName(loginUser.getOrgName()).
					setUserType(Integer.parseInt(loginUser.getUserType())).setOrgType(loginUser.getOrgType());
			list.add(orgList);
			List<RoleList> roleList = loginMapper.findRoleByUserId(loginUser.getUserId());
			loginUser.setOrgList(list);
			loginUser.setRoleList(roleList);
			Map<String, Object> map = new HashMap<>();
			map.put("userId", loginUser.getUserId());
			map.put("orgSeq", loginUser.getOrgSeq());
			String token = JwtTokenUtil.generateToken(map);
			loginUser.setToken(token);
			loginUser.setTokenExpTime(JwtTokenUtil.getExpirationDateFromToken(token));
			//UserLoginInfo userLoginInfo = new UserLoginInfo().setAuthCode(para.getAuthId());
			//userLoginInfoMapper.update(userLoginInfo,new UpdateWrapper<UserLoginInfo>().eq("auth_code",para.getAuthId()));
			responseResult.setCode(1);
			responseResult.setResult(loginUser);
			responseResult.setMessage("登录成功");
			//log.info("用户登录成功！账号:{} ", para.getAuthId());
		} else {
			responseResult.setCode(0).setMessage("手机号不存在");
		}
		return responseResult;
	}

	@Override
	public ResponseResult<String> sendVerificationCode(String authId, String phone) {
		ResponseResult<String> responseResult = new ResponseResult<>();
		try {
			// 验证账号
			String userId = loginMapper.queryAccount(authId, phone);
			if (null == userId) {
				responseResult.setCode(0).setMessage("用户名或店铺授权编码与手机号不匹配");
			} else {
				// 发送验证码
				String param = randomCode();
				UmsParameter umsParameter = new UmsParameter().setParmA(param).setPhone(phone).setCode(CODE);
//				ResponseBase responseBase = foreignService.getUmsDto(umsParameter);
//				if (responseBase.getCode() == 0) {
//					responseResult.setCode(0).setMessage("验证码发送失败");
//				} else {
//					responseResult.setResult(param).setCode(1).setMessage("验证码发送成功");
//				}
			}
		} catch (Exception e) {
			responseResult.setCode(0).setMessage("系统异常");
			log.error("发送验证码失败:{}", e.getMessage());
		}
		return responseResult;
	}

	@Override
	public ResponseBase resetPassword(String authId, String password) {
		ResponseBase responseBase = new ResponseBase();
		try {
			UserLoginInfo userLoginInfo = new UserLoginInfo().setPassword(MD5Util.toMD5(password)).setUpdateTime(LocalDateTime.now());
			int res = userLoginInfoMapper.update(userLoginInfo,new QueryWrapper<UserLoginInfo>().eq("auth_code",authId));
			if (res == 1){
				responseBase.setCode(1).setMessage("密码重置成功!");
			}else{
				responseBase.setCode(0).setMessage("密码重置失败!");
			}
		} catch (Exception e) {
			log.error("密码重置失败:{}", e.getMessage());
			responseBase.setCode(0).setMessage("系统异常");
		}
		return responseBase;
	}

	public static String randomCode() {
		StringBuilder str = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			str.append(random.nextInt(10));
		}
		return str.toString();
	}
}
