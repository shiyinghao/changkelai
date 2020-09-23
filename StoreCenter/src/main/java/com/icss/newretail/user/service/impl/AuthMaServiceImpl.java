package com.icss.newretail.user.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icss.newretail.model.*;
import com.icss.newretail.service.user.AuthMaService;
import com.icss.newretail.user.dao.*;
import com.icss.newretail.user.entity.*;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.MD5Util;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.swagger.invocation.context.ContextUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthMaServiceImpl implements AuthMaService {

	@Autowired
	private UserAuthMethodMapper userAuthMethodMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserCodeMapper userCodeMapper;

	@Autowired
	private LoginMapper loginMapper;

	@Autowired
	private UserOrgRelationMapper userOrgRelationMapper;

	@Autowired
	private UserUmsMapper userUmsMapper;

	@Autowired
	private ShopLoginRecordMapper shopLoginRecordMapper;

	@Autowired
	private StoreUseRecordMapper storeUseRecordMapper;



	/**
	 * 商家微信端账号密码登录
	 *
	 * @param loginRequestByPwd
	 * @return
	 */
	@Override
	public ResponseResult<LoginByPwdDTO> loginByPassword(LoginRequestByPwd loginRequestByPwd) {

		ResponseResult<LoginByPwdDTO> responseResult = new ResponseResult<LoginByPwdDTO>();

		String openId = loginRequestByPwd.getOpenId();
		UserAuthMethod userAuthMethod = userAuthMethodMapper.queryUserByAccount(loginRequestByPwd.getAccount());

		if (userAuthMethod == null && StringUtils.isEmpty(userAuthMethod)) {
			responseResult.setCode(1);
			responseResult.setMessage("您的信息尚未录入!");
		} else {
			UserInfo userInfo = userInfoMapper.selectById(userAuthMethod.getUserId());

			if (MD5Util.toMD5(loginRequestByPwd.getPassword()).equals(userInfo.getPassword())) {
				Map<String, Object> map = new HashMap<>();
				map.put("userId", userInfo.getUserId());
				String token = JwtTokenUtil.generateToken(map);// 生成JWTtoken
				LoginByPwdDTO loginByPwdDTO = new LoginByPwdDTO();
				loginByPwdDTO.setToken(token);
				loginByPwdDTO.setTokenExpTime(JwtTokenUtil.getExpirationDateFromToken(token));
				loginByPwdDTO.setCurrLoginTime(JwtTokenUtil.getIssuedAtDateFromToken(token));
				loginByPwdDTO.setUserId(userInfo.getUserId());
				loginByPwdDTO.setOrgSeq(userInfo.getOrgSeq());
				responseResult.setCode(1);
				responseResult.setMessage("登录成功");
				responseResult.setResult(loginByPwdDTO);
			} else {
				responseResult.setCode(0);
				responseResult.setMessage("账号密码错误");
			}

		}
		return responseResult;
	}


	/**
	 * 商家端登录曾经是否微信小程序
	 *
	 * @param loginMaRequest
	 * @return
	 */
	@Override
	public ResponseResult<LoginMaDTO> loginShop(LoginMaRequest loginMaRequest) {
		ResponseResult<LoginMaDTO> responseResult = new ResponseResult<LoginMaDTO>();

		return responseResult;
	}


	/**
	 * 手机验证码登录
	 *
	 * @param loginByAuthCode
	 * @return
	 */
	@Override
	public ResponseResult<LoginAuthDTO> loginByAuthCode(LoginByAuthCode loginByAuthCode) {

		ResponseResult<LoginAuthDTO> responseResult = new ResponseResult<>();
		LoginAuthDTO loginAuthDTO = new LoginAuthDTO();

		//从loginshop中获取到openId,验证码 手机号
		String openId = loginByAuthCode.getOpenId();
		String authCode = loginByAuthCode.getAuthCode();
		String phone = loginByAuthCode.getAccount();


		try {
			//从loginShop 接口获取的 openId 是否为null
			if (!StringUtils.isEmpty(openId)) {
				//从验证码表中查询 数据库中的最新验证码,并校验,正确再进行下一步操作
				UserAuthCode userAuthCode = userCodeMapper.selectByPhAndCode(phone);
				//            System.out.println("查询出来的最新的验证码信息为:" + userAuthCode);

				//从页面获取到的手机号和验证码与数据库中的手机号验证码进行对比,若验证码正确 再去数据库中查询是否存在账号
				if (userAuthCode.getCode().equals(authCode) && !StringUtils.isEmpty(userAuthCode.getCode())) {

					//根据输入的账号,判断账号是否注册 ,返回t_user_auth_method中的账号记录 ,判断openId是否存在记录
					UserAuthMethod userAuthMethod = userAuthMethodMapper.queryUserByAccount(loginByAuthCode.getAccount());
					UserAuthMethod userAuthMethodSameUser = userAuthMethodMapper.queryUserByAccount(openId);

					if (userAuthMethod != null) {

						String userId = userAuthMethod.getUserId();
						ShopUserInfoDTO shopUserInfoDTO = userInfoMapper.queryUserInfo(userId);
						//根据前端传入参数 userId 从 t_user_organization 表中查询出 user_type
						List<UserOrgRelationDTO> userTypeList = userAuthMethodMapper.queryUserType(userId);
						System.out.println("用户类型集合userTypeList" + userTypeList);
						List<UserStoreNameDTO> userStoreInfoList = userInfoMapper.queryStoreByUserId(userId);
						//                System.out.println("店铺名称集合userStoreNameList>>>>>>>"+userStoreNameList);
						//把用户userId和opneId关联起来,并插入到t_user_auth_method.方便下次直接获取token
						UserAuthMethod userAuthMethodOpenId = new UserAuthMethod();
						userAuthMethodOpenId.setUuid(UUID.randomUUID().toString());
						userAuthMethodOpenId.setUserId(userId);
						userAuthMethodOpenId.setAccount(openId);
						userAuthMethodOpenId.setAuthMethod(4);

						if (userAuthMethodSameUser == null) {
							int insert = userAuthMethodMapper.insert(userAuthMethodOpenId);
						}
						//返回token
						Map<String, Object> map = new HashMap<>();
						map.put("openId", openId);
						map.put("userId", userId);
						String token = JwtTokenUtil.generateToken(map);

						loginAuthDTO.setUserId(userId);
						loginAuthDTO.setToken(token);
						loginAuthDTO.setTokenExpTime(JwtTokenUtil.getExpirationDateFromToken(token));
						loginAuthDTO.setCurrLoginTime(JwtTokenUtil.getIssuedAtDateFromToken(token));
						loginAuthDTO.setShopUserInfoDTO(shopUserInfoDTO);
						loginAuthDTO.setUserTypeList(userTypeList);
						loginAuthDTO.setUserStoreInfoList(userStoreInfoList);

						responseResult.setResult(loginAuthDTO);
						responseResult.setCode(1);
						responseResult.setMessage("登录成功");
					} else {
						System.out.println("未注册账号");
						responseResult.setCode(0);
						responseResult.setMessage("输入账号不存在,请重新输入!");
					}
				} else {
					responseResult.setCode(0);
					responseResult.setMessage("验证码不正确! 请重新输入");
				}
			} else {
				responseResult.setCode(0);
				responseResult.setMessage("参数错误!  openId为null或空字符串");
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseResult.setCode(0);
			responseResult.setMessage("保存登录信息失败!请重新登录");
		}

		return responseResult;
	}


	/**
	 * 小程序保存用户手机号
	 *
	 * @param saveUserInfoRequest
	 * @return
	 */
	@Override
	public ResponseBase saveUserMobile(
			SaveUserInfoRequest saveUserInfoRequest) {

		return null;
	}

	/**
	 * 效验token有效性
	 *
	 * @return
	 */
	@Override
	public ResponseBase checkToken() {
		String token = ContextUtils.getInvocationContext().getContext("token");
		// 验证token是否过期,包含了验证jwt是否正确
		try {
			boolean flag = JwtTokenUtil.isTokenExpired(token);
			if (flag) {
				return new ResponseBase(0, "token is expired");
			}
		} catch (JwtException e) {
			// 有异常就是token解析失败
			return new ResponseBase(0, "token is error");
		}
		return new ResponseBase(1, "");
	}

	@Override
	public ResponseBase insert(UserAuthCodeReq userAuthCodeReq) {
		int count = userCodeMapper.insertCode(userAuthCodeReq);
//        System.out.println("插入记录" + count);
		return new ResponseBase().setMessage("插入成功");
	}

	@Override
	public List<UserStoreNameDTO> queryStoreByUserId(String token) {
		String userId = JwtTokenUtil.currUser();
		return userInfoMapper.queryStoreByUserId(userId);
	}

//	@Override
//	public ResponseResult<LoginUserDTO> loginByPhone(LoginByAuthCode loginByAuthCode) {
//		ResponseResult<LoginUserDTO> responseResult = new ResponseResult<>();
//		//UserAuthCode userAuthCode = userCodeMapper.selectByPhAndCode(loginByAuthCode.getAccount());
//		UserUms userUms = userUmsMapper.selectByVertCode(loginByAuthCode.getAccount());
//
//		if (loginByAuthCode.getAccount().equals("18919662039")){
//			userUms.setVerificationCode("000000");
//		}
//		if (userUms != null && userUms.getVerificationCode().equals(loginByAuthCode.getAuthCode())) {
//			UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel", loginByAuthCode.getAccount()));
//			if (userInfo != null) {
//				LoginUserDTO loginUser = new LoginUserDTO();
//				String userId = userInfo.getUserId();
//				List<OrgList> orgLists = loginMapper.findOrgByUserId(userId);
//				List<OrgList> orgResLists = orgLists.stream().filter(
//						orgList -> orgList.getUserType() == 4
//								|| orgList.getUserType() == 5
//								|| orgList.getUserType() == 6)
//						.collect(Collectors.toList());
//
//				if(CollectionUtils.isEmpty(orgResLists)){
//					responseResult.setCode(0).setMessage("该用户非门店用户!");
//					return responseResult;
//				}
//				loginUser.setUserId(userId);
//				loginUser.setUserName(userInfo.getUserName());
//				loginUser.setRealName(userInfo.getRealName());
//				loginUser.setOrgList(orgResLists);
//				Map<String, Object> map = new HashMap<>();
//				map.put("userId", userId);
//				String token = JwtTokenUtil.generateToken(map);
//				loginUser.setToken(token);
//				responseResult.setResult(loginUser);
//				responseResult.setCode(1).setMessage("登录成功!");
//			} else {
//				responseResult.setCode(0).setMessage("账号不存在!");
//			}
//		} else {
//			responseResult.setCode(0).setMessage("验证码不正确!");
//		}
//		return responseResult;
//	}

	@Override
	public ResponseResult<LoginUserDTO> loginByPhone(LoginByAuthCode loginByAuthCode) {
		ResponseResult<LoginUserDTO> responseResult = new ResponseResult<>();
		// TODO:临时放开验证码校验
		UserAuthCode userAuthCode = userCodeMapper.selectByPhAndCode(loginByAuthCode.getAccount());
		if (loginByAuthCode.getAccount().equals("15208370820")) {
			userAuthCode.setCode("000000");
		}
		if (userAuthCode != null && userAuthCode.getCode().equals(loginByAuthCode.getAuthCode())) {
			UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel", loginByAuthCode.getAccount()).eq("status", 1));
			if (userInfo != null) {
				LoginUserDTO loginUser = new LoginUserDTO();
				String userId = userInfo.getUserId();
				List<OrgList> orgLists = loginMapper.findOrgByUserId(userId);
				List<OrgList> orgResLists = orgLists.stream().filter(
						orgList -> orgList.getUserType() == 4
								|| orgList.getUserType() == 5
								|| orgList.getUserType() == 6)
						.collect(Collectors.toList());

				if (CollectionUtils.isEmpty(orgResLists)) {
					responseResult.setCode(0).setMessage("该用户非门店用户!");
					return responseResult;
				}
				if ("6".equals(userInfo.getUserType())) {
					//判断信息是否完整
					if (userInfo.getIsInfoComplete() == 0) {
						responseResult.setCode(0).setMessage("账号信息不完整，请到管理后台完善店员信息后再登录!");
						return responseResult;
					}
				}
				//验证用户是否被禁用
//				if (!"1".equals(userInfo.getStatus())) {
//					responseResult.setCode(0).setMessage("该用户已被禁用或删除，请联系管理员登录!");
//					return responseResult;
//				}
				loginUser.setUserId(userId);
				loginUser.setUserName(userInfo.getUserName());
				loginUser.setRealName(userInfo.getRealName());
				loginUser.setOrgList(orgResLists);
				Map<String, Object> map = new HashMap<>();
				map.put("userId", userId);
				String token = JwtTokenUtil.generateToken(map);
				loginUser.setToken(token);
				responseResult.setResult(loginUser);
				responseResult.setCode(1).setMessage("登录成功!");
			} else {
				responseResult.setCode(0).setMessage("账号不存在或已禁用");
			}
		} else {
			responseResult.setCode(0).setMessage("验证码不正确!");
		}
		return responseResult;
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public ResponseResult<LoginUserDTO> register(LoginByAuthCode loginByAuthCode) {
		ResponseResult<LoginUserDTO> responseResult = new ResponseResult<>();
		try {
			UserInfo user = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel", loginByAuthCode.getAccount()));
			if (user != null) {
				responseResult.setCode(0).setMessage("该账号已注册!");
				return responseResult;
			}
			UserInfo userInfo = new UserInfo()
					.setUserId(UUID.randomUUID().toString())
					.setUserName(loginByAuthCode.getAccount())
					.setTel(loginByAuthCode.getAccount())
					.setOrgSeq(loginByAuthCode.getOrgSeq())
					.setCreateTime(new Date())
					.setStatus(1)
					.setUserType("6")
					.setRealName(loginByAuthCode.getRealName())
					.setSex(loginByAuthCode.getSex())
					.setHeadPicUrl(loginByAuthCode.getHeadPicUrl());
			userInfoMapper.insert(userInfo);
			UserOrgRelation userOrgRelation = new UserOrgRelation()
					.setUuid(UUID.randomUUID().toString()).setStatus(1)
					.setCreateTime(LocalDateTime.now())
					.setUserId(userInfo.getUserId())
					.setOrgSeq(loginByAuthCode.getOrgSeq())
					.setUserType(6);
			userOrgRelationMapper.insert(userOrgRelation);
			return loginByPhone(loginByAuthCode);
		} catch (Exception e) {
			responseResult.setMessage(e.getMessage());
			responseResult.setCode(0);
			log.error("AuthMaServiceImpl|register->业务员注册[" + e.getLocalizedMessage() + "]");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚
		}
		return responseResult;
	}

	@Override
	public ResponseResult<LoginUserDTO> tokenCheck() {
		ResponseResult<LoginUserDTO> responseResult = new ResponseResult<>();
		try {
			String userId = JwtTokenUtil.currUser();
			UserInfo userInfo = userInfoMapper.selectById(userId);
			List<OrgList> orgLists = loginMapper.findOrgByUserId(userId);
			LoginUserDTO userDTO = new LoginUserDTO();
			userDTO.setUserId(userId);
			userDTO.setUserName(userInfo.getUserName());
			userDTO.setRealName(userInfo.getRealName());
			userDTO.setOrgList(orgLists);
			Map<String, Object> map = new HashMap<>();
			map.put("userId", userId);
			String token = JwtTokenUtil.generateToken(map);
			userDTO.setToken(token);
			responseResult.setResult(userDTO);
			responseResult.setCode(1).setMessage("登录成功");
		} catch (Exception e) {
			log.error("系统异常:{}", e.getMessage());
			responseResult.setCode(0).setMessage("系统异常");
		}
		return responseResult;
	}

	@Override
	public ResponseResult<Long> addLoginRecord(ShopLoginRecordDTO dto) {
		ResponseResult<Long> responseResult = new ResponseResult<>();
		try {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime before = LocalDateTime.now().minusSeconds(30);
			ShopLoginRecord oldRecord = shopLoginRecordMapper.selectOne(new QueryWrapper<ShopLoginRecord>()
					.eq("user_id", dto.getUserId())
					.eq("org_seq", dto.getOrgSeq())
					.between("logout_time", before, now));
			if (oldRecord != null) {
				responseResult.setResult(oldRecord.getUuid()).setCode(1).setMessage("新增登录记录成功");
				return responseResult;
			}
			ShopLoginRecord shopLoginRecord = new ShopLoginRecord();
			BeanUtils.copyProperties(dto, shopLoginRecord);
			shopLoginRecord.setLoginTime(now);
			shopLoginRecordMapper.insert(shopLoginRecord);
			responseResult.setResult(shopLoginRecord.getUuid()).setCode(1).setMessage("新增登录记录成功");
		} catch (BeansException e) {
			log.error("新增登录记录异常:{}", e.getMessage());
			responseResult.setCode(0).setMessage("系统异常");
		}
		return responseResult;
	}

	@Transactional
	@Override
	public ResponseBase addLogoutRecord(Long id) {
		ResponseBase responseBase = new ResponseBase();
		try {
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			ShopLoginRecord shopLoginRecord = shopLoginRecordMapper.selectById(id);
			if (shopLoginRecord == null) {
				responseBase.setCode(0).setMessage("当前id=[" + id + "]未查询到数据");
				return responseBase;
			}
			LocalDateTime now = LocalDateTime.now();
			Long stayTime = Duration.between(shopLoginRecord.getLoginTime(), now).toMinutes();
			shopLoginRecord.setLogoutTime(now).setStayTime(stayTime);
			shopLoginRecordMapper.updateById(shopLoginRecord);
			StoreUseRecord storeUseRecord = storeUseRecordMapper.selectOne(new QueryWrapper<StoreUseRecord>()
					.eq("org_seq", shopLoginRecord.getOrgSeq()));
			if (storeUseRecord == null) {
				storeUseRecord = new StoreUseRecord().setOrgSeq(shopLoginRecord.getOrgSeq()).setUseTime(stayTime)
						.setStartTimes(1).setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now());
				storeUseRecordMapper.insert(storeUseRecord);
			} else {
				storeUseRecord.setUseTime(stayTime);
				storeUseRecordMapper.updateStoreUseRecord(storeUseRecord);
			}
			responseBase.setCode(1).setMessage("成功");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("登出记录维护异常:{}", e.getMessage());
			responseBase.setCode(0).setMessage("系统异常");
		}
		return responseBase;
	}
}
