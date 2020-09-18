package com.icss.newretail.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icss.newretail.model.HwCloudAppInfoDTO;
import com.icss.newretail.model.HwCloudResponseBase;
import com.icss.newretail.model.HwCloudResponseNewInstance;
import com.icss.newretail.service.user.HwCloudService;
import com.icss.newretail.user.dao.InitMapper;
import com.icss.newretail.user.dao.TenantMapper;
import com.icss.newretail.user.dao.TenantSaascallMapper;
import com.icss.newretail.user.dao.TenantSaasorderMapper;
import com.icss.newretail.user.dao.TenantSaasuserMapper;
import com.icss.newretail.user.dao.UserInfoMapper;
import com.icss.newretail.user.dao.UserRoleMapper;
import com.icss.newretail.user.dao.UserUserRoleRelationMapper;
import com.icss.newretail.user.entity.Tenant;
import com.icss.newretail.user.entity.TenantSaascall;
import com.icss.newretail.user.entity.TenantSaasorder;
import com.icss.newretail.user.entity.TenantSaasuser;
import com.icss.newretail.user.entity.UserInfo;
import com.icss.newretail.user.entity.UserRole;
import com.icss.newretail.user.entity.UserUserRoleRelation;
import com.icss.newretail.util.DateUtils;
import com.icss.newretail.util.HwCloudUtil;
import com.icss.newretail.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.servicecomb.swagger.invocation.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @ClassName: HwCloudServiceImpl
 * @Description: TODO
 * @author jc
 * @date 2019年8月7日
 *
 */
@Service
@Slf4j
public class HwCloudServiceImpl implements HwCloudService {
	private static String accessKey = "9fa6c870-8bb0-4e9b-b71e-4c223300156f";// 卖家中心获取Key值

	private static int encryptLength = 2;// 1：AES256_CBC_PKCS5Padding（默认值） 2：AES128_CBC_PKCS5Padding

	@Autowired
	private TenantMapper tenantMapper;
	@Autowired
	private TenantSaasuserMapper tenantSaasuserMapper;
	@Autowired
	private TenantSaascallMapper tenantSaascallMapper;
	@Autowired
	private TenantSaasorderMapper tenantSaasorderMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private UserUserRoleRelationMapper userUserRoleRelationMapper;
	@Autowired
	private InitMapper initMapper;

	/**
	 * 华为云市场回调接口 详见
	 * https://support.huaweicloud.com/accessg-marketplace/saas_topic_0000001.html
	 * @return
	 */
	@SuppressWarnings("finally")
	@Override
	public Response saasproduce(HttpServletRequest request) {
		Object result = null;
		try {
			// 按照业务类型处理客户授权信息
			if (HwCloudUtil.verificateRequestParams(request, accessKey, encryptLength)) { // 鉴权
				String activity = request.getParameter("activity");// 判断业务类型
				if ("newInstance".equals(activity)) {// 新购
					HwCloudAppInfoDTO appInfo = newInstanceHandle(request);
					result = new HwCloudResponseNewInstance(appInfo,request.getParameter("businessId"));
					String json = JSONObject.toJSONString(result);
					addSaascall(request, "000000", "success", json);
				} else if ("refreshInstance".equals(activity)) {// 续费
					refreshInstanceHandle(request);
					result = new HwCloudResponseBase("000000", "success");
					String json = JSONObject.toJSONString(result);
					addSaascall(request, "000000", "success", json);
				} else if ("expireInstance".equals(activity)) {// 过期
					expireInstanceHandle(request);
					result = new HwCloudResponseBase("000000", "success");
					String json = JSONObject.toJSONString(result);
					addSaascall(request, "000000", "success", json);
				} else if ("releaseInstance".equals(activity)) {// 资源释放
					releaseInstanceHandle(request);
					result = new HwCloudResponseBase("000000", "success");
					String json = JSONObject.toJSONString(result);
					addSaascall(request, "000000", "success", json);
				} else {
					result = new HwCloudResponseBase("000002", "接口类型错误！");
					String json = JSONObject.toJSONString(result);
					addSaascall(request, "000002", "接口类型错误！", json);
					log.info("华为云市场回调参数业务类型activity:[" + activity + "]不识别！");
				}
			} else {
				result = new HwCloudResponseBase("000001", "鉴权失败！");
				String json = JSONObject.toJSONString(result);
				addSaascall(request, "000001", "鉴权失败", json);
				log.info("华为云市场回调参数鉴权失败!");
			}
		} catch (Exception e) {
			result = new HwCloudResponseBase("000005", "服务异常！");
			String json = JSONObject.toJSONString(result);
			addSaascall(request, "000005", "服务异常！", json);
			log.info("华为云市场回调服务异常：" + e.getMessage());
		} finally {
			return HwCloudUtil.signRespose(accessKey, result);// HTTP Body签名
		}
	}

	/**
	 * 
	 * @Title: newInstanceHandle
	 * @Description: 新购处理
	 * @param @param request    参数
	 * @return void    返回类型
	 * @throws
	 */
	@Transactional(rollbackFor = { Exception.class })
	public HwCloudAppInfoDTO newInstanceHandle(HttpServletRequest request) {
		HwCloudAppInfoDTO appInfo = null;
		try {
			appInfo = new HwCloudAppInfoDTO(request.getParameterMap());
			String orderId = request.getParameter("orderId");
			TenantSaasorder tenantSaasorder = tenantSaasorderMapper.selectById(orderId);
			if(tenantSaasorder != null){
				TenantSaascall tenantSaascall = tenantSaascallMapper.getTenantSaascallByOrderId(orderId);
				String responseData = tenantSaascall.getResponseData();
				JSONObject json = JSONObject.parseObject(responseData);
				JSONObject jsonObject = json.getJSONObject("appInfo");
				appInfo.setAdminUrl(jsonObject.getString("adminUrl"));
				appInfo.setFrontEndUrl(jsonObject.getString("frontEndUrl"));
				appInfo.setUserName(jsonObject.getString("userName"));
				appInfo.setPassword(jsonObject.getString("password"));
				return appInfo;
			}
			String userName = "admin";
			String password = createPassword();
			appInfo.setUserName(HwCloudUtil.generateSaaSUsernameOrPwd(accessKey, userName, 128));
			appInfo.setPassword(HwCloudUtil.generateSaaSUsernameOrPwd(accessKey, password, 128));
			// 插入平台租户信息表
			Tenant tenant = new Tenant();
			String oldTenantId = tenantMapper.queryLastTenantId();
			if (oldTenantId == null) {
				tenant.setTenantId("01");
			} else {
				tenant.setTenantId(getTenantId(oldTenantId));
			}
			appInfo.setFrontEndUrl("http://mt.wssaa.com/ui/index.html#/?tenc=" + tenant.getTenantId());
			appInfo.setAdminUrl("http://mt.wssaa.com/ui/index.html#/?tenc=" + tenant.getTenantId());

			//初始化管理员角色、用户、菜单
			UserInfo userInfo =  initInfo(tenant.getTenantId(),password);
			String userId = userInfo.getUserId();
//			tenant.setTenantCode(request.getParameter("customerId"));
			tenant.setTenantCode(tenant.getTenantId());
			tenant.setTenantName(request.getParameter("customerName"));
			tenant.setInstanceId(request.getParameter("businessId"));//实例ID，服务商提供的唯一标识 bussinessId作为instanceId
			String expireTime = request.getParameter("expireTime");
			if (expireTime != null) {
				tenant.setExpireTime(LocalDateTime.parse(expireTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
			}
			tenant.setStatus(1);
			tenant.setCreateUser(userId);
			tenant.setUpdateUser(userId);
			tenant.setCreateTime(LocalDateTime.now());
			tenant.setUpdateTime(LocalDateTime.now());
			tenantMapper.insert(tenant);
			// 插入平台租户客户信息表
			TenantSaasuser tenantSaasuser = new TenantSaasuser();
			tenantSaasuser.setUuid(UUID.randomUUID().toString());
			tenantSaasuser.setCustomerId(request.getParameter("customerId"));
			tenantSaasuser.setCustomerName(request.getParameter("customerName"));
			tenantSaasuser.setUserId(request.getParameter("userId"));
			tenantSaasuser.setUserName(request.getParameter("userName"));
			tenantSaasuser.setMobilePhone(request.getParameter("mobilePhone"));
			tenantSaasuser.setEmail(request.getParameter("email"));
			tenantSaasuser.setBusinessId(request.getParameter("businessId"));
			tenantSaasuser.setCreateUser(userId);
			tenantSaasuser.setUpdateUser(userId);
			tenantSaasuser.setCreateTime(LocalDateTime.now());
			tenantSaasuser.setUpdateTime(LocalDateTime.now());
			tenantSaasuser.setTenantId(tenant.getTenantId());
			tenantSaasuserMapper.insert(tenantSaasuser);
			// 平台租户订单信息表
			TenantSaasorder saasorder = new TenantSaasorder();
			saasorder.setOrderId(request.getParameter("orderId"));
			saasorder.setSkuCode(request.getParameter("skuCode"));
			saasorder.setProductId(request.getParameter("productId"));
			saasorder.setTrialFlag(request.getParameter("trialFlag"));
			if (expireTime != null) {
				saasorder.setExpireTime(LocalDateTime.parse(expireTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
			}
			String chargingMode = request.getParameter("chargingMode");
			if (chargingMode != null) {
				saasorder.setChargingMode(Integer.parseInt(chargingMode));
			}
			saasorder.setSaasExtendParams(request.getParameter("saasExtendParams"));
			saasorder.setCreateUser(userId);
			saasorder.setUpdateUser(userId);
			saasorder.setCreateTime(LocalDateTime.now());
			saasorder.setUpdateTime(LocalDateTime.now());
			saasorder.setTenantId(tenant.getTenantId());
			tenantSaasorderMapper.insert(saasorder);
		} catch (Exception e) {
			log.info("新购处理操作异常" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
		}
		return appInfo;
	}

	/**
	 * 
	 * @Title: refreshInstanceHandle
	 * @Description: 续费处理
	 * @param @param request    参数
	 * @return void    返回类型
	 * @throws
	 */
	@Transactional(rollbackFor = { Exception.class })
	public void refreshInstanceHandle(HttpServletRequest request) {
		try {
			// 更新租户信息
//			String userId = JwtTokenUtil.currUser();
			String instanceId = request.getParameter("instanceId");
			String expireTime = request.getParameter("expireTime");
			Tenant tenant = tenantMapper.selectOne(new QueryWrapper<Tenant>().eq("instance_id", instanceId));
			tenant.setStatus(1);
			if (expireTime != null) {
				tenant.setExpireTime(LocalDateTime.parse(expireTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
			}
//			tenant.setUpdateUser(userId);
			tenant.setUpdateTime(LocalDateTime.now());
			tenantMapper.updateById(tenant);
			// 新增租户订单
			TenantSaasorder saasorder = new TenantSaasorder();
			saasorder.setOrderId(request.getParameter("orderId"));
			saasorder.setProductId(request.getParameter("productId"));
			if (expireTime != null) {
				saasorder.setExpireTime(LocalDateTime.parse(expireTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
			}
//			saasorder.setCreateUser(userId);
//			saasorder.setUpdateUser(userId);
			saasorder.setCreateTime(LocalDateTime.now());
			saasorder.setUpdateTime(LocalDateTime.now());
			saasorder.setTenantId(tenant.getTenantId());
			tenantSaasorderMapper.insert(saasorder);
		} catch (Exception e) {
			log.info("续费处理操作异常" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
		}
	}

	/**
	 * 
	 * @Title: expireInstanceHandle
	 * @Description: 过期处理
	 * @param @param request    参数
	 * @return void    返回类型
	 * @throws
	 */
	public void expireInstanceHandle(HttpServletRequest request) {
		try {
			// 更新租户信息
//			String userId = JwtTokenUtil.currUser();
			String instanceId = request.getParameter("instanceId");
			Tenant tenant = tenantMapper.selectOne(new QueryWrapper<Tenant>().eq("instance_id", instanceId));
			tenant.setStatus(2);
//			tenant.setUpdateUser(userId);
			tenant.setUpdateTime(LocalDateTime.now());
			tenantMapper.updateById(tenant);
		} catch (Exception e) {
			log.info("更新租户信息异常" + e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: releaseInstanceHandle
	 * @Description: 资源释放操作
	 * @param @param request    参数
	 * @return void    返回类型
	 * @throws
	 */
	public void releaseInstanceHandle(HttpServletRequest request) {
		try {
			// 更新租户信息
//			String userId = JwtTokenUtil.currUser();
			String instanceId = request.getParameter("instanceId");
			Tenant tenant = tenantMapper.selectOne(new QueryWrapper<Tenant>().eq("instance_id", instanceId));
			tenant.setStatus(3);
//			tenant.setUpdateUser(userId);
			tenant.setUpdateTime(LocalDateTime.now());
			tenantMapper.updateById(tenant);
		} catch (Exception e) {
			log.info("更新租户信息异常" + e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: addSaascall
	 * @Description: 新增交互记录
	 * @param @param request
	 * @param @param code
	 * @param @param msg
	 * @param @param res    参数
	 * @return void    返回类型
	 * @throws
	 */
	public void addSaascall(HttpServletRequest request, String code, String msg, Object res) {
		try {
			// 新增交互记录
			TenantSaascall saascall = new TenantSaascall();
			saascall.setActivity(request.getParameter("activity"));
			String instanceId = request.getParameter("businessId") == null ? request.getParameter("activity")
					: request.getParameter("businessId");
			saascall.setInstanceId(instanceId);
			saascall.setTestFlag(request.getParameter("testFlag"));
			if (request.getParameter("timeStamp") != null) {
				Date date = new SimpleDateFormat("yyyyMMddHHmmssSSS").parse(request.getParameter("timeStamp"));
				saascall.setSendTime(DateUtils.Date2LocalDateTime(date));
			}
			saascall.setOrderId(request.getParameter("orderId"));
			saascall.setRequestData(JSON.toJSONString(request.getParameterMap()));
			saascall.setResultCode(code);
			saascall.setResultMsg(msg);
			saascall.setResponseData(res.toString());
			saascall.setCreateTime(LocalDateTime.now());
			saascall.setUpdateTime(LocalDateTime.now());
			tenantSaascallMapper.insert(saascall);
		} catch (Exception e) {
			log.info("新增交互记录异常" + e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: getTenantId
	 * @Description: 获取新tenantId（01-99-A1-Z9-AA-ZZ）
	 * @param @param oldTenantId
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public String getTenantId(String oldTenantId) {
		String regex1 = "^[0-9]{2}$";
		String regex2 = "^[A-Z][0-9]$";
		String regex3 = "^[A-Z]{2}$";
		String res = null;
		if (Pattern.matches(regex1, oldTenantId)) {
			Integer num = Integer.parseInt(oldTenantId);
			if (num < 9) {
				num++;
				res = "0" + num.toString();
			} else if (num >= 9 && num < 99) {
				num++;
				res = num.toString();
			} else {
				res = "A1";
			}
		} else if (Pattern.matches(regex2, oldTenantId)) {
			if ("Z9".equals(oldTenantId)) {
				res = "AA";
			} else {
				char char1 = oldTenantId.charAt(0);
				int char2 = Integer.parseInt(String.valueOf(oldTenantId.charAt(1)));
				if (char2 < 9) {
					char2++;
				} else {
					char2 = 1;
					char1++;
				}
				res = String.valueOf(char1) + String.valueOf(char2);
			}
		} else if (Pattern.matches(regex3, oldTenantId)) {
			char char1 = oldTenantId.charAt(0);
			char char2 = oldTenantId.charAt(1);
			if (char2 < 'Z') {
				char2++;
			} else {
				char2 = 'A';
				char1++;
			}
			res = String.valueOf(char1) + String.valueOf(char2);
		}
		return res;
	}

	/**
	 * 生成8位随机密码
	 * @return
	 */
	public String createPassword(){
		char[] ss = new char[8];
		int i=0;
		while(i<8) {
			int f = (int) (Math.random()*3);
			if(f==0)
				ss[i] = (char) ('A'+Math.random()*26);
			else if(f==1)
				ss[i] = (char) ('a'+Math.random()*26);
			else
				ss[i] = (char) ('0'+Math.random()*10);
			i++;
		}
		String str=new String(ss);
		log.info(str);
		return str;
	}

	/**
	 * 初始化数据
	 * @param tenantId
	 * @param password
	 */
	@Transactional(rollbackFor = { Exception.class })
	public UserInfo initInfo(String tenantId,String password){
		UserInfo userInfo = new UserInfo();
		try {
			//初始化admin用户
			userInfo.setUserId(UUID.randomUUID().toString());
			userInfo.setUserName("admin");
			userInfo.setPassword(MD5Util.toMD5(password));
			userInfo.setCreateTime(new Date());
			userInfoMapper.createAccount(userInfo,"userName");
			userInfoMapper.insert(userInfo);
			//初始化管理员角色
			UserRole userRole = new UserRole();
			userRole.setRoleId(UUID.randomUUID().toString());
			userRole.setRoleReq(System.currentTimeMillis()+"");
			userRole.setRoleName("系统管理员");
			userRole.setStatus(1);
			userRole.setRoleType(1);
			userRole.setCreateTime(new Date());
			userRole.setCreateUser(userInfo.getUserId());
			userRoleMapper.insert(userRole);
			//初始admin用户设置管理员角色
			List<UserUserRoleRelation> list = new ArrayList<>();
			UserUserRoleRelation userRoleRelation = new UserUserRoleRelation();
			userRoleRelation.setUserRoleRelationId(UUID.randomUUID().toString());
			userRoleRelation.setRoleId(userRole.getRoleId());
			userRoleRelation.setUserId(userInfo.getUserId());
			userRoleRelation.setCreateUser(userInfo.getUserId());
			userRoleRelation.setUpdateUser(userInfo.getUserId());
			userRoleRelation.setStatus(1);
			list.add(userRoleRelation);
			userUserRoleRelationMapper.bathcAdd(list);
			//初始化应用
			initMapper.initApp(tenantId);
			//初始化模块
			initMapper.initModule(tenantId);
			//初始化应用模块关联
			initMapper.initAppModuleRelation(tenantId);
			//初始化菜单
			initMapper.initMenu(tenantId);
			//初始化管理员角色菜单关联
			initMapper.initRoleMenuRelation(userRole.getRoleId(),tenantId);
		} catch (Exception e) {
			log.info("初始化数据" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
		}
		return userInfo;
	}

}
