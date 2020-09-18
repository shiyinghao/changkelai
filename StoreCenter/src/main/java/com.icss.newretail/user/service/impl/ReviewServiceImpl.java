package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.service.data.DataService;
import com.icss.newretail.service.data.StoreActivationService;
import com.icss.newretail.service.goods.GoodsForeignService;
import com.icss.newretail.service.member.MemberService;
import com.icss.newretail.service.pay.IcbcInteractiveService;
import com.icss.newretail.service.user.ForeignService;
import com.icss.newretail.service.user.ReviewService;
import com.icss.newretail.user.dao.*;
import com.icss.newretail.user.entity.*;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.MD5Util;
import com.icss.newretail.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author jc
 * @date 2020/3/23 20:06
 */
@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

	private static final String MEMBER_SEQ_KEY = "USER";
	private static final String SEQ_KEY = "DY";
	private static final String store_maneger = "store_manager";
	private static final String store_owner = "store_owner";
	private static final String code = "1011012092222";
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private UserStoreVerifyMapper userStoreVerifyMapper;
	@Autowired
	private UserReviewInfoMapper mapper;
	@Autowired
	private UserInfoMapper UserInfoMapperMapper;
	@Autowired
	private UserAuthMethodMapper userAuthMethodMapper;
	@Autowired
	private UserReviewInfoMapper userReviewInfoMapper;
	@Autowired
	private OrganizationMapper organizationMapper;
	@Autowired
	private StoreMapper storeMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private UserOrgRelationMapper userOrgRelationMapper;
	@Autowired
	private StoreBonuspointMapper storeBonuspointMapper;
	private UserOrgRelation userOrgRelation;
	private UserStoreInfo userStoreInfo;
	private StoreBonuspoint storeBonuspoint;
	private UserInfo userinfoOwn;
	private UserInfo userinfoManager;
	@Autowired
	private ForeignService foreignService;
	@Autowired
	private UserReviewRecordMapper userReviewRecordMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private UserUserRoleRelationMapper uerUserRoleRelationMapper;
	@RpcReference(microserviceName = "goods-service", schemaId = "GoodsForeign")
	private GoodsForeignService goodsForeignService;
	@Autowired
	private StoreGradelevelRuleMapper storeGradelevelRuleMapper;

	@RpcReference(microserviceName = "member-service", schemaId = "MemberApi")
	private MemberService memberService;

	@RpcReference(microserviceName = "data-service", schemaId = "dataApi")
	private DataService dataService;

	@RpcReference(microserviceName = "pay-service", schemaId = "IcbcInteractiveApi")
	private IcbcInteractiveService icbcInteractiveService;

	@RpcReference(microserviceName = "data-service", schemaId = "StoreActivation")
	private StoreActivationService storeActivationService;

	@Autowired
	private UserIcbcTempMapper userIcbcTempMapper;

	@Autowired
	private UserIcbcFilingMapper userIcbcFilingMapper;

	@Autowired
	private UserLoginInfoMapper userLoginInfoMapper;

	@Autowired
	private RedissonClient redissonClient;

	public ReviewServiceImpl() {
	}

	@Override
	public ResponseResult<LoginUserDTO> shopAuth(String authCode) {
		ResponseResult<LoginUserDTO> responseBase = new ResponseResult<LoginUserDTO>();
		List<UserStoreInfo> userStoreInfo = storeMapper.selectList(new QueryWrapper<UserStoreInfo>().eq("auth_code", authCode));
		//判断当前申请码是否已经生成了店铺
		if (userStoreInfo != null && userStoreInfo.size() > 0) {
			responseBase.setCode(0);
			responseBase.setMessage("当前门店授权编码已绑定店铺");
			return responseBase;
		}
		UserStoreVerify userStoreVerify = userStoreVerifyMapper.selectOne(new QueryWrapper<UserStoreVerify>().eq("auth_code", authCode));
		if (userStoreVerify != null) {
			responseBase.setCode(1);
			responseBase.setMessage("验证通过");
			Map<String, Object> map = new HashMap<>();
			map.put("userId", authCode);
			String token = JwtTokenUtil.generateToken(map);// 生成JWTtoken
			LoginUserDTO loginUser = new LoginUserDTO();
			loginUser.setToken(token);
			responseBase.setResult(loginUser);
		} else {
			responseBase.setCode(0);
			responseBase.setMessage("验证失败");
		}
		return responseBase;
	}

	@Override
	@Transactional
	public ResponseBase applyShop(UserReviewInfoDTO userReviewInfoDTO) {


		ResponseBase responseBase = new ResponseBase();
		//判断authCode不能为空 如果为空 则从token中拿去authcode
		String authCode = userReviewInfoDTO.getAuthCode();
		if (StringUtils.isEmpty(authCode) || "undefined".equals(authCode)) {
			userReviewInfoDTO.setAuthCode(JwtTokenUtil.currUser());
		}
		//判断建库信息是否存在银行key值
		List<UserIcbcFilingDTO> userIcbcFilingDTOList2 = userReviewInfoDTO.getUserIcbcFilingDTOList();
		if (userIcbcFilingDTOList2 != null && userIcbcFilingDTOList2.size() > 0) {
			for (int i = 0; i < userIcbcFilingDTOList2.size(); i++) {
				//如果上传的建库信息出现没有银行key的情况 则不允许提交
				String icbcKey = userIcbcFilingDTOList2.get(i).getIcbckey() == null ? "" : userIcbcFilingDTOList2.get(i).getIcbckey();
				if ("".equals(icbcKey)) {
					int fileType = userIcbcFilingDTOList2.get(i).getFileType() == null ? 0 : userIcbcFilingDTOList2.get(i).getFileType();
//					1-法定代表人授权书,2-信息查询使用授权书,3-收单业务书,4-非金融企业划型信息采集表,5-对公客户信息采集表\r\n,
//					6-非自然人客户受益所有人采集表,7-单位税收居民声明文件，8-营业执照，9-专卖店门头照片，10-法人身份证正面照片，
//					11-法人身份证背面照片，12-结算账号照片，13-门店内景照片，14-门店收银台照片\r\n)',
					String message = getMessage(fileType);
					responseBase.setCode(0);
					responseBase.setMessage("工行文件上传密钥信息获取失败，" + message);
					return responseBase;
				}
			}
		} else {
			responseBase.setCode(0);
			responseBase.setMessage("建档信息未正常上传，请检查信息是否正确！");
			return responseBase;
		}
		UserReviewInfo userReviewInfo = new UserReviewInfo();
		BeanUtils.copyProperties(userReviewInfoDTO, userReviewInfo);
		if (userReviewInfoDTO.getUuid() != null) {
			userReviewInfo.setReviewStatus(1);
			userReviewInfo.setReviewType(1);
			//重新提交申请将原来得审批人信息弃之为空
			userReviewInfo.setReviewUser("");
			userReviewInfo.setFinalReviewUser("");
			userReviewInfo.setReviewOpinion(null);
			userReviewInfo.setFinalReviewOpinion(null);
			userReviewInfo.setIcbcSwitch(1);
			userReviewInfo.setUpdateTime(LocalDateTime.now());
			//获取基地名称，战区名称，基地业务员名称
			if (userReviewInfoDTO.getUpOrgSeq() != null && userReviewInfoDTO.getUpOrgSeq() != "" && userReviewInfoDTO.getUpOrgSeq() != "null") {
				UserOrganization userOrganization = organizationMapper.selectById(userReviewInfoDTO.getUpOrgSeq());
				userReviewInfo.setUpOrgZone(userOrganization.getOrgName());
			}
			if (userReviewInfoDTO.getBaseCode() != null && userReviewInfoDTO.getBaseCode() != "" && userReviewInfoDTO.getBaseCode() != "null") {
				UserOrganization userOrganization = organizationMapper.selectById(userReviewInfoDTO.getBaseCode());
				userReviewInfo.setBaseName(userOrganization.getOrgName());
			}
			if (userReviewInfoDTO.getBasePersonCode() != null && userReviewInfoDTO.getBasePersonCode() != "" && userReviewInfoDTO.getBasePersonCode() != "null") {
				UserInfo userInfo = userInfoMapper.selectById(userReviewInfoDTO.getBasePersonCode());
				userReviewInfo.setBasePersonName(userInfo.getRealName());
			}
			UserReviewRecord userReviewRecord = new UserReviewRecord();
			String uuid = UUID.randomUUID().toString();
			userReviewRecord.setUuid(uuid);
			userReviewRecord.setReviewId(userReviewInfo.getUuid());
			//审核状态审核状态(1-已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过)
			userReviewRecord.setReviewStatus(1);
			userReviewRecord.setReviewTime(LocalDateTime.now());
			userReviewRecordMapper.insert(userReviewRecord);
			mapper.updateById(userReviewInfo);
		} else {
			//判断用户是否重复点击
			List<UserReviewInfo> userReviewInfo1 = userReviewInfoMapper.selectList(
					new QueryWrapper<UserReviewInfo>().eq("auth_code", userReviewInfoDTO.getAuthCode()).eq("review_type", 1));
			if (userReviewInfo1 != null && userReviewInfo1.size() > 0) {
				responseBase.setCode(0);
				responseBase.setMessage("您已提交审核，请勿重复提交！");
				return responseBase;
			}

			String reviewuuid = UUID.randomUUID().toString();
			userReviewInfo.setUuid(reviewuuid);
			//修改状态为初审
			userReviewInfo.setReviewStatus(1);
			userReviewInfo.setReviewType(1);
			//重新提交申请，都允许银行审核
			userReviewInfo.setIcbcSwitch(1);
			userReviewInfo.setCreateTime(LocalDateTime.now());
			//获取基地名称，战区名称，基地业务员名称
			if (userReviewInfoDTO.getUpOrgSeq() != null && userReviewInfoDTO.getUpOrgSeq() != "" && userReviewInfoDTO.getUpOrgSeq() != "null") {
				UserOrganization userOrganization = organizationMapper.selectById(userReviewInfoDTO.getUpOrgSeq());
				userReviewInfo.setUpOrgZone(userOrganization.getOrgName());
			}
			if (userReviewInfoDTO.getBaseCode() != null && userReviewInfoDTO.getBaseCode() != "" && userReviewInfoDTO.getBaseCode() != "null") {
				UserOrganization userOrganization = organizationMapper.selectById(userReviewInfoDTO.getBaseCode());
				userReviewInfo.setBaseName(userOrganization.getOrgName());
			}
			if (userReviewInfoDTO.getBasePersonCode() != null && userReviewInfoDTO.getBasePersonCode() != "" && userReviewInfoDTO.getBasePersonCode() != "null") {
				UserInfo userInfo = userInfoMapper.selectById(userReviewInfoDTO.getBasePersonCode());
				userReviewInfo.setBasePersonName(userInfo.getRealName());
			}
			//查询当前授权码下最大得编辑记录数 然后加1
//			Integer number = mapper.getSumNumber(userReviewInfo.getAuthCode());
//			userReviewInfo.setNumber(number+1);
			mapper.insert(userReviewInfo);
			//给记录表插入信息
			UserReviewRecord userReviewRecord = new UserReviewRecord();
			String uuid = UUID.randomUUID().toString();
			userReviewRecord.setUuid(uuid);
			userReviewRecord.setReviewId(reviewuuid);
			//审核状态审核状态(1-已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过)
			userReviewRecord.setReviewStatus(1);
			userReviewRecord.setReviewTime(LocalDateTime.now());
			userReviewRecordMapper.insert(userReviewRecord);
		}
		//查看建档数据 更新建档数据
		List<UserIcbcFilingDTO> userIcbcFilingDTOList = userReviewInfoDTO.getUserIcbcFilingDTOList();
		//将所有数据修改未失效状态
		UserIcbcFiling userIcbcFiling = new UserIcbcFiling();
		userIcbcFiling.setReviewId(userReviewInfo.getUuid());
		userIcbcFiling.setStatus(0);
		userIcbcFilingMapper.update(userIcbcFiling, new QueryWrapper<UserIcbcFiling>().eq("review_id", userReviewInfo.getUuid()));
		//重新插入数据
		if (userIcbcFilingDTOList != null && userIcbcFilingDTOList.size() > 0) {
			for (int i = 0; i < userIcbcFilingDTOList.size(); i++) {
				UserIcbcFiling userIcbcFilingNew = new UserIcbcFiling();
				String uuidFiling = UUID.randomUUID().toString();
				BeanUtils.copyProperties(userIcbcFilingDTOList.get(i), userIcbcFilingNew);
				userIcbcFilingNew.setUuid(uuidFiling);
				userIcbcFilingNew.setReviewId(userReviewInfo.getUuid());
				userIcbcFilingNew.setCreateTime(LocalDateTime.now());
				userIcbcFilingNew.setCreateUser(JwtTokenUtil.currUser());
				userIcbcFilingMapper.insert(userIcbcFilingNew);
			}
		}

		responseBase.setCode(1);
		responseBase.setMessage("成功");
		return responseBase;
	}

	public String getMessage(int fileType) {
//		1-法定代表人授权书,2-信息查询使用授权书,3-收单业务书,4-非金融企业划型信息采集表,5-对公客户信息采集表\r\n,
//					6-非自然人客户受益所有人采集表,7-单位税收居民声明文件，8-营业执照，9-专卖店门头照片，10-法人身份证正面照片，
//					11-法人身份证背面照片，12-结算账号照片，13-门店内景照片，14-门店收银台照片\r\n)',
		String memssage = "";
		switch (fileType) {
			case 1:
				memssage = "请重新上传【法定代表人授权书】";
				break;
			case 2:
				memssage = "请重新上传【信息查询使用授权书】";
				break;
			case 3:
				memssage = "请重新上传【收单业务书】";
				break;
			case 4:
				memssage = "请重新上传【非金融企业划型信息采集表】";
				break;
			case 5:
				memssage = "请重新上传【对公客户信息采集表】";
				break;
			case 6:
				memssage = "请重新上传【非自然人客户受益所有人采集表】";
				break;
			case 7:
				memssage = "请重新上传【单位税收居民声明文件】";
				break;
			case 8:
				memssage = "请重新上传【营业执照】";
				break;
			case 9:
				memssage = "请重新上传【专卖店门头照片】";
				break;
			case 10:
				memssage = "请重新上传【法人身份证正面照片】";
				break;
			case 11:
				memssage = "请重新上传【法人身份证背面照片】";
				break;
			case 12:
				memssage = "请重新上传【结算账号照片】";
				break;
			case 13:
				memssage = "请重新上传【门店内景照片】";
				break;
			case 14:
				memssage = "请重新上传【门店收银台照片】";
				break;
			default:
				memssage = "请联系管理员";
				break;

		}
		return memssage;
	}

	@Override
	public ResponseResultPage<UserReviewInfoDTO> queryUserReviewInfo(PageData<UserReviewRequest> pageData) {
		ResponseResultPage<UserReviewInfoDTO> result = new ResponseResultPage<>();
		try {
			Page<UserReviewInfoDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
			UserOrganization userOrganization = new UserOrganization();
			Page<UserReviewInfoDTO> pageResult = new Page<>();
			if (pageData.getCondition().getUpOrgSeq() != null && pageData.getCondition().getUpOrgSeq() != "") {
				userOrganization = organizationMapper.selectById(pageData.getCondition().getUpOrgSeq());
				if ("2".equals(userOrganization.getOrgType())) {//战区 根据组织编码查询，不变

				} else {//总部查所有，将seq设置为空wa
					pageData.getCondition().setUpOrgSeq(null);
				}
				String type = userOrganization.getOrgType();
				if ("1" == type || "1".equals(type)) {
					//总部
					pageData.getCondition().setUpOrgSeq(null);
					pageResult = mapper.queryStoreInfo(page, pageData.getCondition());
				} else if ("2" == type || "2".equals(type)) {
					//战区
					pageResult = mapper.queryStoreInfo(page, pageData.getCondition());
				} else if ("3" == type || "3".equals(type)) {
					//基地
					pageResult = mapper.queryStoreInfoByJD(page, pageData.getCondition(), pageData.getCondition().getUpOrgSeq());

				} else if ("4" == type || "4".equals(type)) {
					//门店
					pageResult = mapper.queryStoreInfoByMD(page, pageData.getCondition(), pageData.getCondition().getUpOrgSeq());
				}

			}
			result.setCode(1);
			result.setMessage("共有" + pageResult.getRecords().size() + "条门店申请信息");
			result.setRecords(pageResult.getRecords());
			result.setSize(pageData.getSize());
			result.setCurrent(pageData.getCurrent());
			result.setTotal(pageResult.getTotal());
		} catch (Exception ex) {
			log.error("OrganizationServiceImpl|queryUserReviewInfo->获取申请门店[" + ex.getMessage() + "]");
			result.setCode(0);
			result.setMessage(ex.getMessage());
		}
		return result;
	}

	/**
	 * 创建店长或者店主账号 和 店铺认证方式
	 */
	public void createStoreAccount(UserStoreInfo userReviewInfo, String userType, String password, String orgseq) {


		UserInfo userInfo = new UserInfo();
		UserLoginInfo userLoginInfo = new UserLoginInfo();
		UserUserRoleRelation userUserRoleRelation = new UserUserRoleRelation();
		userUserRoleRelation.setUserRoleRelationId(UUID.randomUUID().toString());
		String userId = UUID.randomUUID().toString();
		password = MD5Util.toMD5("888888");
		userInfo.setUserId(userId);
		if (userType == "5" || "5".equals(userType)) {//生成店长
			userInfo.setUserName(userReviewInfo.getStoreOwnerName());
			//初始密码为电话
			userInfo.setPassword(password);//店长电话
			userInfo.setTel(userReviewInfo.getStoreManagerPhone());
			userInfo.setUserName(userReviewInfo.getStoreManagerPhone());
			userInfo.setRealName(userReviewInfo.getStoreManagerName());
			userInfo.setStatus(1);
			userInfo.setIsLock(1);
			userInfo.setUserType("5");
			userInfo.setHeadPicUrl(userReviewInfo.getStoreManagerPic());
			userLoginInfo.setPassword(password);
			userinfoManager = userInfo;
			//分配店长权限
			UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("role_req", store_maneger));
			userUserRoleRelation.setRoleId(userRole.getRoleId());
		} else {//生成店主
			userInfo.setPassword(password);//店主电话
			userInfo.setTel(userReviewInfo.getStoreOwnerPhone());
			userInfo.setUserName(userReviewInfo.getStoreOwnerPhone());
			userInfo.setRealName(userReviewInfo.getStoreOwnerName());
			userInfo.setStatus(1);
			userInfo.setIsLock(1);
			userInfo.setUserType("4");
			userInfo.setHeadPicUrl(userReviewInfo.getStoreOwnerPic());
			userLoginInfo.setPassword(password);
			userinfoOwn = userInfo;
			//分配店主权限
			UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("role_req", store_owner));
			userUserRoleRelation.setRoleId(userRole.getRoleId());
			userLoginInfo.setUuid(UUID.randomUUID().toString());
			userLoginInfo.setOrgSeq(orgseq);
			userLoginInfo.setAuthCode(userReviewInfo.getAuthCode());
			userLoginInfo.setUserId(userId);
			userLoginInfo.setStatus(1);
			userLoginInfo.setCreateTime(LocalDateTime.now());
			userLoginInfo.setUpdateTime(LocalDateTime.now());
			userLoginInfo.setCreateUser(JwtTokenUtil.currUser());
			userLoginInfo.setUpdateUser(JwtTokenUtil.currUser());
			userLoginInfoMapper.insert(userLoginInfo);
		}
		//废弃
//		userInfo.setOrgSeq(userReviewInfo.getOrgSeq());
		userInfo.setUserType(userType);
		userInfo.setCreateTime(new Date());
		userInfo.setSalt((new Random().nextInt(100)) + "");//盐

		// 1.0 向 用户基本信息 t_user_info 插入数据
		UserInfoMapperMapper.insert(userInfo);

		userUserRoleRelation.setUserId(userId);
		userUserRoleRelation.setCreateUser(JwtTokenUtil.currUser());
		userUserRoleRelation.setStatus(1);
		userUserRoleRelation.setCreateTime(LocalDateTime.now());
		uerUserRoleRelationMapper.insert(userUserRoleRelation);
		//调用短信平台
		//生成店长账号
		UmsParameter umsParameter = new UmsParameter();
		try {
			if (userType == "4" || "4".equals(userType)) {//生成店主
				//生成店主账号
				umsParameter.setParmA(userReviewInfo.getAuthCode());
				umsParameter.setParmB("888888");
				umsParameter.setPhone(userReviewInfo.getStoreOwnerPhone());
				umsParameter.setCode(code);
				foreignService.getUmsDto(umsParameter);
			}
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
	}

	@Override
	@Transactional
	public ResponseBase preliminaryExamination(PreliminaryExamination preliminaryExamination) {
		ResponseBase responseBase = new ResponseBase();
		//非该战区人员无法审核
		UserReviewInfo userReviewMap = userReviewInfoMapper.selectOne(new QueryWrapper<UserReviewInfo>().eq("uuid", preliminaryExamination.getUuid()));
		List<UserOrgRelation> userlist = userOrgRelationMapper.selectList(new QueryWrapper<UserOrgRelation>().eq("user_id", JwtTokenUtil.currUser()));
		Boolean preliminary = false;
		if (userlist != null && userlist.size() > 0) {
			for (int i = 0; i < userlist.size(); i++) {
				String nowOrgseq = userlist.get(i).getOrgSeq();
				if (nowOrgseq == userReviewMap.getUpOrgSeq() || nowOrgseq.equals(userReviewMap.getUpOrgSeq())) {
					preliminary = true;
					break;
				}
			}
		}
		if (!preliminary) {
			responseBase.setCode(0);
			responseBase.setMessage("没有审核权限！");
			return responseBase;
		}
		UserReviewRecord userReviewRecord = new UserReviewRecord();
//		String uuid = UUID.randomUUID().toString();
//		userReviewRecord.setUuid(uuid);
		if (userReviewMap != null) {
			userReviewMap.setReviewUser(JwtTokenUtil.currUser());
			userReviewMap.setReviewOpinion(preliminaryExamination.getReviewOpinion());
			userReviewRecord.setReviewOption(preliminaryExamination.getReviewOpinion());
			userReviewRecord.setReviewUser(JwtTokenUtil.currUser());
			userReviewRecord.setReviewTime(LocalDateTime.now());
			userReviewRecord.setReviewId(preliminaryExamination.getUuid());
			//（审核状态(1-已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过)）
			if (preliminaryExamination.getReviewStatus() == 1) {//初核通过
				userReviewRecord.setReviewStatus(2);
				//如果 审核通过，则调用张亮提供的银行审核接口
				//判断当前审核是否已经通过银行建档
				int icbcReviewStatus = userReviewMap.getIcbcReviewStatus() == null ? 0 : userReviewMap.getIcbcReviewStatus();
				//如果银行通过审核 则不会再提交至银行
				if (icbcReviewStatus == 1) {
					userReviewMap.setReviewStatus(2);//设置为复核
				} else {
					//插入一条初审审核进度
					userReviewRecord.setUuid(UUID.randomUUID().toString());
					userReviewRecordMapper.insert(userReviewRecord);
					userReviewMap.setReviewStatus(6);//设置为银行审核
					userReviewRecord.setReviewOption("银行审核中");
					userReviewRecord.setReviewUser(JwtTokenUtil.currUser());
					userReviewRecord.setReviewTime(LocalDateTime.now());
					userReviewRecord.setReviewStatus(6);
					//店铺建档信息
					UserReviewInfoDTO userReviewInfoDTO = new UserReviewInfoDTO();
					List<UserIcbcFilingDTO> userIcbcFilingDTOList = new ArrayList<>();
					BeanUtils.copyProperties(userReviewMap, userReviewInfoDTO);
					List<UserIcbcFiling> userIcbcFilingList = userIcbcFilingMapper.selectList(new QueryWrapper<UserIcbcFiling>().eq("review_id", userReviewMap.getUuid()).eq("status", 1));
					if (userIcbcFilingList != null && userIcbcFilingList.size() > 0) {
						for (int i = 0; i < userIcbcFilingList.size(); i++) {
							//查询模板路径
							UserIcbcTemp userIcbcTemp = userIcbcTempMapper.selectById(userIcbcFilingList.get(i).getFileTempId());
							UserIcbcTempDTO userIcbcTempDTO = new UserIcbcTempDTO();
							if (userIcbcTemp != null) {
								BeanUtils.copyProperties(userIcbcTemp, userIcbcTempDTO);
							}
							UserIcbcFilingDTO userIcbcFiling = new UserIcbcFilingDTO();
							BeanUtils.copyProperties(userIcbcFilingList.get(i), userIcbcFiling);
							userIcbcFiling.setUserIcbcTempDTO(userIcbcTempDTO);
							userIcbcFilingDTOList.add(userIcbcFiling);
						}
					}
					userReviewInfoDTO.setUserIcbcFilingDTOList(userIcbcFilingDTOList);
					//调用审核接口 RPC
					ResponseBase res = icbcInteractiveService.accountSubmit(userReviewInfoDTO);
					if (res.getCode() == 0) {
						//银行审核不通过
						//银行未通过，直接返回至用户申请
						userReviewMap.setIcbcReviewOpinion(res.getMessage());
						userReviewMap.setIcbcReviewStatus(2);
						//设置银行可审核
						userReviewMap.setIcbcSwitch(1);
						userReviewMap.setReviewStatus(8);
						userReviewMap.setUpdateTime(LocalDateTime.now());
						userReviewRecord.setUuid(UUID.randomUUID().toString());
						userReviewRecord.setReviewUser(JwtTokenUtil.currUser());
						userReviewRecord.setReviewTime(LocalDateTime.now());
						userReviewRecord.setReviewId(preliminaryExamination.getUuid());
						userReviewRecord.setReviewStatus(8);
						userReviewRecord.setReviewOption(res.getMessage());
						userReviewInfoMapper.updateById(userReviewMap);
						userReviewRecordMapper.insert(userReviewRecord);
//						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						responseBase.setCode(1);
						responseBase.setMessage("战区审核成功，银行资料审核异常，已退回专卖店!");
						return responseBase;
					}
				}
				userReviewMap.setReviewType(1);//状态为申请审核
			} else {
				userReviewMap.setReviewStatus(3);//设置为不通过
				userReviewMap.setReviewType(1);//状态为申请审核
				userReviewMap.setUpdateTime(LocalDateTime.now());
				userReviewRecord.setReviewStatus(3);
				userReviewRecord.setUuid(UUID.randomUUID().toString());
				userReviewInfoMapper.updateById(userReviewMap);
				userReviewRecordMapper.insert(userReviewRecord);
				responseBase.setCode(1);
				responseBase.setMessage("审核成功");
				return responseBase;
			}
			userReviewMap.setUpdateTime(LocalDateTime.now());
			int userMap = userReviewInfoMapper.updateById(userReviewMap);
			//插入日志表
			userReviewRecord.setUuid(UUID.randomUUID().toString());
			userReviewRecordMapper.insert(userReviewRecord);
			responseBase.setCode(1);
			responseBase.setMessage("审核成功");
		} else {
			responseBase.setCode(0);
			responseBase.setMessage("审核失败");
		}
		return responseBase;
	}

	@Override
	@Transactional
	public ResponseBase finalReview(PreliminaryExamination preliminaryExamination) {
		ResponseBase responseBase = new ResponseBase();
		UserReviewRecord userReviewRecord = new UserReviewRecord();
		String uuid = UUID.randomUUID().toString();
		userReviewRecord.setUuid(uuid);
		UserReviewInfo userReviewMap = userReviewInfoMapper.selectOne(new QueryWrapper<UserReviewInfo>().eq("uuid", preliminaryExamination.getUuid()));
		try {
			if (userReviewMap != null) {
				List<UserOrgRelation> userlist = userOrgRelationMapper.selectList(new QueryWrapper<UserOrgRelation>().eq("user_id", JwtTokenUtil.currUser()));
				Boolean preliminary = false;
				if (userlist != null && userlist.size() > 0) {
					for (int i = 0; i < userlist.size(); i++) {
						//判断是否是总部
						int type = userlist.get(i).getUserType();
						if (type == 1) {
							preliminary = true;
						}
					}
				}
				if (!preliminary) {
					responseBase.setCode(0);
					responseBase.setMessage("没有审核权限！");
					return responseBase;
				}
				//判断是否重复点击
				//如果当前店铺已经生成，不支持重复点击
				UserStoreInfo userStoreInfo = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("auth_code", userReviewMap.getAuthCode()).groupBy("auth_code"));
				if (userStoreInfo != null) {
					responseBase.setMessage("店铺已经生成！请前往【店铺档案】查看");
					return responseBase;
				}
				RLock lock = redissonClient.getLock("store_lock" + userReviewMap.getAuthCode());
				boolean res = lock.tryLock(30, 30, TimeUnit.SECONDS);
				if (res) {
					try {
						userReviewMap.setFinalReviewUser(JwtTokenUtil.currUser());
						userReviewMap.setFinalReviewOpinion(preliminaryExamination.getFinalReviewOpinion());
						userReviewRecord.setReviewOption(preliminaryExamination.getFinalReviewOpinion());
						userReviewRecord.setReviewUser(JwtTokenUtil.currUser());
						userReviewRecord.setReviewTime(LocalDateTime.now());
						userReviewRecord.setReviewId(preliminaryExamination.getUuid());
						//（审核状态(1-已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过)）
						if ("1".equals(preliminaryExamination.getReviewStatus()) || Integer.parseInt(preliminaryExamination.getReviewStatus().toString()) == 1) {
							//设置为复审通过
							userReviewMap.setReviewStatus(5);
							userReviewMap.setReviewType(1);
							userReviewRecord.setReviewStatus(5);
						} else {
							//设置为复审不通过
							userReviewMap.setReviewStatus(4);
							userReviewMap.setReviewType(1);//设置为申请审核
							userReviewMap.setUpdateTime(LocalDateTime.now());
							userReviewInfoMapper.updateById(userReviewMap);
							userReviewRecord.setReviewStatus(4);
							userReviewRecordMapper.insert(userReviewRecord);
							responseBase.setCode(1);
							responseBase.setMessage("审核成功");
							return responseBase;
						}
						userReviewMap.setUpdateTime(LocalDateTime.now());
						userReviewInfoMapper.updateById(userReviewMap);
						//插入日志表
						userReviewRecordMapper.insert(userReviewRecord);

						String userType = "5";//用户类型(1集团人员2战区人员3基地人员4店主5店长6店员)
						// 生成组织机构信息
						UserOrganization orgNization = new UserOrganization();
						String orgseq = UUID.randomUUID().toString();
						orgNization.setOrgSeq(orgseq);
						orgNization.setOrgName(userReviewMap.getStoreName());
						orgNization.setUpOrgSeq(userReviewMap.getBaseCode());//基地编码
						orgNization.setOrgType("4");//门店
						orgNization.setStatus(1);
						orgNization.setCreateUser(JwtTokenUtil.currUser());
						orgNization.setCreateTime(LocalDateTime.now());
						orgNization.setUpdateUser(JwtTokenUtil.currUser());
						orgNization.setUpdateTime(LocalDateTime.now());
						organizationMapper.insert(orgNization);
						//生成门店信息
						UserStoreInfo userStoreinfo = new UserStoreInfo();
						BeanUtils.copyProperties(userReviewMap, userStoreinfo);
						String mdUUid = UUID.randomUUID().toString();
						//防止将申请信息的uuid 将店铺uuid覆盖
						DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDateTime startDateTime = LocalDate.parse(userReviewMap.getOpenDate(), df).atStartOfDay();
						userStoreinfo.setWechatId(userReviewMap.getWechatId());
						userStoreinfo.setOpenDate(startDateTime);
						userStoreinfo.setUuid(mdUUid);
						userStoreinfo.setOrgSeq(orgseq);
						userStoreinfo.setStatus(1);
						userStoreinfo.setOpenStatus(1);
						userStoreinfo.setCreateUser(JwtTokenUtil.currUser());
						userStoreinfo.setUpdateUser(JwtTokenUtil.currUser());
						userStoreinfo.setCreateTime(LocalDateTime.now());
						userStoreinfo.setUpdateTime(LocalDateTime.now());
						userStoreinfo.setStoreCode(generateMemberSeq(MEMBER_SEQ_KEY));
//				//初始化门店等级 默认 白银等级
//				StoreGradelevelRule storeGradelevelRule = storeGradelevelRuleMapper.selectOne
//						(new QueryWrapper<StoreGradelevelRule>().eq("begin_score",0).eq("STATUS",1));
//				userStoreinfo.setGradelevelId(storeGradelevelRule.getUuid());
						storeMapper.insert(userStoreinfo);
						//绑定工行建库信息
						List<UserIcbcFiling> userIcbcFilingList = userIcbcFilingMapper.selectList(new QueryWrapper<UserIcbcFiling>().eq("review_id", userReviewMap.getUuid()).eq("status", 1));
						if (userIcbcFilingList != null && userIcbcFilingList.size() > 0) {
							for (int i = 0; i < userIcbcFilingList.size(); i++) {
								userIcbcFilingList.get(i).setStoreId(mdUUid);
								userIcbcFilingMapper.updateById(userIcbcFilingList.get(i));
							}
						}
						//初始化生成门店积分信息
						storeBonuspoint = new StoreBonuspoint();
						storeBonuspoint.setUuid(UUID.randomUUID().toString());
						storeBonuspoint.setOrgSeq(orgseq);
						storeBonuspoint.setCurrentScore(new BigDecimal("0"));
						storeBonuspoint.setTotalScore(new BigDecimal("0"));
						storeBonuspoint.setCreaterUser(JwtTokenUtil.currUser());
						storeBonuspoint.setUpdateUser(JwtTokenUtil.currUser());
						storeBonuspoint.setCreateTime(LocalDateTime.now());
						storeBonuspoint.setUpdateTime(LocalDateTime.now());
						storeBonuspointMapper.insert(storeBonuspoint);
						//初始化商品强上架，初始化商品库存
						ResponseBase ress = goodsForeignService.initGoods(userStoreinfo.getOrgSeq(), userStoreinfo.getUpOrgSeq());
						if (ress.getCode() == 1) {
							System.out.println("----------初始化商品强上架操作成功----------");
						} else {
							System.out.println("----------初始化商品强上架操作失败----------");
						}
						//通过电话号码判断是否已经存在店主账号,不存在则生成，存在则不做操作
						Boolean typeOwn = true;
						Boolean typeManager = true;
						userinfoOwn = userInfoMapper.selectOne
								(new QueryWrapper<UserInfo>().eq("tel", userReviewMap.getStoreOwnerPhone()).groupBy("tel"));
						Random random = new Random();
						int randomNum = random.nextInt(1000000);
						String password1 = String.format("%06d", randomNum);
						if (userinfoOwn == null) {
							//店主
							userType = "4";
							createStoreAccount(userStoreinfo, userType, password1, orgseq);
						} else {
							String password4 = MD5Util.toMD5("888888");
							UserUserRoleRelation userUserRoleRelation = new UserUserRoleRelation();
							userUserRoleRelation.setUserRoleRelationId(UUID.randomUUID().toString());
							//分配店主权限
							UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("role_req", store_owner));
							userUserRoleRelation.setRoleId(userRole.getRoleId());
							userUserRoleRelation.setUserId(userinfoOwn.getUserId());
							userUserRoleRelation.setCreateUser(JwtTokenUtil.currUser());
							userUserRoleRelation.setStatus(1);
							userUserRoleRelation.setCreateTime(LocalDateTime.now());
							uerUserRoleRelationMapper.insert(userUserRoleRelation);

							UserLoginInfo userLoginInfo = new UserLoginInfo();
							userLoginInfo.setUuid(UUID.randomUUID().toString());
							userLoginInfo.setOrgSeq(orgseq);
							userLoginInfo.setAuthCode(userReviewMap.getAuthCode());
							userLoginInfo.setUserId(userinfoOwn.getUserId());
							userLoginInfo.setPassword(password4);
							userLoginInfo.setStatus(1);
							userLoginInfo.setCreateTime(LocalDateTime.now());
							userLoginInfo.setUpdateTime(LocalDateTime.now());
							userLoginInfo.setCreateUser(JwtTokenUtil.currUser());
							userLoginInfo.setUpdateUser(JwtTokenUtil.currUser());
							userLoginInfoMapper.insert(userLoginInfo);
							//调用短信平台
							//发送店主短信
							UmsParameter umsParameter = new UmsParameter();
							umsParameter.setParmA(userReviewMap.getAuthCode());
							umsParameter.setParmB("888888");
							umsParameter.setPhone(userStoreinfo.getStoreOwnerPhone());
							umsParameter.setCode(code);
							foreignService.getUmsDto(umsParameter);
						}
						userinfoManager = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel", userReviewMap.getStoreManagerPhone()).groupBy("tel"));
						String password2 = String.format("%06d", randomNum);
						if (userinfoManager == null) {
							typeManager = false;
							//店长
							userType = "5";
							createStoreAccount(userStoreinfo, userType, password2, orgseq);
						} else {
							typeManager = false;
							String password3 = MD5Util.toMD5("888888");
							UserUserRoleRelation userUserRoleRelation = new UserUserRoleRelation();
							userUserRoleRelation.setUserRoleRelationId(UUID.randomUUID().toString());
							//分配店长权限
							UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("role_req", store_maneger));
							userUserRoleRelation.setRoleId(userRole.getRoleId());
							userUserRoleRelation.setUserId(userinfoManager.getUserId());
							userUserRoleRelation.setCreateUser(JwtTokenUtil.currUser());
							userUserRoleRelation.setStatus(1);
							userUserRoleRelation.setCreateTime(LocalDateTime.now());
							uerUserRoleRelationMapper.insert(userUserRoleRelation);
						}
						//生成店主账号
						userOrgRelation = new UserOrgRelation();
						userOrgRelation.setUuid(UUID.randomUUID().toString());
						userOrgRelation.setUserId(userinfoOwn.getUserId());
						userOrgRelation.setOrgSeq(orgseq);
						userOrgRelation.setUserType(4);
						userOrgRelation.setStatus(userinfoOwn.getStatus());
						userOrgRelation.setCreateUser(JwtTokenUtil.currUser());
						userOrgRelation.setUpdateUser(JwtTokenUtil.currUser());
						userOrgRelation.setCreateTime(LocalDateTime.now());
						userOrgRelation.setUpdateTime(LocalDateTime.now());
						userOrgRelationMapper.insert(userOrgRelation);

						//生成店长账号
						userOrgRelation = new UserOrgRelation();
						userOrgRelation.setUuid(UUID.randomUUID().toString());
						userOrgRelation.setUserId(userinfoManager.getUserId());
						userOrgRelation.setOrgSeq(orgseq);
						userOrgRelation.setUserType(5);
						userOrgRelation.setStatus(userinfoManager.getStatus());
						userOrgRelation.setCreateUser(JwtTokenUtil.currUser());
						userOrgRelation.setUpdateUser(JwtTokenUtil.currUser());
						userOrgRelation.setCreateTime(LocalDateTime.now());
						userOrgRelation.setUpdateTime(LocalDateTime.now());
						userOrgRelationMapper.insert(userOrgRelation);
						responseBase.setCode(1);
						responseBase.setMessage("审核成功");
					} catch (Exception e) {
						throw new Exception(e.getMessage());
					} finally {
						try {
							lock.unlock();
						} catch (Exception e) {
							log.error("店铺审核异常：事物锁释放异常！");
						}
					}
				} else {
					throw new Exception("店铺审核异常，事物锁获取异常");
				}
			} else {
				responseBase.setCode(0);
				responseBase.setMessage("审核失败");
			}
		} catch (Exception e) {
			responseBase.setCode(0);
			responseBase.setMessage("审核失败----" + e);
			e.printStackTrace();
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return responseBase;
	}

	@Override
	public ResponseBase addStore(UserStoreInfoDTO userStoreInfoDTO) {
		ResponseBase responseBase = new ResponseBase();
		userStoreInfo = new UserStoreInfo();
		String userType = "5";//用户类型(1集团人员2战区人员3基地人员4店主5店长6店员)
		String password1 = MD5Util.toMD5("888888");
		BeanUtils.copyProperties(userStoreInfoDTO, userStoreInfo);
		if (userStoreInfo.getUuid() != null && userStoreInfo.getUuid() != "") {
			storeMapper.insert(userStoreInfo);
		} else {
			storeMapper.updateById(userStoreInfo);
		}
		Random random = new Random();
		int randomNum = random.nextInt(1000000);
		//生成店长账号
		createStoreAccount(userStoreInfo, userType, password1, userStoreInfoDTO.getOrgSeq());
		//通过电话号码判断是否已经存在店主账号,不存在则生成，存在则不做操作
		UserInfo userinfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel", userStoreInfo.getStoreOwnerPhone()));
		if (userinfo == null) {
			userType = "4";
			String password2 = String.format("%06d", randomNum);
			createStoreAccount(userStoreInfo, userType, password1, userStoreInfoDTO.getOrgSeq());
		}
		responseBase.setCode(1);
		responseBase.setMessage("成功");
		return responseBase;
	}

	@Override
	@Transactional
	public ResponseBase editClerk(UserClerkDTO userclerk) {
		ResponseBase responseBase = new ResponseBase();
		try {
			//店员
			if (StringUtils.isEmpty(userclerk.getUserType()) || userclerk.getUserType() == "6" || "6".equals(userclerk.getUserType())) {
				//是否是信息完备
				Integer isWS = 1;
				if (StringUtils.isEmpty(userclerk.getTel()) || StringUtils.isEmpty(userclerk.getHeadPicUrl())
						|| StringUtils.isEmpty(userclerk.getIdCard())
						|| StringUtils.isEmpty(userclerk.getRealName())
						|| StringUtils.isEmpty(userclerk.getStatus() + "")) {
					isWS = 0;
				}
				if (userclerk.getUserId() != null) {
					//判断是否是停用 如果停用则修改手机号为A+tel
					UserInfo userInfos = new UserInfo();
					if (userclerk.getTel() == null) {
						//从列表进入  所以是只是修改启用状态，这个时候需要更改电话号码为 Atel
						userInfos = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("user_id", userclerk.getUserId()));
					} else {
						//从编辑信息进入 此时电话号码不从库里面查询
						userInfos.setTel(userclerk.getTel());
					}
					Integer status = userclerk.getStatus();
					if (status == 0) {
						//停用  查询当前号码 是否已经被注销修改过，多少次就多加几个A
						UserInfo count = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().like("tel", userInfos.getTel()).orderByDesc("tel").last("limit 1"));
						StringBuilder A = new StringBuilder("A");
//					for (int i = 0; i < count; i++) {
//						A.append("A");
//					}
						userclerk.setTel(A + count.getTel());
					} else {
						userclerk.setTel(userInfos.getTel().replaceAll("[a-zA-Z]", ""));
					}
					//编辑(不会更改组织信息)
					int count = userInfoMapper.selectCount(new QueryWrapper<UserInfo>().eq("tel", userclerk.getTel()).ne("user_id", userclerk.getUserId()));
					if (count > 0) {
						responseBase.setCode(0);
						responseBase.setMessage("当前已存在该手机号为启用状态！");
						return responseBase;
					}
					//判断是否有员工编号
					UserInfo user = userInfoMapper.selectById(userclerk.getUserId());
					if (StringUtils.isEmpty(user.getEmployeeNo())) {
						String employeeNo = generateMemberSeq(SEQ_KEY);
						userclerk.setEmployeeNo(employeeNo);
					}
					UserInfo userInfo = new UserInfo();
					BeanUtils.copyProperties(userclerk, userInfo);
					userInfo.setUpdateTime(LocalDateTime.now());
					userInfo.setUpdateUser(JwtTokenUtil.currUser()).setIsInfoComplete(isWS);
					userInfoMapper.updateById(userInfo);
					responseBase.setCode(1);
					responseBase.setMessage("编辑成功");
					//更新data库
					storeActivationService.editClerk(userclerk, null, null);
				} else {
					//新增
					int count = userInfoMapper.selectCount(new QueryWrapper<UserInfo>().eq("tel", userclerk.getTel()));
					if (count > 0) {
						responseBase.setCode(0);
						responseBase.setMessage("当前已存在该手机号为启用状态！");
						return responseBase;
					}
					UserInfo userInfo = new UserInfo();
					BeanUtils.copyProperties(userclerk, userInfo);
					String userId = UUID.randomUUID().toString();
					//生产员工编号
					String employeeNo = generateMemberSeq(SEQ_KEY);
					userInfo.setUserId(userId)
							.setEmployeeNo(employeeNo)
							.setUserType("6")
							.setCreateTime(new Date())
							.setCreateUser(JwtTokenUtil.currUser())
							.setUpdateTime(LocalDateTime.now())
							.setUpdateUser(JwtTokenUtil.currUser())
							.setIsInfoComplete(isWS);
					userInfoMapper.insert(userInfo);
					String uuid = UUID.randomUUID().toString();
					UserOrgRelation userOrgRelation = new UserOrgRelation()
							.setUuid(uuid)
							.setUserId(userInfo.getUserId())
							.setUserType(6)
							.setStatus(1)
							.setOrgSeq(userclerk.getOrgSeq())
							.setCreateUser(JwtTokenUtil.currUser())
							.setCreateTime(LocalDateTime.now())
							.setUpdateUser(JwtTokenUtil.currUser())
							.setUpdateTime(LocalDateTime.now());
					userOrgRelationMapper.insert(userOrgRelation);
					responseBase.setCode(1);
					responseBase.setMessage("新增成功");
					//更新data库
					userclerk.setIsInfoComplete(isWS);
					userclerk.setStatus(userInfo.getStatus());
					userclerk.setEmployeeNo(employeeNo);
					storeActivationService.editClerk(userclerk, userId, uuid);
				}
				if (isWS == 1) {
					//如果 当前店员中都已经完善信息，则更新店铺是否完善信息为 1
					// type=1 说明 除了当前店员，其余所有店员中都已经完善，
					//	type=0 说明 除了当前店员，其余所有店员中还有未完善得，
					Integer type = userOrgRelationMapper.getIsWs(userclerk.getOrgSeq(), null);
					storeMapper.update(new UserStoreInfo().setIsInfoComplete(type).setUpdateTime(LocalDateTime.now()).setUpdateUser(JwtTokenUtil.currUser()), new QueryWrapper<UserStoreInfo>()
							.eq("org_seq", userclerk.getOrgSeq()));
				} else {
					storeMapper.update(new UserStoreInfo().setIsInfoComplete(isWS).setUpdateTime(LocalDateTime.now()).setUpdateUser(JwtTokenUtil.currUser()), new QueryWrapper<UserStoreInfo>()
							.eq("org_seq", userclerk.getOrgSeq()));
				}
			} else {
				//店长店主
				if (userclerk.getUserId() != null) {
					if (userclerk.getStatus() == 1 || "1".equals(userclerk.getStatus())) {
						//编辑
						int count = userInfoMapper.selectCount(new QueryWrapper<UserInfo>().eq("tel", userclerk.getTel()).ne("user_id", userclerk.getUserId()));
						if (count > 0) {
							responseBase.setCode(0);
							responseBase.setMessage("当前已存在该手机号为启用状态！");
							return responseBase;
						}
						//判断是否有员工编号
						UserInfo user = userInfoMapper.selectById(userclerk.getUserId());
						if (StringUtils.isEmpty(user.getEmployeeNo())) {
							String employeeNo = generateMemberSeq(SEQ_KEY);
							userclerk.setEmployeeNo(employeeNo);
						}
						UserInfo userInfo = new UserInfo();
						BeanUtils.copyProperties(userclerk, userInfo);
						userInfo.setUpdateTime(LocalDateTime.now());
						userInfoMapper.updateById(userInfo);
						//更新店铺表 查询orgseq
						userOrgRelationMapper.updateByStore(userclerk, JwtTokenUtil.currUser());
						//更新data库
						storeActivationService.editClerk(userclerk, null, null);
						responseBase.setCode(1);
						responseBase.setMessage("编辑成功");
					} else {
						responseBase.setCode(0);
						responseBase.setMessage("店主店长信息不能直接禁用或者删除");
						return responseBase;
					}
				} else {
					responseBase.setCode(0);
					responseBase.setMessage("店主或者店长标识信息不能为空");
					return responseBase;
				}
			}
		} catch (Exception e) {
			responseBase.setCode(0);
			responseBase.setMessage("系统异常");
			log.error("新增或编辑店员信息失败:{}", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return responseBase;
	}

	/**
	 * 编辑巡店店员接口
	 *
	 * @param userclerk
	 * @return
	 */
	@Override
	@Transactional
	public ResponseBase editStoreClerk(UserClerkDTO userclerk) {
		ResponseBase responseBase = new ResponseBase();
		try {
			if (userclerk.getUserId() != null) {
				//编辑(不会更改组织信息)
				int count = userInfoMapper.selectCount(new QueryWrapper<UserInfo>().eq("tel", userclerk.getTel()).ne("user_id", userclerk.getUserId()));
				if (count > 0) {
					responseBase.setCode(0);
					responseBase.setMessage("该电话号码已经存在！");
					return responseBase;
				}
				UserInfo userInfo = new UserInfo();
				BeanUtils.copyProperties(userclerk, userInfo);
				userInfo.setUpdateTime(LocalDateTime.now());
				userInfo.setUpdateUser(JwtTokenUtil.currUser());
				userInfoMapper.updateById(userInfo);
				responseBase.setCode(1);
				responseBase.setMessage("编辑成功");
			} else {
				//新增
				int count = userInfoMapper.selectCount(new QueryWrapper<UserInfo>().eq("tel", userclerk.getTel()));
				if (count > 0) {
					responseBase.setCode(0);
					responseBase.setMessage("该电话号码已经存在！");
					return responseBase;
				}
				UserInfo userInfo = new UserInfo();
				BeanUtils.copyProperties(userclerk, userInfo);
				userInfo.setUserId(UUID.randomUUID().toString())
						.setUserType("6")
						.setCreateTime(new Date())
						.setCreateUser(JwtTokenUtil.currUser())
						.setUpdateTime(LocalDateTime.now())
						.setUpdateUser(JwtTokenUtil.currUser());
				userInfoMapper.insert(userInfo);
				UserOrgRelation userOrgRelation = new UserOrgRelation()
						.setUuid(UUID.randomUUID().toString())
						.setUserId(userInfo.getUserId())
						.setUserType(6)
						.setStatus(1)
						.setOrgSeq(userclerk.getOrgSeq())
						.setCreateUser(JwtTokenUtil.currUser())
						.setCreateTime(LocalDateTime.now())
						.setUpdateUser(JwtTokenUtil.currUser())
						.setUpdateTime(LocalDateTime.now());
				userOrgRelationMapper.insert(userOrgRelation);

				UserLoginInfo userLoginInfo = new UserLoginInfo();
				userLoginInfo.setPassword(MD5Util.toMD5("1"));//默认密码是1
				userLoginInfo.setUuid(UUID.randomUUID().toString());
				userLoginInfo.setOrgSeq(userclerk.getOrgSeq());
				userLoginInfo.setAuthCode(userclerk.getTel());
				userLoginInfo.setUserId(userInfo.getUserId());
				userLoginInfo.setStatus(1);
				userLoginInfo.setCreateTime(LocalDateTime.now());
				userLoginInfo.setUpdateTime(LocalDateTime.now());
				userLoginInfo.setCreateUser(JwtTokenUtil.currUser());
				userLoginInfo.setUpdateUser(JwtTokenUtil.currUser());
				userLoginInfoMapper.insert(userLoginInfo);

				responseBase.setCode(1);
				responseBase.setMessage("新增成功");
			}

		} catch (Exception e) {
			responseBase.setCode(0);
			responseBase.setMessage("系统异常");
			log.error("新增或编辑店员信息失败:{}", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return responseBase;
	}

	@Override
	public ResponseResultPage<UserInfoDTO> qryClerk(PageData<UserClerkRequest> userclerkPage) {
		ResponseResultPage<UserInfoDTO> result = new ResponseResultPage<>();
		try {
			Page<UserInfoDTO> page = new Page<>(userclerkPage.getCurrent(), userclerkPage.getSize());
			JwtTokenUtil.currUserInfo();
			Page<UserInfoDTO> pageResult = userInfoMapper.qryClerk(page, userclerkPage.getCondition());
			result.setCode(1);
			result.setMessage("共有" + pageResult.getRecords().size() + "条店员信息");
			result.setRecords(pageResult.getRecords());
			result.setSize(userclerkPage.getSize());
			result.setCurrent(userclerkPage.getCurrent());
			result.setTotal(pageResult.getTotal());
		} catch (Exception ex) {
			log.error("qryClerk->获取店员信息[" + ex.getMessage() + "]");
			result.setCode(0);
			result.setMessage(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional
	public ResponseResultPage<UserQryStoreDTO> qryStore(PageData<UserStoreRequest> userStoreInfo) {
		ResponseResultPage<UserQryStoreDTO> result = new ResponseResultPage<>();
		try {
			//添加排序
			List<SortDTO> listSort = userStoreInfo.getCondition().getSortList();
			StringBuffer str = new StringBuffer();
			if (listSort != null && listSort.size() > 0) {
				for (int i = 0; i < listSort.size(); i++) {
					str.append(listSort.get(i).getSortField());
					str.append(" ");
					str.append(listSort.get(i).getSort());
					if (i < listSort.size() - 1) {
						str.append(",");
					}
				}
			}
			Page<UserQryStoreDTO> page = new Page<>(userStoreInfo.getCurrent(), userStoreInfo.getSize());
			Page<UserQryStoreDTO> pageResult = new Page<UserQryStoreDTO>();
			if (userStoreInfo.getCondition().getOrgSeq() != null) {
				UserOrganizationRequest userOrganizationRequest = foreignService.getOrganizationInfo(userStoreInfo.getCondition().getOrgSeq());
				String type = userOrganizationRequest.getOrgType();
				ArrayList<String> list = new ArrayList<>();
				if (type == "1" || "1".equals(type)) {
					//总部
					pageResult = userInfoMapper.qryStore(page, userStoreInfo.getCondition(), type, str.toString());
				} else if (type == "2" || "2".equals(type)) {
					//战区
					pageResult = userInfoMapper.qryStore(page, userStoreInfo.getCondition(), type, str.toString());
				} else if (type == "3" || "3".equals(type)) {
					//基地
					pageResult = userInfoMapper.qryStore(page, userStoreInfo.getCondition(), type, str.toString());
				} else if (type == "4" || "4".equals(type)) {
					//门店
					pageResult = userInfoMapper.qryStore(page, userStoreInfo.getCondition(), type, str.toString());
				}
			}
			result.setCode(1);
			result.setMessage("共有" + pageResult.getRecords().size() + "条店铺信息");
			result.setRecords(pageResult.getRecords());
			result.setSize(userStoreInfo.getSize());
			result.setCurrent(userStoreInfo.getCurrent());
			result.setTotal(pageResult.getTotal());
		} catch (Exception ex) {
			log.error("qryStore->获取店铺信息[" + ex.getMessage() + "]");
			result.setCode(0);
			result.setMessage(ex.getMessage());
		}
		return result;
	}

	@Override
	public ResponseBase storeAdministration(String uuid, Integer openStatus) {
		ResponseBase responseBase = new ResponseBase();
		UserStoreInfo userStore = new UserStoreInfo();
		if (uuid != null && uuid != "") {
			userStore.setUuid(uuid);
			userStore.setOpenStatus(openStatus);
			userStore.setUpdateUser(JwtTokenUtil.currUser());
			userStore.setUpdateTime(LocalDateTime.now());
			storeMapper.update(userStore, new QueryWrapper<UserStoreInfo>().eq("uuId", uuid));
			responseBase.setCode(1);
			responseBase.setMessage("操作成功");
		} else {
			responseBase.setCode(0);
			responseBase.setMessage("uuid或openStatus参数不能为空");
		}
		return responseBase;
	}

	@Override
	public ResponseResult queryReviewInfo(String uuId) {
		ResponseResult<UserReviewInfoDTO> responseResult = new ResponseResult<UserReviewInfoDTO>();
		UserReviewInfo userReviewInfo = userReviewInfoMapper.selectById(uuId);
		List<UserReviewRecordDTO> userReviewRecordDTOSList = new ArrayList<UserReviewRecordDTO>();
		List<UserIcbcFilingDTO> UserIcbcFilingDTOList = new ArrayList<UserIcbcFilingDTO>();
		if (userReviewInfo != null) {
			//店铺申请流程信息
			List<UserReviewRecord> userRecord = userReviewRecordMapper.selectList(new QueryWrapper<UserReviewRecord>().eq("review_id", uuId));
			if (userRecord != null && userRecord.size() > 0) {
				for (int i = 0; i < userRecord.size(); i++) {
					UserReviewRecordDTO userReviewRecordD = new UserReviewRecordDTO();
					BeanUtils.copyProperties(userRecord.get(i), userReviewRecordD);
					userReviewRecordDTOSList.add(userReviewRecordD);
				}
			}
			//店铺建档信息
			List<UserIcbcFiling> userIcbcFilingList = userIcbcFilingMapper.selectList(new QueryWrapper<UserIcbcFiling>().eq("review_id", uuId).eq("status", 1));
			if (userIcbcFilingList != null && userIcbcFilingList.size() > 0) {
				for (int i = 0; i < userIcbcFilingList.size(); i++) {
					//查询模板路径
					UserIcbcTemp userIcbcTemp = userIcbcTempMapper.selectById(userIcbcFilingList.get(i).getFileTempId());
					UserIcbcTempDTO userIcbcTempDTO = new UserIcbcTempDTO();
					if (userIcbcTemp != null) {
						BeanUtils.copyProperties(userIcbcTemp, userIcbcTempDTO);
					}
					UserIcbcFilingDTO userIcbcFiling = new UserIcbcFilingDTO();
					BeanUtils.copyProperties(userIcbcFilingList.get(i), userIcbcFiling);
					userIcbcFiling.setUserIcbcTempDTO(userIcbcTempDTO);
					UserIcbcFilingDTOList.add(userIcbcFiling);
				}
			}
		}

		//获取店员信息
		StoreUserInfoDTO storeUserInfoDTO = new StoreUserInfoDTO();
		UserStoreInfo userStoreInfo = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("auth_code", userReviewInfo.getAuthCode()).groupBy("auth_code"));
		List<StoreUserInfoDTO> storeUserInfoDTOS = new ArrayList<>();
		if (userStoreInfo != null) {
			storeUserInfoDTO.setOrgSeq(userStoreInfo.getOrgSeq());
			PageData pageData = new PageData();
			pageData.setCondition(storeUserInfoDTO);
			pageData.setSize(100000000);
			ResponseResultPage<StoreUserInfoDTO> responseResultPage = queryStoreUserInfo(pageData);
			storeUserInfoDTOS = responseResultPage.getRecords();
		}
		UserReviewInfoDTO userinfo = new UserReviewInfoDTO();
		BeanUtils.copyProperties(userReviewInfo, userinfo);
		userinfo.setUserReviewRecordList(userReviewRecordDTOSList);
		userinfo.setUserIcbcFilingDTOList(UserIcbcFilingDTOList);
		userinfo.setStoreUserInfoDTOS(storeUserInfoDTOS);
		if (userReviewInfo != null) {
			responseResult.setCode(1);
			responseResult.setMessage("成功");
			responseResult.setResult(userinfo);
		} else {
			responseResult.setCode(0);
			responseResult.setMessage("查询失败");
		}
		return responseResult;
	}

	@Override
	public ResponseResult<UserInfoDTO> qryClerkUserInfo(String uuid) {
		ResponseResult<UserInfoDTO> responseResult = new ResponseResult<UserInfoDTO>();
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		UserInfo userInfo = userInfoMapper.selectById(uuid);
		BeanUtils.copyProperties(userInfo, userInfoDTO);
		if (userInfo != null) {
			responseResult.setCode(1);
			responseResult.setMessage("成功");
			responseResult.setResult(userInfoDTO);
		} else {
			responseResult.setCode(0);
			responseResult.setMessage("查询失败");
		}
		return responseResult;
	}

	@Override
	public Map<String, Object> getAllZone() {
		Map<String, Object> maplist = new HashMap<String, Object>();
		List<Map<String, Object>> lsit = new ArrayList<Map<String, Object>>();
		//org_type  2 表示战区  3表市基地
		try {
			List<Map<String, Object>> userorganization = organizationMapper.selectMaps(new QueryWrapper<UserOrganization>().eq("org_type", 2));
			if (userorganization != null && userorganization.size() > 0) {
				for (int i = 0; i < userorganization.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", userorganization.get(i).get("org_seq"));
					map.put("name", userorganization.get(i).get("org_name"));
					lsit.add(map);
				}
				maplist.put("code", 1);
				maplist.put("message", "查询成功");
			} else {
				maplist.put("code", 1);
				maplist.put("message", "未查询到数据");
			}
		} catch (Exception e) {
			maplist.put("code", 0);
			maplist.put("message", e);
		}
		maplist.put("result", lsit);
		return maplist;
	}

	@Override
	public ResponseRecords getAllZoneList() {
		ResponseRecords maplist = new ResponseRecords();
		List<String> lsit = new ArrayList<String>();
		//org_type  2 表示战区  3表市基地
		try {
			List<UserOrganization> userorganization = organizationMapper.selectList(new QueryWrapper<UserOrganization>().eq("org_type", 2));
			if (userorganization.size() > 0) {
				lsit = userorganization.stream().map(p -> p.getOrgSeq()).collect(Collectors.toList());
				maplist.setCode(1);
			} else {
				maplist.setCode(1);
			}
		} catch (Exception e) {
			maplist.setCode(0);
		}
		maplist.setRecords(lsit);
		return maplist;
	}

	@Override
	public Map<String, Object> getAllBase(String code) {
		Map<String, Object> maplist = new HashMap<String, Object>();
		List<Map<String, Object>> lsit = new ArrayList<Map<String, Object>>();
		//org_type  2 表示战区  3表市基地
		try {
			List<Map<String, Object>> userorganization = organizationMapper.selectMaps(new QueryWrapper<UserOrganization>().eq("org_type", 3).eq("up_org_seq", code));
			if (userorganization != null && userorganization.size() > 0) {
				for (int i = 0; i < userorganization.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", userorganization.get(i).get("org_seq"));
					map.put("name", userorganization.get(i).get("org_name"));
					lsit.add(map);
				}
				maplist.put("code", 1);
				maplist.put("message", "查询成功");
			} else {
				maplist.put("code", 1);
				maplist.put("message", "未查询到数据");
			}
		} catch (Exception e) {
			maplist.put("code", 0);
			maplist.put("message", e);
		}
		maplist.put("result", lsit);
		return maplist;
	}

	@Override
	public ResponseResult<UserStoreInfoDTO> queryUserStoreInfo(String uuid) {
		ResponseResult<UserStoreInfoDTO> responseResult = new ResponseResult<UserStoreInfoDTO>();
		UserStoreInfoDTO userInfoDTO = new UserStoreInfoDTO();
		UserStoreInfo userStoreInfo = storeMapper.selectById(uuid);
		BeanUtils.copyProperties(userStoreInfo, userInfoDTO);
		//店铺建档信息
		List<UserIcbcFilingDTO> UserIcbcFilingDTOList = new ArrayList<UserIcbcFilingDTO>();
		List<UserIcbcFiling> userIcbcFilingList = userIcbcFilingMapper.selectList(new QueryWrapper<UserIcbcFiling>().eq("store_id", uuid).eq("status", 1));
		if (userIcbcFilingList != null && userIcbcFilingList.size() > 0) {
			for (int i = 0; i < userIcbcFilingList.size(); i++) {
				//查询模板路径
				UserIcbcTemp userIcbcTemp = userIcbcTempMapper.selectById(userIcbcFilingList.get(i).getFileTempId());
				UserIcbcTempDTO userIcbcTempDTO = new UserIcbcTempDTO();
				if (userIcbcTemp != null) {
					BeanUtils.copyProperties(userIcbcTemp, userIcbcTempDTO);
				}
				UserIcbcFilingDTO userIcbcFiling = new UserIcbcFilingDTO();
				BeanUtils.copyProperties(userIcbcFilingList.get(i), userIcbcFiling);
				userIcbcFiling.setUserIcbcTempDTO(userIcbcTempDTO);
				UserIcbcFilingDTOList.add(userIcbcFiling);
			}
		}
		userInfoDTO.setUserIcbcFilingDTOList(UserIcbcFilingDTOList);
		//获取店员信息
		StoreUserInfoDTO storeUserInfoDTO = new StoreUserInfoDTO();
		storeUserInfoDTO.setOrgSeq(userInfoDTO.getOrgSeq());
		PageData pageData = new PageData();
		pageData.setCondition(storeUserInfoDTO);
		pageData.setSize(100000000);
		ResponseResultPage<StoreUserInfoDTO> responseResultPage = queryStoreUserInfo(pageData);
		List<StoreUserInfoDTO> storeUserInfoDTOS = responseResultPage.getRecords();
		userInfoDTO.setStoreUserInfoDTOS(storeUserInfoDTOS);
		//查询店铺申请流程信息
		List<UserReviewRecordDTO> userReviewRecordList = userInfoMapper.qryStoreRecedListInfo(userInfoDTO.getAuthCode());
		userInfoDTO.setUserReviewRecordDTOList(userReviewRecordList);
		if (userStoreInfo != null) {
			responseResult.setCode(1);
			responseResult.setMessage("成功");
			responseResult.setResult(userInfoDTO);
		} else {
			responseResult.setCode(0);
			responseResult.setMessage("查询失败");
		}
		return responseResult;
	}

	@Override
	public Map<String, Object> getBaseUserInfo(String orgSeq) {
		Map<String, Object> maplist = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<Map<String, Object>> userorgrelation = userOrgRelationMapper.selectMaps(new QueryWrapper<UserOrgRelation>().eq("org_seq", orgSeq));
			if (userorgrelation != null && userorgrelation.size() > 0) {
				for (int i = 0; i < userorgrelation.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					UserInfo userinfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("user_id", userorgrelation.get(i).get("user_id")));
					map.put("id", userinfo.getUserId());
					map.put("name", userinfo.getUserName());
					list.add(map);
				}
				maplist.put("code", 1);
				maplist.put("message", "查询成功");
			} else {
				maplist.put("code", 1);
				maplist.put("message", "未查询到数据");
			}
		} catch (Exception e) {
			maplist.put("code", 0);
			maplist.put("message", e);
			return maplist;
		}
		maplist.put("result", list);
		return maplist;
	}

	@Override
	public ResponseResult<UserReviewInfoDTO> getStoreByAuthCode(String authCode) {
		ResponseResult<UserReviewInfoDTO> res = new ResponseResult<UserReviewInfoDTO>();
		List<UserReviewRecord> lsitusrRecord = new ArrayList<UserReviewRecord>();
		List<UserReviewRecordDTO> lsitusrRecordDto = new ArrayList<UserReviewRecordDTO>();
		List<UserIcbcFilingDTO> UserIcbcFilingDTOList = new ArrayList<>();
		UserReviewInfoDTO userReviewInfoDTO = new UserReviewInfoDTO();
		UserReviewInfo listUserReview = userReviewInfoMapper.selectOne(new QueryWrapper<UserReviewInfo>().eq("auth_code", authCode).groupBy("auth_code"));
		if (listUserReview != null) {
			BeanUtils.copyProperties(listUserReview, userReviewInfoDTO);
			lsitusrRecord = userReviewRecordMapper.selectList(new QueryWrapper<UserReviewRecord>().eq("review_id", listUserReview.getUuid()).orderByDesc("review_time"));
			if (lsitusrRecord != null && lsitusrRecord.size() > 0) {
				for (int i = 0; i < lsitusrRecord.size(); i++) {
					UserReviewRecordDTO userReviewRecordDTO = new UserReviewRecordDTO();
					BeanUtils.copyProperties(lsitusrRecord.get(i), userReviewRecordDTO);
					lsitusrRecordDto.add(userReviewRecordDTO);
				}
			}
			//店铺建档信息
			List<UserIcbcFiling> userIcbcFilingList = userIcbcFilingMapper.selectList(new QueryWrapper<UserIcbcFiling>().eq("review_id", listUserReview.getUuid()).eq("status", 1));
			if (userIcbcFilingList != null && userIcbcFilingList.size() > 0) {
				for (int i = 0; i < userIcbcFilingList.size(); i++) {
					//查询模板路径
					UserIcbcTemp userIcbcTemp = userIcbcTempMapper.selectById(userIcbcFilingList.get(i).getFileTempId());
					UserIcbcTempDTO userIcbcTempDTO = new UserIcbcTempDTO();
					if (userIcbcTemp != null) {
						BeanUtils.copyProperties(userIcbcTemp, userIcbcTempDTO);
					}
					UserIcbcFilingDTO userIcbcFiling = new UserIcbcFilingDTO();
					BeanUtils.copyProperties(userIcbcFilingList.get(i), userIcbcFiling);
					userIcbcFiling.setUserIcbcTempDTO(userIcbcTempDTO);
					UserIcbcFilingDTOList.add(userIcbcFiling);
				}
			}
		}
		res.setCode(1);
		res.setMessage("查询成功");
		userReviewInfoDTO.setUserReviewRecordList(lsitusrRecordDto);
		userReviewInfoDTO.setUserIcbcFilingDTOList(UserIcbcFilingDTOList);
		res.setResult(userReviewInfoDTO);
		return res;
	}

	@Override
	public ResponseBase editStore(UserReviewInfoDTO userReviewInfoDTO) {
		ResponseBase responseBase = new ResponseBase();
		UserReviewInfo userReviewInfo = new UserReviewInfo();
		//判断当前编辑店铺店主店长的信息 是否出现重复
		if (StringUtils.isEmpty(userReviewInfoDTO.getAuthCode())) {
			responseBase.setCode(0);
			responseBase.setMessage("店铺授权码不能为空");
			return responseBase;
		}
		//查询当前是否已经有已经存在待审核得数据，有则不允许提交
		UserReviewInfo userReview = userReviewInfoMapper.selectOne(
				new QueryWrapper<UserReviewInfo>().eq("auth_code",
						userReviewInfoDTO.getAuthCode()).in("review_status", 1, 2).groupBy("auth_code"));
		if (userReview != null) {
			responseBase.setCode(0);
			responseBase.setMessage("当前店铺已提交至战区审核，请勿重复提交！");
			return responseBase;
		}
		UserStoreInfo userStoreInfo = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("auth_code", userReviewInfoDTO.getAuthCode()));
		String memagePhone = userReviewInfoDTO.getStoreManagerPhone();
		String ownPhone = userReviewInfoDTO.getStoreOwnerPhone();
		String nowMemagePhone = userStoreInfo.getStoreManagerPhone() == null ? "0" : userStoreInfo.getStoreManagerPhone();
		String nowOwnPhone = userStoreInfo.getStoreOwnerPhone() == null ? "0" : userStoreInfo.getStoreOwnerPhone();
		if (!memagePhone.equals(nowMemagePhone)) {
			//如果店长号码做了修改，判断档期按修改号码是否存在于系统中
			List<UserInfo> userInfoList = userInfoMapper.selectList(new QueryWrapper<UserInfo>().eq("tel", memagePhone));
			if (userInfoList != null && userInfoList.size() > 0) {
				responseBase.setCode(0);
				responseBase.setMessage("店长手机号已存在，请重新填写");
				return responseBase;
			}
		}
		if (!ownPhone.equals(nowOwnPhone)) {
			//如果店主号码做了修改，判断当前按修改号码是否存在于系统中
			List<UserInfo> userInfoList = userInfoMapper.selectList(new QueryWrapper<UserInfo>().eq("tel", ownPhone));
			if (userInfoList != null && userInfoList.size() > 0) {
				responseBase.setCode(0);
				responseBase.setMessage("店主手机号已存在，请重新填写");
				return responseBase;
			}
		}
		BeanUtils.copyProperties(userReviewInfoDTO, userReviewInfo);
		if (userReviewInfoDTO.getUuid() != null) {
			userReviewInfo.setReviewStatus(1);
			//修改申请
			userReviewInfo.setReviewType(2);
			//重新提交申请将原来得审批人信息弃之为空
			userReviewInfo.setReviewUser("");
			userReviewInfo.setFinalReviewUser("");
			userReviewInfo.setReviewOpinion(null);
			userReviewInfo.setFinalReviewOpinion(null);
			userReviewInfo.setCreateTime(LocalDateTime.now());
			UserReviewRecord userReviewRecord = new UserReviewRecord();
			String uuid = UUID.randomUUID().toString();
			userReviewRecord.setUuid(uuid);
			userReviewRecord.setReviewId(userReviewInfo.getUuid());
			//审核状态审核状态(1-已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过)
			userReviewRecord.setReviewStatus(1);
			userReviewRecord.setReviewTime(LocalDateTime.now());
			userReviewRecordMapper.insert(userReviewRecord);
			mapper.updateById(userReviewInfo);
		} else {
			String reviewuuid = UUID.randomUUID().toString();
			userReviewInfo.setUuid(reviewuuid);
			//修改状态为初审
			userReviewInfo.setReviewStatus(1);
			//申请模式为 修改审核
			userReviewInfo.setReviewType(2);
			userReviewInfo.setCreateTime(LocalDateTime.now());
			//获取基地名称，战区名称，基地业务员名称
			if (userReviewInfoDTO.getUpOrgSeq() != null && userReviewInfoDTO.getUpOrgSeq() != "") {
				UserOrganization userOrganization = organizationMapper.selectById(userReviewInfoDTO.getUpOrgSeq());
				if (userOrganization != null) {
					userReviewInfo.setUpOrgZone(userOrganization.getOrgName());
				}
			}
			if (userReviewInfoDTO.getBaseCode() != null && userReviewInfoDTO.getBaseCode() != "") {
				UserOrganization userOrganization = organizationMapper.selectById(userReviewInfoDTO.getBaseCode());
				if (userOrganization != null) {
					userReviewInfo.setBaseName(userOrganization.getOrgName());
				}
			}
			if (userReviewInfoDTO.getBasePersonCode() != null && userReviewInfoDTO.getBasePersonCode() != "") {
				UserInfo userInfo = userInfoMapper.selectById(userReviewInfoDTO.getBasePersonCode());
				if (userInfo != null) {
					userReviewInfo.setBasePersonName(userInfo.getRealName());
				}
			}
			//查询当前授权码下最大得编辑记录数 然后加1
			Integer number = mapper.getSumNumber(userReviewInfo.getAuthCode()) == null ? 0 : mapper.getSumNumber(userReviewInfo.getAuthCode());
			userReviewInfo.setNumber(number + 1);
			mapper.insert(userReviewInfo);
			//给记录表插入信息
			UserReviewRecord userReviewRecord = new UserReviewRecord();
			String uuid = UUID.randomUUID().toString();
			userReviewRecord.setUuid(uuid);
			userReviewRecord.setReviewId(reviewuuid);
			//审核状态审核状态(1-已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过)
			userReviewRecord.setReviewStatus(1);
			userReviewRecord.setReviewTime(LocalDateTime.now());
			userReviewRecordMapper.insert(userReviewRecord);
		}
		responseBase.setCode(1);
		responseBase.setMessage("成功");
		return responseBase;
	}

	@Override
	@Transactional
	public ResponseBase shopPreliminary(PreliminaryExamination preliminaryExamination) {
		ResponseBase responseBase = new ResponseBase();
		UserReviewRecord userReviewRecord = new UserReviewRecord();

		String uuid = UUID.randomUUID().toString();
		userReviewRecord.setUuid(uuid);
		UserReviewInfo userReviewMap = userReviewInfoMapper.selectOne(new QueryWrapper<UserReviewInfo>().eq("uuid", preliminaryExamination.getUuid()));
		//非该战区人员无法审核
		List<UserOrgRelation> userlist = userOrgRelationMapper.selectList(new QueryWrapper<UserOrgRelation>().eq("user_id", JwtTokenUtil.currUser()));
		Boolean preliminary = false;
		if (userlist != null && userlist.size() > 0) {
			for (int i = 0; i < userlist.size(); i++) {
				String nowOrgseq = userlist.get(i).getOrgSeq();
				if (nowOrgseq == userReviewMap.getUpOrgSeq() || nowOrgseq.equals(userReviewMap.getUpOrgSeq())) {
					preliminary = true;
					break;
				}
			}
		}
		if (!preliminary) {
			responseBase.setCode(0);
			responseBase.setMessage("没有审核权限！");
			return responseBase;
		}


		if (userReviewMap != null) {
			userReviewMap.setReviewUser(JwtTokenUtil.currUser());
			userReviewMap.setReviewOpinion(preliminaryExamination.getReviewOpinion());
			userReviewRecord.setReviewOption(preliminaryExamination.getReviewOpinion());
			userReviewRecord.setReviewUser(JwtTokenUtil.currUser());
			userReviewRecord.setReviewTime(LocalDateTime.now());
			userReviewRecord.setReviewId(preliminaryExamination.getUuid());
			//（审核状态(1-已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过)）
			if (preliminaryExamination.getReviewStatus() == 1) {//初核通过
				userReviewMap.setReviewStatus(2);//设置为复核
				userReviewMap.setReviewType(2);//状态为编辑审核
				userReviewRecord.setReviewStatus(2);
			} else {
				userReviewMap.setReviewStatus(3);//设置为不通过
				userReviewMap.setReviewType(2);//状态为编辑审核
				userReviewMap.setUpdateTime(LocalDateTime.now());
				userReviewRecord.setReviewStatus(3);
				userReviewInfoMapper.updateById(userReviewMap);
				userReviewRecordMapper.insert(userReviewRecord);
				responseBase.setCode(1);
				responseBase.setMessage("审核成功");
				return responseBase;
			}
			userReviewMap.setUpdateTime(LocalDateTime.now());
			int userMap = userReviewInfoMapper.updateById(userReviewMap);
			//插入日志表
			userReviewRecordMapper.insert(userReviewRecord);
			responseBase.setCode(1);
			responseBase.setMessage("审核成功");
		} else {
			responseBase.setCode(0);
			responseBase.setMessage("审核失败");
		}
		return responseBase;
	}

	@Override
	@Transactional
	public ResponseBase storeFinalReview(PreliminaryExamination preliminaryExamination) {
		ResponseBase responseBase = new ResponseBase();
		UserReviewRecord userReviewRecord = new UserReviewRecord();
		String uuid = UUID.randomUUID().toString();
		userReviewRecord.setUuid(uuid);
		UserReviewInfo userReviewMap = userReviewInfoMapper.selectOne(new QueryWrapper<UserReviewInfo>().eq("uuid", preliminaryExamination.getUuid()));
		List<UserOrgRelation> userlist = userOrgRelationMapper.selectList(new QueryWrapper<UserOrgRelation>().eq("user_id", JwtTokenUtil.currUser()));
		Boolean preliminary = false;
		if (userlist != null && userlist.size() > 0) {
			for (int i = 0; i < userlist.size(); i++) {
				//判断是否是总部
				int type = userlist.get(i).getUserType();
				if (type == 1) {
					preliminary = true;
				}
			}
		}
		if (!preliminary) {
			responseBase.setCode(0);
			responseBase.setMessage("没有审核权限！");
			return responseBase;
		}

//		UserStoreInfo userStoreInfos = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("auth_code", userReviewMap.getAuthCode()).groupBy("auth_code"));
//		if (userStoreInfos != null) {
//			responseBase.setCode(0).setMessage("店铺已经生成！请前往【店铺档案】查看");
//			return responseBase;
//		}
		try {
			if (userReviewMap != null) {
				userReviewMap.setFinalReviewUser(JwtTokenUtil.currUser());
				userReviewMap.setFinalReviewOpinion(preliminaryExamination.getFinalReviewOpinion());
				userReviewRecord.setReviewOption(preliminaryExamination.getFinalReviewOpinion());
				userReviewRecord.setReviewUser(JwtTokenUtil.currUser());
				userReviewRecord.setReviewTime(LocalDateTime.now());
				userReviewRecord.setReviewId(preliminaryExamination.getUuid());
				//（审核状态(1-已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过)）
				if ("1".equals(preliminaryExamination.getReviewStatus()) || Integer.parseInt(preliminaryExamination.getReviewStatus().toString()) == 1) {
					//设置为复审通过
					userReviewMap.setReviewStatus(5);
					userReviewMap.setReviewType(2);
					userReviewRecord.setReviewStatus(5);
				} else {
					//设置为复审不通过
					userReviewMap.setReviewStatus(4);
					userReviewMap.setReviewType(2);//设置为申请审核
					userReviewMap.setUpdateTime(LocalDateTime.now());
					userReviewInfoMapper.updateById(userReviewMap);
					userReviewRecord.setReviewStatus(4);
					userReviewRecordMapper.insert(userReviewRecord);
					responseBase.setCode(1);
					responseBase.setMessage("审核成功");
					return responseBase;
				}
				userReviewMap.setUpdateTime(LocalDateTime.now());
				userReviewInfoMapper.updateById(userReviewMap);
				//插入日志表
				userReviewRecordMapper.insert(userReviewRecord);

				String userType = "5";//用户类型(1集团人员2战区人员3基地人员4店主5店长6店员)
				// 更新组织机构信息
				UserOrganization orgNization = new UserOrganization();
				//插叙店铺信息
				UserStoreInfo userStoreInfo = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("auth_code", userReviewMap.getAuthCode()));
				String storeid = userStoreInfo.getUuid();
				UserOrganization userOrganization = new UserOrganization();
				userOrganization.setOrgSeq(userStoreInfo.getOrgSeq());
				userOrganization.setOrgName(userReviewMap.getStoreName());
				if (userReviewMap.getBaseCode() != null) {
					userOrganization.setUpOrgSeq(userReviewMap.getBaseCode());
				}
				userOrganization.setUpdateUser(JwtTokenUtil.currUser());
				userOrganization.setUpdateTime(LocalDateTime.now());
				//根据组织机构编码 更新organization 中得店铺名称
				organizationMapper.updateById(userOrganization);
				//更新门店信息
				//查看是否修改了手机号码
				String memagePhone = userReviewMap.getStoreManagerPhone();
				String ownPhone = userReviewMap.getStoreOwnerPhone();
				String nowMemagePhone = userStoreInfo.getStoreManagerPhone() == null ? "0" : userStoreInfo.getStoreManagerPhone();
				String nowOwnPhone = userStoreInfo.getStoreOwnerPhone() == null ? "0" : userStoreInfo.getStoreOwnerPhone();
				Boolean memageStatus = false;
				Boolean ownStatus = false;
				if (!memagePhone.equals(nowMemagePhone)) {
					memageStatus = true;
				}
				if (!ownPhone.equals(nowOwnPhone)) {
					ownStatus = true;
				}
				BeanUtils.copyProperties(userReviewMap, userStoreInfo);
				//防止将申请信息的uuid 将店铺uuid覆盖
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime startDateTime = LocalDate.parse(userReviewMap.getOpenDate().substring(0, 10), df).atStartOfDay();
				userStoreInfo.setWechatId(userReviewMap.getWechatId());
				userStoreInfo.setUuid(storeid);
				userStoreInfo.setStatus(1);
				userStoreInfo.setOpenStatus(1);
				userStoreInfo.setUpdateUser(JwtTokenUtil.currUser());
				userStoreInfo.setUpdateTime(LocalDateTime.now());
				userStoreInfo.setOpenDate(startDateTime);
				storeMapper.updateById(userStoreInfo);
				//更新 人员组织机构表信息
				//跟新店主信息
				//查询店主得userid
				// 1：如果原来有店长店主信息，则在原来的基础上更新
				// 2：如果原来的店长店主为空 则以最新的编辑数据新增店长店主，
				// 3：如果原来的店长店主不为空，但是用户表没有人员信息，则同样增加数据
				if ("0".equals(nowOwnPhone)) {
					//如果店铺没有店主信息 如果查看当前申请人员是否存在人员信息，如果存在则更新 不存在则都在删除
					userinfoOwn = userInfoMapper.selectOne
							(new QueryWrapper<UserInfo>().eq("tel", userReviewMap.getStoreOwnerPhone()).groupBy("tel"));
				} else {
					//店铺有店主信息 查看是否有店主人员信息 如果有则更新
					userinfoOwn = userInfoMapper.selectOne
							(new QueryWrapper<UserInfo>().eq("tel", nowOwnPhone).groupBy("tel"));
				}
				UserInfo ownUserinfo = new UserInfo();
				if (userinfoOwn == null) {
					//主要是针对数据迁移数据，有可能存在没有店长或者店主得用户，所以编辑时自动创建
					//插入人员组织信息
					String uuidOwn = UUID.randomUUID().toString();
					ownUserinfo.setUserId(uuidOwn);
					ownUserinfo.setRealName(userStoreInfo.getStoreOwnerName());
					ownUserinfo.setUserName(userStoreInfo.getStoreOwnerPhone());
					ownUserinfo.setTel(userStoreInfo.getStoreOwnerPhone());
					ownUserinfo.setHeadPicUrl(userStoreInfo.getStoreOwnerPic());
					ownUserinfo.setPassword(MD5Util.toMD5("1"));
					userInfoMapper.insert(ownUserinfo);
					userinfoOwn = ownUserinfo;
					//生成店主账号
					UserOrgRelation userOrgRelation = new UserOrgRelation();
					userOrgRelation.setUuid(UUID.randomUUID().toString());
					userOrgRelation.setUserId(uuidOwn);
					userOrgRelation.setOrgSeq(userStoreInfo.getOrgSeq());
					userOrgRelation.setUserType(4);
					userOrgRelation.setStatus(1);
					userOrgRelation.setCreateUser(JwtTokenUtil.currUser());
					userOrgRelation.setUpdateUser(JwtTokenUtil.currUser());
					userOrgRelation.setCreateTime(LocalDateTime.now());
					userOrgRelation.setUpdateTime(LocalDateTime.now());
					userOrgRelationMapper.insert(userOrgRelation);
				} else {
					String ownUserId = userinfoOwn.getUserId();
					userinfoOwn.setUserId(ownUserId);
					userinfoOwn.setRealName(userStoreInfo.getStoreOwnerName());
					userinfoOwn.setUserName(userStoreInfo.getStoreOwnerPhone());
					userinfoOwn.setTel(userStoreInfo.getStoreOwnerPhone());
					userinfoOwn.setHeadPicUrl(userStoreInfo.getStoreOwnerPic());
					userinfoOwn.setUpdateTime(LocalDateTime.now());
					userinfoOwn.setUpdateUser(JwtTokenUtil.currUser());
					if (ownStatus) {
						userinfoOwn.setPassword(MD5Util.toMD5("888888"));
					}
					userInfoMapper.updateById(userinfoOwn);
				}
				//查询店长得userid
				//理由同上
				if ("0".equals(nowMemagePhone)) {
					userinfoManager = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel", userReviewMap.getStoreManagerPhone()).groupBy("tel"));
				} else {
					userinfoManager = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel", nowMemagePhone).groupBy("tel"));
				}
				//更新店长信息
				UserInfo managerUserinfo = new UserInfo();
				if (userinfoManager == null) {
					//生成店长账号
					String managerUserId = UUID.randomUUID().toString();
					managerUserinfo.setUserId(managerUserId);
					managerUserinfo.setRealName(userStoreInfo.getStoreManagerName());
					managerUserinfo.setUserName(userStoreInfo.getStoreManagerPhone());
					managerUserinfo.setTel(userStoreInfo.getStoreManagerPhone());
					managerUserinfo.setHeadPicUrl(userStoreInfo.getStoreManagerPic());
					managerUserinfo.setPassword(MD5Util.toMD5("1"));
					userInfoMapper.insert(managerUserinfo);
					userinfoManager = managerUserinfo;

					//生成店长组织机构信息
					UserOrgRelation userOrgRelation = new UserOrgRelation();
					userOrgRelation.setUuid(UUID.randomUUID().toString());
					userOrgRelation.setUserId(managerUserId);
					userOrgRelation.setOrgSeq(userStoreInfo.getOrgSeq());
					userOrgRelation.setUserType(5);
					userOrgRelation.setStatus(1);
					userOrgRelation.setCreateUser(JwtTokenUtil.currUser());
					userOrgRelation.setUpdateUser(JwtTokenUtil.currUser());
					userOrgRelation.setCreateTime(LocalDateTime.now());
					userOrgRelation.setUpdateTime(LocalDateTime.now());
					userOrgRelationMapper.insert(userOrgRelation);

				} else {
//					//查看当前编辑，店主电话是否已经存在在系统，则给该角色增加当前店铺的店主 角色
//					String ownPhoneNew = userStoreInfo.getStoreOwnerPhone() == null ? "" : userStoreInfo.getStoreOwnerPhone();
//					UserInfo userInfoList = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel", ownPhoneNew).eq("status", 1));
//					if (userInfoList != null) {
//						//判断当前角色是否是在同一家店铺 如果是同一家店铺，出现了角色变更 则删除原来的角色，如果未出现角色变更（仅仅店主修改电话，店长修改电话），则直接更新即可
//						List<UserOrgRelation> userOrgRelation = userOrgRelationMapper.selectList
//								(new QueryWrapper<UserOrgRelation>().eq("", userInfoList.getUserId()));
//						if (userOrgRelation != null && userOrgRelation.size() > 0) {
//							for (int i = 0; i < userOrgRelation.size(); i++) {
//								//当前申请电话号码的人员所属门店orgseq
//								String orgSeq = userOrgRelation.get(i).getOrgSeq();
//								//当前人员所属角色
//								Integer userTypeNew = userOrgRelation.get(i).getUserType();
//								//查询店铺orgseq
//								UserStoreInfo userStoreInfo1 = storeMapper.selectOne
//										(new QueryWrapper<UserStoreInfo>().eq("auth_code", userStoreInfo.getAuthCode()));
//								if (userStoreInfo1.getOrgSeq() == orgSeq || orgSeq.equals(userStoreInfo1.getOrgSeq())) {
//									//如果是同一家门店
//									//如果 申请电话所属的角色是店主
//									if (userTypeNew == 4) {
//										String ownUserId = userinfoOwn.getUserId();
//										userinfoOwn.setUserId(ownUserId);
//										userinfoOwn.setRealName(userStoreInfo.getStoreOwnerName());
//										userinfoOwn.setUserName(userStoreInfo.getStoreOwnerPhone());
//										userinfoOwn.setTel(userStoreInfo.getStoreOwnerPhone());
//										userinfoOwn.setHeadPicUrl(userStoreInfo.getStoreOwnerPic());
//										userinfoOwn.setUpdateTime(LocalDateTime.now());
//										userinfoOwn.setUpdateUser(JwtTokenUtil.currUser());
//										if (ownStatus) {
//											userinfoOwn.setPassword(MD5Util.toMD5("1"));
//										}
//										userInfoMapper.updateById(userinfoOwn);
//									}
//									//如果 申请电话所属角色是 店长 店员
//									if (userTypeNew == 5 || userTypeNew == 6) {
//										//删除店长 店员角色
//										userOrgRelationMapper.deleteById(userOrgRelation.get(i).getUuid());
//										//删除人员信息
//										String userid = userOrgRelation.get(i).getUserId();
//										userInfoMapper.deleteById(userid);
//
//										String ownUserId = userinfoOwn.getUserId();
//										userinfoOwn.setUserId(ownUserId);
//										userinfoOwn.setRealName(userStoreInfo.getStoreOwnerName());
//										userinfoOwn.setUserName(userStoreInfo.getStoreOwnerPhone());
//										userinfoOwn.setTel(userStoreInfo.getStoreOwnerPhone());
//										userinfoOwn.setHeadPicUrl(userStoreInfo.getStoreOwnerPic());
//										userinfoOwn.setUpdateTime(LocalDateTime.now());
//										userinfoOwn.setUpdateUser(JwtTokenUtil.currUser());
//										if (ownStatus) {
//											userinfoOwn.setPassword(MD5Util.toMD5("1"));
//										}
//										userInfoMapper.updateById(userinfoOwn);
//									}
//								}
//							}
//						}
//						//判断当前角色是 4-店主  5-店长 6-店员 其他
//						//如果当前角色是店主
//
//
//					}
					String managerUserId = userinfoManager.getUserId();
					userinfoManager.setUserId(managerUserId);
					userinfoManager.setRealName(userStoreInfo.getStoreManagerName());
					userinfoManager.setUserName(userStoreInfo.getStoreManagerPhone());
					userinfoManager.setTel(userStoreInfo.getStoreManagerPhone());
					userinfoManager.setHeadPicUrl(userStoreInfo.getStoreManagerPic());
					userinfoManager.setUpdateTime(LocalDateTime.now());
					userinfoManager.setUpdateUser(JwtTokenUtil.currUser());
					if (memageStatus) {
						userinfoManager.setPassword(MD5Util.toMD5("888888"));
					}
					userInfoMapper.updateById(userinfoManager);
				}
				responseBase.setCode(1);
				responseBase.setMessage("审核成功");
			} else {
				responseBase.setCode(0);
				responseBase.setMessage("审核失败");
				//事务回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		} catch (Exception e) {
			responseBase.setCode(0);
			responseBase.setMessage("审核失败----" + e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		}
		return responseBase;
	}

	@Override
	public ResponseBase phoneVerification(String phone, Integer type) {
		ResponseBase responseBase = new ResponseBase();
		//店主
		if (type == 4) {
			//userinfo 表中是否存在
			String tel = userInfoMapper.getPhoneAsByOwn(phone);
			if (!StringUtils.isEmpty(tel)) {
				responseBase.setCode(0);
				responseBase.setMessage("当前号码已被注册，请重新填写");
				return responseBase;
			}
			//店铺申请表中是否已经存在
			List<UserReviewInfo> userReviewInfo = userReviewInfoMapper.selectList(new QueryWrapper<UserReviewInfo>().eq("store_owner_phone", phone).ne("review_status", 3));
			if (userReviewInfo != null && userReviewInfo.size() > 0) {
				responseBase.setCode(0);
				responseBase.setMessage("当前号码已被申请，请重新填写");
				return responseBase;
			}
		}
		//店长
		if (type == 5) {
			List<UserInfo> userInfoList = userInfoMapper.selectList(new QueryWrapper<UserInfo>().eq("tel", phone).eq("status", 1));
			if (userInfoList != null && userInfoList.size() > 0) {
				responseBase.setCode(0);
				responseBase.setMessage("当前号码已被注册，请重新填写");
				return responseBase;
			}
			//店铺申请表中是否已经存在
			List<UserReviewInfo> userReviewInfo = userReviewInfoMapper.selectList(new QueryWrapper<UserReviewInfo>().eq("store_manager_phone", phone).ne("review_status", 3));
			if (userReviewInfo != null && userReviewInfo.size() > 0) {
				responseBase.setCode(0);
				responseBase.setMessage("当前号码已被申请，请重新填写");
				return responseBase;
			}

		}
		responseBase.setCode(1);
		responseBase.setMessage("号码可以使用");
		return responseBase;
	}

	@Override
	public ResponseResult<UserReviewInfoDTO> getReviewId(String reviewId) {
		ResponseResult<UserReviewInfoDTO> res = new ResponseResult<UserReviewInfoDTO>();
		List<UserReviewRecord> lsitusrRecord = new ArrayList<UserReviewRecord>();
		List<UserReviewRecordDTO> lsitusrRecordDto = new ArrayList<UserReviewRecordDTO>();
		UserReviewInfoDTO userReviewInfoDTO = new UserReviewInfoDTO();
		lsitusrRecord = userReviewRecordMapper.selectList(new QueryWrapper<UserReviewRecord>().eq("review_id", reviewId).orderByDesc("review_time"));
		if (lsitusrRecord != null && lsitusrRecord.size() > 0) {
			for (int i = 0; i < lsitusrRecord.size(); i++) {
				UserReviewRecordDTO userReviewRecordDTO = new UserReviewRecordDTO();
				BeanUtils.copyProperties(lsitusrRecord.get(i), userReviewRecordDTO);
				lsitusrRecordDto.add(userReviewRecordDTO);
			}
		}
		res.setCode(1);
		res.setMessage("查询成功");
		userReviewInfoDTO.setUserReviewRecordList(lsitusrRecordDto);
		res.setResult(userReviewInfoDTO);
		return res;
	}

	/**
	 * 生成编码
	 *
	 * @return
	 */
	public String generateMemberSeq(String str) {
		int year = LocalDate.now().getYear();
		DecimalFormat df = new DecimalFormat("0000");
		RedisAtomicLong counter = new RedisAtomicLong(str, redisTemplate.getConnectionFactory());
		long count = counter.incrementAndGet();
		String memberSeq = str + year + df.format(count);
		return memberSeq;
	}

	@Override
	public ResponseResultPage<StoreUserInfoDTO> queryStoreUserInfo(PageData<StoreUserInfoDTO> pageData) {
		ResponseResultPage<StoreUserInfoDTO> responseResultPage = new ResponseResultPage<>();

		Page<UserInfo> page = new Page<>(pageData.getCurrent(), pageData.getSize());
		UserOrganization userOrganization = new UserOrganization();
		userOrganization = organizationMapper.selectById(pageData.getCondition().getOrgSeq());
		String type = userOrganization.getOrgType();
		ResponseResultPage<StoreUserInfoDTO> result = storeActivationService.queryStoreUserInfo(pageData, type);
		responseResultPage.setSize(result.getSize());
		responseResultPage.setCurrent(result.getCurrent());
		responseResultPage.setRecords(result.getRecords());
		responseResultPage.setTotal(result.getTotal());
		responseResultPage.setCode(1);
		responseResultPage.setMessage("查询成功");
		return responseResultPage;
	}

	@Override
	public ResponseResult<List<UserIcbcTempDTO>> queryUserIcbcTemp(Integer type) {
		ResponseResult<List<UserIcbcTempDTO>> responseResult = new ResponseResult<List<UserIcbcTempDTO>>();
		List<UserIcbcTemp> userIcbcTemps = userIcbcTempMapper.selectList(new QueryWrapper<UserIcbcTemp>().eq("temp_type", type));
		List<UserIcbcTempDTO> userIcbcTempDTOS = new ArrayList<>();
		if (userIcbcTemps != null && userIcbcTemps.size() > 0) {
			for (int i = 0; i < userIcbcTemps.size(); i++) {
				UserIcbcTempDTO userIcbcTempDTO = new UserIcbcTempDTO();
				BeanUtils.copyProperties(userIcbcTemps.get(i), userIcbcTempDTO);
				userIcbcTempDTOS.add(userIcbcTempDTO);
			}
			responseResult.setCode(1);
			responseResult.setResult(userIcbcTempDTOS);
		} else {
			responseResult.setCode(0);
			responseResult.setMessage("未查询到数据");
		}
		return responseResult;
	}

	@Override
	@Transactional
	public ResponseBase saveUserReviewInfo(UserReviewInfoDTO userReviewInfoDTO) {
		ResponseBase responseBase = new ResponseBase();
		try {
			UserReviewInfo userReviewInfo = new UserReviewInfo();
			BeanUtils.copyProperties(userReviewInfoDTO, userReviewInfo);
			if (userReviewInfo.getUuid() == null || "".equals(userReviewInfo.getUuid())) {
				List<UserReviewInfo> userReviewInfo1 = userReviewInfoMapper.selectList(
						new QueryWrapper<UserReviewInfo>().eq("auth_code", userReviewInfoDTO.getAuthCode()).eq("review_type", 3));
				if (userReviewInfo1 != null && userReviewInfo1.size() > 0) {
					responseBase.setCode(0);
					responseBase.setMessage("您已提交保存，请勿重复提交！");
					return responseBase;
				}
				//创建
				String uuid = UUID.randomUUID().toString();
				userReviewInfo.setUuid(uuid);
				//获取基地名称，战区名称，基地业务员名称
				if (userReviewInfoDTO.getUpOrgSeq() != null && userReviewInfoDTO.getUpOrgSeq() != "") {
					UserOrganization userOrganization = organizationMapper.selectById(userReviewInfoDTO.getUpOrgSeq());
					userReviewInfo.setUpOrgZone(userOrganization.getOrgName());
				}
				if (userReviewInfoDTO.getBaseCode() != null && userReviewInfoDTO.getBaseCode() != "") {
					UserOrganization userOrganization = organizationMapper.selectById(userReviewInfoDTO.getBaseCode());
					userReviewInfo.setBaseName(userOrganization.getOrgName());
				}
				if (userReviewInfoDTO.getBasePersonCode() != null && userReviewInfoDTO.getBasePersonCode() != "") {
					UserInfo userInfo = userInfoMapper.selectById(userReviewInfoDTO.getBasePersonCode());
					userReviewInfo.setBasePersonName(userInfo.getRealName());
				}
				//插入数据
				userReviewInfo.setReviewType(3);
				userReviewInfo.setCreateTime(LocalDateTime.now());
				mapper.insert(userReviewInfo);
				//查看建档数据
				List<UserIcbcFilingDTO> userIcbcFilingDTOList = userReviewInfoDTO.getUserIcbcFilingDTOList();
				if (userIcbcFilingDTOList != null && userIcbcFilingDTOList.size() > 0) {
					for (int i = 0; i < userIcbcFilingDTOList.size(); i++) {
						UserIcbcFiling userIcbcFiling = new UserIcbcFiling();
						String uuidFiling = UUID.randomUUID().toString();
						BeanUtils.copyProperties(userIcbcFilingDTOList.get(i), userIcbcFiling);
						userIcbcFiling.setUuid(uuidFiling);
						userIcbcFiling.setReviewId(uuid);
						userIcbcFiling.setCreateTime(LocalDateTime.now());
						userIcbcFiling.setCreateUser(JwtTokenUtil.currUser());
						userIcbcFilingMapper.insert(userIcbcFiling);
					}
				}
			} else {
				//更新
				//获取基地名称，战区名称，基地业务员名称
				if (userReviewInfoDTO.getUpOrgSeq() != null && userReviewInfoDTO.getUpOrgSeq() != "") {
					UserOrganization userOrganization = organizationMapper.selectById(userReviewInfoDTO.getUpOrgSeq());
					userReviewInfo.setUpOrgZone(userOrganization.getOrgName());
				}
				if (userReviewInfoDTO.getBaseCode() != null && userReviewInfoDTO.getBaseCode() != "") {
					UserOrganization userOrganization = organizationMapper.selectById(userReviewInfoDTO.getBaseCode());
					userReviewInfo.setBaseName(userOrganization.getOrgName());
				}
				if (userReviewInfoDTO.getBasePersonCode() != null && userReviewInfoDTO.getBasePersonCode() != "") {
					UserInfo userInfo = userInfoMapper.selectById(userReviewInfoDTO.getBasePersonCode());
					userReviewInfo.setBasePersonName(userInfo.getRealName());
				}
				BeanUtils.copyProperties(userReviewInfoDTO, userReviewInfo);
				userReviewInfo.setUpdateTime(LocalDateTime.now());
				mapper.updateById(userReviewInfo);
				//查看建档数据
				List<UserIcbcFilingDTO> userIcbcFilingDTOList = userReviewInfoDTO.getUserIcbcFilingDTOList();
				//将所有数据修改未失效状态
				UserIcbcFiling userIcbcFiling = new UserIcbcFiling();
				userIcbcFiling.setReviewId(userReviewInfo.getUuid());
				userIcbcFiling.setStatus(0);
				userIcbcFilingMapper.update(userIcbcFiling, new QueryWrapper<UserIcbcFiling>().eq("review_id", userReviewInfo.getUuid()));
				//重新插入数据
				if (userIcbcFilingDTOList != null && userIcbcFilingDTOList.size() > 0) {
					for (int i = 0; i < userIcbcFilingDTOList.size(); i++) {
						UserIcbcFiling userIcbcFilingNew = new UserIcbcFiling();
						String uuidFiling = UUID.randomUUID().toString();
						BeanUtils.copyProperties(userIcbcFilingDTOList.get(i), userIcbcFilingNew);
						userIcbcFilingNew.setUuid(uuidFiling);
						userIcbcFilingNew.setReviewId(userReviewInfo.getUuid());
						userIcbcFilingNew.setCreateTime(LocalDateTime.now());
						userIcbcFilingNew.setCreateUser(JwtTokenUtil.currUser());
						userIcbcFilingMapper.insert(userIcbcFilingNew);
					}
				}
			}
		} catch (Exception e) {
			log.error("拟稿暂存saveUserReviewInfo接口报错》》》》》》》》》》》》》》》》", e);
			responseBase.setCode(0);
			responseBase.setMessage("保存失败" + e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return responseBase;
		}
		responseBase.setCode(1);
		responseBase.setMessage("保存成功");
		return responseBase;
	}

	@Override
	@Transactional
	public ResponseBase edidUserIcbcTemp(UserIcbcTempDTO UserIcbcTempDTO) {
		ResponseBase responseBase = new ResponseBase();
		if (UserIcbcTempDTO.getUuid() == null || "".equals(UserIcbcTempDTO.getUuid())) {
			//创建
			String uuid = UUID.randomUUID().toString();
			UserIcbcTemp userIcbcTemp = new UserIcbcTemp();
			userIcbcTemp.setUuid(uuid);
			BeanUtils.copyProperties(UserIcbcTempDTO, userIcbcTemp);
			userIcbcTempMapper.insert(userIcbcTemp);
		} else {
			UserIcbcTemp userIcbcTemp = new UserIcbcTemp();
			BeanUtils.copyProperties(UserIcbcTempDTO, userIcbcTemp);
			userIcbcTempMapper.updateById(userIcbcTemp);
		}
		responseBase.setCode(1);
		responseBase.setMessage("成功");
		return responseBase;
	}

	@Override
	@Transactional
	public ResponseBase jumpUserReviewInfo(IcbcAnswerRequest icbcAnswerRequest) {
		ResponseBase responseBase = new ResponseBase();
		UserReviewRecord userReviewRecord = new UserReviewRecord();
		String uuid = UUID.randomUUID().toString();
		userReviewRecord.setUuid(uuid);
		UserReviewInfo userReviewInfo = userReviewInfoMapper.selectById(icbcAnswerRequest.getSubmitId());
		if (userReviewInfo == null) {
			responseBase.setCode(0);
			responseBase.setMessage("未查询到数据，发现异常id为》》》》》" + icbcAnswerRequest.getSubmitId());
			return responseBase;
		}
		int isSwith = userReviewInfo.getIcbcSwitch() == null ? 0 : userReviewInfo.getIcbcSwitch();
		if (isSwith == 2) {
			responseBase.setCode(0);
			responseBase.setMessage("当前状态不支持银行审核----IcbcSwitch值》》》》》》" + isSwith);
			return responseBase;
		}
		//判断是否是更新支付信息 只允许更新一次
		//店铺的authcode一般只会存在一条
		List<UserStoreInfo> userStoreInfo = storeMapper.selectList(new QueryWrapper<UserStoreInfo>().eq("auth_code", userReviewInfo.getAuthCode()));
		if (userStoreInfo != null && userStoreInfo.size() > 0) {
			boolean type = false;
			//已经生成店铺表示 当前是更新支付信息，则判断支付信息是否是解锁状态
			for (int i = 0; i < userStoreInfo.size(); i++) {
				Integer icbcSwith = userStoreInfo.get(i).getIcbcSwitch() == null ? 0 : userStoreInfo.get(i).getIcbcSwitch();
				//如果状态为1 则表示已解锁
				if (icbcSwith == 1) {
					type = true;
					String option = "解锁支付信息：";
					UserStoreInfo userStoreInfo1 = userStoreInfo.get(i);
					if (icbcAnswerRequest.getMerId() != null) {
						option += icbcAnswerRequest.getMerId().toString();
						userStoreInfo1.setMerid(icbcAnswerRequest.getMerId());
					}
					if (icbcAnswerRequest.getMerPrtclNo() != null) {
						option += icbcAnswerRequest.getMerPrtclNo();
						userStoreInfo1.setAgreement(icbcAnswerRequest.getMerPrtclNo());
					}
					if (icbcAnswerRequest.getIcbcAppid() != null) {
						option += icbcAnswerRequest.getIcbcAppid();
						userStoreInfo1.setWechatId(icbcAnswerRequest.getIcbcAppid());
					}
					if (icbcAnswerRequest.getCreateDate() != null) {
						option += icbcAnswerRequest.getCreateDate();
						userStoreInfo1.setCreatedDate(icbcAnswerRequest.getCreateDate());
					}
					userStoreInfo1.setIcbcUpdateTime(LocalDateTime.now());
					userStoreInfo1.setUpdateTime(LocalDateTime.now());
					userStoreInfo1.setUpdateUser(JwtTokenUtil.currUser());
					//更新一次以后将当前支付状态修改为已锁定
					userStoreInfo1.setIcbcSwitch(2);
					storeMapper.updateById(userStoreInfo1);

					//增加流程信息
					userReviewRecord.setReviewUser("icbc");
					userReviewRecord.setReviewTime(LocalDateTime.now());
					userReviewRecord.setReviewId(userReviewInfo.getUuid());
					userReviewRecord.setOrgSeq(userStoreInfo1.getOrgSeq());
					userReviewRecord.setReviewOption(option);
					userReviewRecord.setReviewStatus(9);
					userReviewRecordMapper.insert(userReviewRecord);
					return responseBase.setCode(1).setMessage("修改成功");
				}
			}
			if (!type) {
				responseBase.setCode(0);
				responseBase.setMessage("当前店铺支付信息没有被解锁，无法修改");
				return responseBase;
			}
		}

		String submitStatus = icbcAnswerRequest.getSubmitStatus();
		//增加流程信息
//		userReviewRecord.setReviewUser("icbc");
		userReviewRecord.setReviewTime(LocalDateTime.now());
		userReviewRecord.setReviewId(userReviewInfo.getUuid());
		if (submitStatus == "2" || "2".equals(submitStatus)) {
			//银行通过，插入银行返回基本信息，
			userReviewInfo.setMerid(icbcAnswerRequest.getMerId());
			userReviewInfo.setAgreement(icbcAnswerRequest.getMerPrtclNo());
			userReviewInfo.setWechatId(icbcAnswerRequest.getIcbcAppid());
			userReviewInfo.setCreatedDate(icbcAnswerRequest.getCreateDate());
			userReviewInfo.setUpdateTime(LocalDateTime.now());
			userReviewInfo.setIcbcReviewStatus(1);
			//设置银行不可审核
			userReviewInfo.setIcbcSwitch(2);
			userReviewInfo.setReviewStatus(7);
			userReviewRecord.setReviewOption("审核通过");
			userReviewRecord.setReviewStatus(7);
			userReviewInfoMapper.updateById(userReviewInfo);
			userReviewRecordMapper.insert(userReviewRecord);
		} else {
			//银行未通过，直接返回至用户申请
			userReviewInfo.setIcbcReviewOpinion(icbcAnswerRequest.getSubmitMsg());
			userReviewInfo.setIcbcReviewStatus(2);
			//设置银行可审核
			userReviewInfo.setIcbcSwitch(1);
			userReviewInfo.setReviewStatus(8);
			userReviewInfo.setUpdateTime(LocalDateTime.now());
			userReviewRecord.setReviewStatus(8);
			userReviewRecord.setReviewOption(icbcAnswerRequest.getSubmitMsg());
			userReviewInfoMapper.updateById(userReviewInfo);
			userReviewRecordMapper.insert(userReviewRecord);
		}
		responseBase.setCode(1);
		responseBase.setMessage("审核成功");
		return responseBase;
	}

	@Override
	public ResponseBase paymentInformation(String uuid, Integer isSwith) {
		ResponseBase responseBase = new ResponseBase();
		try {
			UserStoreInfo userStoreInfo = new UserStoreInfo();
			userStoreInfo.setUuid(uuid);
			userStoreInfo.setIcbcSwitch(isSwith);
			responseBase.setCode(1);
			responseBase.setMessage("操作成功");
			storeMapper.updateById(userStoreInfo);
		} catch (Exception e) {
			responseBase.setCode(0);
			responseBase.setMessage("解锁失败");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return responseBase;
	}
	//巡店一个门店的auth_code 对应两个角色 店长和店主

	@Override
	public Map<String, Object> getAllShop(String code) {
		Map<String, Object> maplist = new HashMap<String, Object>();
		List<Map<String, Object>> lsit = new ArrayList<Map<String, Object>>();
		//org_type  2 表示战区  3表市基地 4门店
		try {
			List<Map<String, Object>> userorganization = organizationMapper.selectMaps(new QueryWrapper<UserOrganization>().eq("org_type", 4).eq("up_org_seq", code));
			if (userorganization != null && userorganization.size() > 0) {
				for (int i = 0; i < userorganization.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", userorganization.get(i).get("org_seq"));
					map.put("name", userorganization.get(i).get("org_name"));
					lsit.add(map);
				}
				maplist.put("code", 1);
				maplist.put("message", "查询成功");
			} else {
				maplist.put("code", 1);
				maplist.put("message", "未查询到数据");
			}
		} catch (Exception e) {
			maplist.put("code", 0);
			maplist.put("message", e);
		}
		maplist.put("result", lsit);
		return maplist;
	}
/*
		UserInfo userInfo = new UserInfo();
		UserLoginInfo userLoginInfo = new UserLoginInfo();
		UserUserRoleRelation userUserRoleRelation = new UserUserRoleRelation();
		userUserRoleRelation.setUserRoleRelationId(UUID.randomUUID().toString());
		password = MD5Util.toMD5("888888");
		if (userType == "5" || "5".equals(userType)) {
			String userId = UUID.randomUUID().toString();
			userInfo.setUserId(userId);
			//生成店长
			userInfo.setUserName(userReviewInfo.getStoreOwnerName());
			//初始密码为电话
			userInfo.setPassword(password);//店长电话
			userInfo.setTel(userReviewInfo.getStoreManagerPhone());
			userInfo.setUserName(userReviewInfo.getStoreManagerPhone());
			userInfo.setRealName(userReviewInfo.getStoreManagerName());
			userInfo.setStatus(1);
			userInfo.setIsLock(1);
			userInfo.setUserType("5");
			userInfo.setHeadPicUrl(userReviewInfo.getStoreManagerPic());
			userLoginInfo.setPassword(password);
			userinfoManager = userInfo;
			//分配店长权限
			UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("role_req", store_maneger));
			userUserRoleRelation.setRoleId(userRole.getRoleId());
			userLoginInfo.setUuid(UUID.randomUUID().toString());
			userLoginInfo.setOrgSeq(orgseq);
			userLoginInfo.setAuthCode(userReviewInfo.getAuthCode()+"A");//生成两个AuthCode 其中店长后面都一个A
			userLoginInfo.setUserId(userId);
			userLoginInfo.setStatus(1);
			userLoginInfo.setCreateTime(LocalDateTime.now());
			userLoginInfo.setUpdateTime(LocalDateTime.now());
			userLoginInfo.setCreateUser(JwtTokenUtil.currUser());
			userLoginInfo.setUpdateUser(JwtTokenUtil.currUser());
			userLoginInfoMapper.insert(userLoginInfo);
			//废弃
//			userInfo.setOrgSeq(userReviewInfo.getOrgSeq());
			userInfo.setUserType(userType);
			userInfo.setCreateTime(new Date());
			userInfo.setSalt((new Random().nextInt(100)) + "");//盐

			// 1.0 向 用户基本信息 t_user_info 插入数据
			UserInfoMapperMapper.insert(userInfo);

			userUserRoleRelation.setUserId(userId);
			userUserRoleRelation.setCreateUser(JwtTokenUtil.currUser());
			userUserRoleRelation.setStatus(1);
			userUserRoleRelation.setCreateTime(LocalDateTime.now());
			uerUserRoleRelationMapper.insert(userUserRoleRelation);
		} else {
			String userId = UUID.randomUUID().toString();
			userInfo.setUserId(userId);
			//生成店主
			userInfo.setPassword(password);
			userInfo.setTel(userReviewInfo.getStoreOwnerPhone());
			userInfo.setUserName(userReviewInfo.getStoreOwnerPhone());
			userInfo.setRealName(userReviewInfo.getStoreOwnerName());
			userInfo.setStatus(1);
			userInfo.setIsLock(1);
			userInfo.setUserType("4");
			userInfo.setHeadPicUrl(userReviewInfo.getStoreOwnerPic());
			userLoginInfo.setPassword(password);
			userinfoOwn = userInfo;
			//分配店主权限
			UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("role_req", store_owner));
			userUserRoleRelation.setRoleId(userRole.getRoleId());
			userLoginInfo.setUuid(UUID.randomUUID().toString());
			userLoginInfo.setOrgSeq(orgseq);
			userLoginInfo.setAuthCode(userReviewInfo.getAuthCode());
			userLoginInfo.setUserId(userId);
			userLoginInfo.setStatus(1);
			userLoginInfo.setCreateTime(LocalDateTime.now());
			userLoginInfo.setUpdateTime(LocalDateTime.now());
			userLoginInfo.setCreateUser(JwtTokenUtil.currUser());
			userLoginInfo.setUpdateUser(JwtTokenUtil.currUser());
			userLoginInfoMapper.insert(userLoginInfo);
			//废弃
//			userInfo.setOrgSeq(userReviewInfo.getOrgSeq());
			userInfo.setUserType(userType);
			userInfo.setCreateTime(new Date());
			userInfo.setSalt((new Random().nextInt(100)) + "");//盐

			// 1.0 向 用户基本信息 t_user_info 插入数据
			UserInfoMapperMapper.insert(userInfo);

			userUserRoleRelation.setUserId(userId);
			userUserRoleRelation.setCreateUser(JwtTokenUtil.currUser());
			userUserRoleRelation.setStatus(1);
			userUserRoleRelation.setCreateTime(LocalDateTime.now());
			uerUserRoleRelationMapper.insert(userUserRoleRelation);
		}
	
		//调用短信平台
		//生成店长账号
		UmsParameter umsParameter = new UmsParameter();
		try {
			if (userType == "4" || "4".equals(userType)) {
				//生成店主账号
				umsParameter.setParmA(userReviewInfo.getAuthCode());
				umsParameter.setParmB("888888");
				umsParameter.setPhone(userReviewInfo.getStoreOwnerPhone());
				umsParameter.setCode(code);
				foreignService.getUmsDto(umsParameter);
			}
			if (userType == "5" || "5".equals(userType)) {
				//生成店长账号
				umsParameter.setParmA(userReviewInfo.getAuthCode()+"A");
				umsParameter.setParmB("888888");
				umsParameter.setPhone(userReviewInfo.getStoreManagerPhone());
				umsParameter.setCode(code);
				foreignService.getUmsDto(umsParameter);
			}
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
	}
*/

	/**
	 * 创建店长或者店主账号 和 店铺认证方式
	 */
	public void createStoreAccount2(UserStoreInfo userReviewInfo, String userType, String password, String orgseq) {
		UserInfo userInfo = new UserInfo();
		UserLoginInfo userLoginInfo = new UserLoginInfo();
		UserUserRoleRelation userUserRoleRelation = new UserUserRoleRelation();
		userUserRoleRelation.setUserRoleRelationId(UUID.randomUUID().toString());
		String userId = UUID.randomUUID().toString();
		password = MD5Util.toMD5("888888");
		userInfo.setUserId(userId);
		if (userType == "5" || "5".equals(userType)) {//生成店长
			userInfo.setUserName(userReviewInfo.getStoreOwnerName());
			//初始密码为电话
			userInfo.setPassword(password);//店长电话
			userInfo.setTel(userReviewInfo.getStoreManagerPhone());
			userInfo.setUserName(userReviewInfo.getStoreManagerPhone());
			userInfo.setRealName(userReviewInfo.getStoreManagerName());
			userInfo.setStatus(1);
			userInfo.setIsLock(1);
			userInfo.setUserType("5");
			userInfo.setHeadPicUrl(userReviewInfo.getStoreManagerPic());
			userLoginInfo.setPassword(password);
			userinfoManager = userInfo;
			//分配店长权限
			UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("role_req", store_maneger));
			userUserRoleRelation.setRoleId(userRole.getRoleId());
		} else {//生成店主
			userInfo.setPassword(password);//店主电话
			userInfo.setTel(userReviewInfo.getStoreOwnerPhone());
			userInfo.setUserName(userReviewInfo.getStoreOwnerPhone());
			userInfo.setRealName(userReviewInfo.getStoreOwnerName());
			userInfo.setStatus(1);
			userInfo.setIsLock(1);
			userInfo.setUserType("4");
			userInfo.setHeadPicUrl(userReviewInfo.getStoreOwnerPic());
			userLoginInfo.setPassword(password);
			userinfoOwn = userInfo;
			//分配店主权限
			UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("role_req", store_owner));
			userUserRoleRelation.setRoleId(userRole.getRoleId());
			userLoginInfo.setUuid(UUID.randomUUID().toString());
			userLoginInfo.setOrgSeq(orgseq);
			userLoginInfo.setAuthCode(userReviewInfo.getAuthCode());
			userLoginInfo.setUserId(userId);
			userLoginInfo.setStatus(1);
			userLoginInfo.setCreateTime(LocalDateTime.now());
			userLoginInfo.setUpdateTime(LocalDateTime.now());
			userLoginInfo.setCreateUser(JwtTokenUtil.currUser());
			userLoginInfo.setUpdateUser(JwtTokenUtil.currUser());
			userLoginInfoMapper.insert(userLoginInfo);
		}
		//废弃
		//userInfo.setOrgSeq(userReviewInfo.getOrgSeq());
		userInfo.setUserType(userType);
		userInfo.setCreateTime(new Date());
		userInfo.setSalt((new Random().nextInt(100)) + "");//盐

		// 1.0 向 用户基本信息 t_user_info 插入数据
		UserInfoMapperMapper.insert(userInfo);

		userUserRoleRelation.setUserId(userId);
		userUserRoleRelation.setCreateUser(JwtTokenUtil.currUser());
		userUserRoleRelation.setStatus(1);
		userUserRoleRelation.setCreateTime(LocalDateTime.now());
		uerUserRoleRelationMapper.insert(userUserRoleRelation);
		//调用短信平台
		//生成店长账号
		UmsParameter umsParameter = new UmsParameter();
		try {
			if (userType == "4" || "4".equals(userType)) {//生成店主
				//生成店主账号
				umsParameter.setParmA(userReviewInfo.getAuthCode());
				//umsParameter.setParmA(userReviewInfo.getStoreOwnerPhone());
				umsParameter.setParmB("888888");
				umsParameter.setPhone(userReviewInfo.getStoreOwnerPhone());
				umsParameter.setCode(code);
				foreignService.getUmsDto(umsParameter);
			}
			if (userType == "5" || "5".equals(userType)) {//生成店主
				//生成店主账号
				//umsParameter.setParmA(userReviewInfo.getAuthCode());
				umsParameter.setParmA(userReviewInfo.getStoreManagerPhone());
				umsParameter.setParmB("888888");
				umsParameter.setPhone(userReviewInfo.getStoreManagerPhone());
				umsParameter.setCode(code);
				foreignService.getUmsDto(umsParameter);
			}
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
	}

	@Override
	@Transactional
	public ResponseBase finalReview2(PreliminaryExamination preliminaryExamination) {
		ResponseBase responseBase = new ResponseBase();
		UserReviewRecord userReviewRecord = new UserReviewRecord();
		String uuid = UUID.randomUUID().toString();
		userReviewRecord.setUuid(uuid);
		UserReviewInfo userReviewMap = userReviewInfoMapper.selectOne(new QueryWrapper<UserReviewInfo>().eq("uuid", preliminaryExamination.getUuid()));
		try {
			if (userReviewMap != null) {
				List<UserOrgRelation> userlist = userOrgRelationMapper.selectList(new QueryWrapper<UserOrgRelation>().eq("user_id", JwtTokenUtil.currUser()));
				Boolean preliminary = false;
				if (userlist != null && userlist.size() > 0) {
					for (int i = 0; i < userlist.size(); i++) {
						//判断是否是总部
						int type = userlist.get(i).getUserType();
						if (type == 1) {
							preliminary = true;
						}
					}
				}
				if (!preliminary) {
					responseBase.setCode(0);
					responseBase.setMessage("没有审核权限！");
					return responseBase;
				}
				//判断是否重复点击
				//如果当前店铺已经生成，不支持重复点击
				UserStoreInfo userStoreInfo = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("auth_code", userReviewMap.getAuthCode()).groupBy("auth_code"));
				if (userStoreInfo != null) {
					responseBase.setMessage("店铺已经生成！请前往【店铺档案】查看");
					return responseBase;
				}
				userReviewMap.setFinalReviewUser(JwtTokenUtil.currUser());
				userReviewMap.setFinalReviewOpinion(preliminaryExamination.getFinalReviewOpinion());
				userReviewRecord.setReviewOption(preliminaryExamination.getFinalReviewOpinion());
				userReviewRecord.setReviewUser(JwtTokenUtil.currUser());
				userReviewRecord.setReviewTime(LocalDateTime.now());
				userReviewRecord.setReviewId(preliminaryExamination.getUuid());
				//（审核状态(1-已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过)）
				if ("1".equals(preliminaryExamination.getReviewStatus()) || Integer.parseInt(preliminaryExamination.getReviewStatus().toString()) == 1) {
					//设置为复审通过
					userReviewMap.setReviewStatus(5);
					userReviewMap.setReviewType(1);
					userReviewRecord.setReviewStatus(5);
				} else {
					//设置为复审不通过
					userReviewMap.setReviewStatus(4);
					userReviewMap.setReviewType(1);//设置为申请审核
					userReviewMap.setUpdateTime(LocalDateTime.now());
					userReviewInfoMapper.updateById(userReviewMap);
					userReviewRecord.setReviewStatus(4);
					userReviewRecordMapper.insert(userReviewRecord);
					responseBase.setCode(1);
					responseBase.setMessage("审核成功");
					return responseBase;
				}
				userReviewMap.setUpdateTime(LocalDateTime.now());
				userReviewInfoMapper.updateById(userReviewMap);
				//插入日志表
				userReviewRecordMapper.insert(userReviewRecord);

				String userType = "5";//用户类型(1集团人员2战区人员3基地人员4店主5店长6店员)
				// 生成组织机构信息
				UserOrganization orgNization = new UserOrganization();
				String orgseq = UUID.randomUUID().toString();
				orgNization.setOrgSeq(orgseq);
				orgNization.setOrgName(userReviewMap.getStoreName());
				orgNization.setUpOrgSeq(userReviewMap.getBaseCode());//基地编码
				orgNization.setOrgType("4");//门店
				orgNization.setStatus(1);
				orgNization.setCreateUser(JwtTokenUtil.currUser());
				orgNization.setCreateTime(LocalDateTime.now());
				orgNization.setUpdateUser(JwtTokenUtil.currUser());
				orgNization.setUpdateTime(LocalDateTime.now());
				organizationMapper.insert(orgNization);
				//生成门店信息
				UserStoreInfo userStoreinfo = new UserStoreInfo();
				BeanUtils.copyProperties(userReviewMap, userStoreinfo);
				String mdUUid = UUID.randomUUID().toString();
				//防止将申请信息的uuid 将店铺uuid覆盖
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime startDateTime = LocalDate.parse(userReviewMap.getOpenDate(), df).atStartOfDay();
				userStoreinfo.setWechatId(userReviewMap.getWechatId());
				userStoreinfo.setOpenDate(startDateTime);
				userStoreinfo.setUuid(mdUUid);
				userStoreinfo.setOrgSeq(orgseq);
				userStoreinfo.setStatus(1);
				userStoreinfo.setOpenStatus(1);
				userStoreinfo.setCreateUser(JwtTokenUtil.currUser());
				userStoreinfo.setUpdateUser(JwtTokenUtil.currUser());
				userStoreinfo.setCreateTime(LocalDateTime.now());
				userStoreinfo.setUpdateTime(LocalDateTime.now());
				userStoreinfo.setStoreCode(generateMemberSeq(MEMBER_SEQ_KEY));
//					//初始化门店等级 默认 白银等级
//					StoreGradelevelRule storeGradelevelRule = storeGradelevelRuleMapper.selectOne
//							(new QueryWrapper<StoreGradelevelRule>().eq("begin_score",0).eq("STATUS",1));
//					userStoreinfo.setGradelevelId(storeGradelevelRule.getUuid());
				storeMapper.insert(userStoreinfo);
				//绑定工行建库信息
				List<UserIcbcFiling> userIcbcFilingList = userIcbcFilingMapper.selectList(new QueryWrapper<UserIcbcFiling>().eq("review_id", userReviewMap.getUuid()).eq("status", 1));
				if (userIcbcFilingList != null && userIcbcFilingList.size() > 0) {
					for (int i = 0; i < userIcbcFilingList.size(); i++) {
						userIcbcFilingList.get(i).setStoreId(mdUUid);
						userIcbcFilingMapper.updateById(userIcbcFilingList.get(i));
					}
				}
				//初始化生成门店积分信息
				storeBonuspoint = new StoreBonuspoint();
				storeBonuspoint.setUuid(UUID.randomUUID().toString());
				storeBonuspoint.setOrgSeq(orgseq);
				storeBonuspoint.setCurrentScore(new BigDecimal("0"));
				storeBonuspoint.setTotalScore(new BigDecimal("0"));
				storeBonuspoint.setCreaterUser(JwtTokenUtil.currUser());
				storeBonuspoint.setUpdateUser(JwtTokenUtil.currUser());
				storeBonuspoint.setCreateTime(LocalDateTime.now());
				storeBonuspoint.setUpdateTime(LocalDateTime.now());
				storeBonuspointMapper.insert(storeBonuspoint);
				//初始化商品强上架
				ResponseBase res = goodsForeignService.initGoods(userStoreinfo.getOrgSeq(), userStoreinfo.getUpOrgSeq());
				if (res.getCode() == 1) {
					System.out.println("----------初始化商品强上架操作成功----------");
				} else {
					System.out.println("----------初始化商品强上架操作失败----------");
				}
				//通过电话号码判断是否已经存在店主账号,不存在则生成，存在则不做操作
				Boolean typeOwn = true;
				Boolean typeManager = true;
				userinfoOwn = userInfoMapper.selectOne
						(new QueryWrapper<UserInfo>().eq("tel", userReviewMap.getStoreOwnerPhone()).groupBy("tel"));
				Random random = new Random();
				int randomNum = random.nextInt(1000000);
				String password1 = String.format("%06d", randomNum);
				if (userinfoOwn == null) {
					//店主
					userType = "4";
					createStoreAccount2(userStoreinfo, userType, password1, orgseq);
				} else {
					String password4 = MD5Util.toMD5("888888");
					UserUserRoleRelation userUserRoleRelation = new UserUserRoleRelation();
					userUserRoleRelation.setUserRoleRelationId(UUID.randomUUID().toString());
					//分配店主权限
					UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("role_req", store_owner));
					userUserRoleRelation.setRoleId(userRole.getRoleId());
					userUserRoleRelation.setUserId(userinfoOwn.getUserId());
					userUserRoleRelation.setCreateUser(JwtTokenUtil.currUser());
					userUserRoleRelation.setStatus(1);
					userUserRoleRelation.setCreateTime(LocalDateTime.now());
					uerUserRoleRelationMapper.insert(userUserRoleRelation);

					UserLoginInfo userLoginInfo = new UserLoginInfo();
					userLoginInfo.setUuid(UUID.randomUUID().toString());
					userLoginInfo.setOrgSeq(orgseq);
					userLoginInfo.setAuthCode(userReviewMap.getAuthCode());
					userLoginInfo.setUserId(userinfoOwn.getUserId());
					userLoginInfo.setPassword(password4);
					userLoginInfo.setStatus(1);
					userLoginInfo.setCreateTime(LocalDateTime.now());
					userLoginInfo.setUpdateTime(LocalDateTime.now());
					userLoginInfo.setCreateUser(JwtTokenUtil.currUser());
					userLoginInfo.setUpdateUser(JwtTokenUtil.currUser());
					userLoginInfoMapper.insert(userLoginInfo);
					//调用短信平台
					//发送店主短信
					UmsParameter umsParameter = new UmsParameter();
					umsParameter.setParmA(userReviewMap.getAuthCode());
					umsParameter.setParmB("888888");
					umsParameter.setPhone(userStoreinfo.getStoreOwnerPhone());
					umsParameter.setCode(code);
					foreignService.getUmsDto(umsParameter);
				}
				userinfoManager = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel", userReviewMap.getStoreManagerPhone()).groupBy("tel"));
				String password2 = String.format("%06d", randomNum);
				if (userinfoManager == null) {
					typeManager = false;
					//店长
					userType = "5";
					createStoreAccount2(userStoreinfo, userType, password2, orgseq);
				} else {
					typeManager = false;
					String password3 = MD5Util.toMD5("888888");
					UserUserRoleRelation userUserRoleRelation = new UserUserRoleRelation();
					userUserRoleRelation.setUserRoleRelationId(UUID.randomUUID().toString());
					//分配店长权限
					UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("role_req", store_maneger));
					userUserRoleRelation.setRoleId(userRole.getRoleId());
					userUserRoleRelation.setUserId(userinfoManager.getUserId());
					userUserRoleRelation.setCreateUser(JwtTokenUtil.currUser());
					userUserRoleRelation.setStatus(1);
					userUserRoleRelation.setCreateTime(LocalDateTime.now());
					uerUserRoleRelationMapper.insert(userUserRoleRelation);
				}
				//生成店主账号
				userOrgRelation = new UserOrgRelation();
				userOrgRelation.setUuid(UUID.randomUUID().toString());
				userOrgRelation.setUserId(userinfoOwn.getUserId());
				userOrgRelation.setOrgSeq(orgseq);
				userOrgRelation.setUserType(4);
				userOrgRelation.setStatus(userinfoOwn.getStatus());
				userOrgRelation.setCreateUser(JwtTokenUtil.currUser());
				userOrgRelation.setUpdateUser(JwtTokenUtil.currUser());
				userOrgRelation.setCreateTime(LocalDateTime.now());
				userOrgRelation.setUpdateTime(LocalDateTime.now());
				userOrgRelationMapper.insert(userOrgRelation);

				//生成店长账号
				userOrgRelation = new UserOrgRelation();
				userOrgRelation.setUuid(UUID.randomUUID().toString());
				userOrgRelation.setUserId(userinfoManager.getUserId());
				userOrgRelation.setOrgSeq(orgseq);
				userOrgRelation.setUserType(5);
				userOrgRelation.setStatus(userinfoManager.getStatus());
				userOrgRelation.setCreateUser(JwtTokenUtil.currUser());
				userOrgRelation.setUpdateUser(JwtTokenUtil.currUser());
				userOrgRelation.setCreateTime(LocalDateTime.now());
				userOrgRelation.setUpdateTime(LocalDateTime.now());
				userOrgRelationMapper.insert(userOrgRelation);
				responseBase.setCode(1);
				responseBase.setMessage("审核成功");
			} else {
				responseBase.setCode(0);
				responseBase.setMessage("审核失败");
			}
		} catch (Exception e) {
			responseBase.setCode(0);
			responseBase.setMessage("审核失败----" + e);
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return responseBase;
	}

	@Override
	public ResponseBase addAuhCode(AddAuthCode addAuthCode) {
		ResponseBase responseBase = new ResponseBase();
		UserStoreVerify userStoreVerify = new UserStoreVerify();
		if (StringUtils.isEmpty(addAuthCode.getAuthCode())) {
			responseBase.setCode(0).setMessage("店铺申请码不能为空");
			return responseBase;
		}
		UserStoreVerify userStoreVerifys = userStoreVerifyMapper.selectOne(new QueryWrapper<UserStoreVerify>().eq("auth_code", addAuthCode.getAuthCode()));
		if (userStoreVerifys != null) {
			responseBase.setCode(0).setMessage("当前店铺已经存在，店铺名称为【" + userStoreVerifys.getCompanyName() + "】");
			return responseBase;
		}
		userStoreVerify.setUuid(UUID.randomUUID().toString())
				.setAuthCode(addAuthCode.getAuthCode())
				.setDate(LocalDateTime.now())
				.setCompanyName(addAuthCode.getCompanyName());
		userStoreVerifyMapper.insert(userStoreVerify);

		responseBase.setCode(1).setMessage("成功");
		return responseBase;
	}

	@Override
	public ResponseBase addAccount(StoreAccountDTO dto) {
		ResponseBase responseBase = new ResponseBase();
		try {
			UserLoginInfo u = userLoginInfoMapper.selectOne(new QueryWrapper<UserLoginInfo>().eq("user_id", dto.getUserId()).eq("org_seq", dto.getOrgSeq()));
			if (u != null) {
				responseBase.setCode(0).setMessage("账号已存在!");
				return responseBase;
			}
			UserLoginInfo userLoginInfo = new UserLoginInfo()
					.setUuid(UUID.randomUUID().toString())
					.setAuthCode(dto.getAuthCode())
					.setUserId(dto.getUserId())
					.setOrgSeq(dto.getOrgSeq())
					.setCreateTime(LocalDateTime.now())
					.setUpdateTime(LocalDateTime.now())
					.setStatus(1)
					.setCreateUser(JwtTokenUtil.currUser())
					.setPassword(MD5Util.toMD5(dto.getPassword()));
			userLoginInfoMapper.insert(userLoginInfo);
			responseBase.setCode(1).setMessage("新增账号成功!");
		} catch (Exception e) {
			log.error("新增账号失败:{}", e.getMessage());
			responseBase.setCode(0).setMessage("新增账号失败!");
		}
		return responseBase;
	}

	@Override
	public ResponseResultPage<UserStoreVerifyDTO> getAllAuthCode(PageData<AddAuthCode> pageData) {
		ResponseResultPage<UserStoreVerifyDTO> responseResultPage = new ResponseResultPage<>();
		Page<AddAuthCode> page = new Page<>(pageData.getCurrent(), pageData.getSize());
		Page<UserStoreVerifyDTO> result = userInfoMapper.getAllAuthCode(page, pageData.getCondition());
		responseResultPage.setSize(result.getSize());
		responseResultPage.setCurrent(result.getCurrent());
		responseResultPage.setRecords(result.getRecords());
		responseResultPage.setTotal(result.getTotal());
		responseResultPage.setCode(1);
		responseResultPage.setMessage("查询成功");
		return responseResultPage;
	}

	@Override
	@Transactional
	public ResponseBase deleteLoginAuthCode(String uuid) {
		ResponseBase responseBase = new ResponseBase();
		userStoreVerifyMapper.deleteById(uuid);
		return responseBase.setCode(1).setMessage("删除成功");
	}

	@Override
	public ResponseRecords<UserQryStoreDTO> qryStoreAmount(UserStoreRequest userStoreInfo) {
		ResponseRecords<UserQryStoreDTO> result = new ResponseRecords<>();
		try {
			ArrayList<UserQryStoreDTO> list = userInfoMapper.qryStoreAmount(userStoreInfo);
			result.setCode(1);
			result.setRecords(list);
		} catch (Exception ex) {
			log.error("qryStoreAmount->获取所有店铺信息[" + ex.getMessage() + "]");
			result.setCode(0);
			result.setMessage(ex.getMessage());
		}
		return result;
	}

	@Override
	public ResponseBase deleteClerk(String uuid) {
		ResponseBase responseBase = new ResponseBase();
		//删除店员 主要修改 status=0  然后电话号码前加A
		UserInfo userInfo = userInfoMapper.selectById(uuid);
		UserClerkDTO userclerk = new UserClerkDTO();
		userclerk.setUserId(uuid);
		try {
			if (!StringUtils.isEmpty(userInfo.getTel())) {
				String tel = userInfo.getTel().replaceAll("[a-zA-Z]", "");
				//停用  查询当前号码 是否已经被注销修改过，多少次就多加几个A
				int count = userInfoMapper.selectCount(new QueryWrapper<UserInfo>().like("tel", tel));
				StringBuilder A = new StringBuilder("A");
				for (int i = 0; i < count; i++) {
					A.append("A");
				}
				userInfo.setTel(A + userInfo.getTel());
				userclerk.setTel(A + userInfo.getTel());
			}
			userInfo.setStatus(2);
			userclerk.setStatus(2);
			userInfoMapper.updateById(userInfo);
			//更新data库
			storeActivationService.editClerk(userclerk, null, null);
			//如果 当前店员中都已经完善信息，则更新店铺是否完善信息为 1
			// type=1 说明 除了当前店员，其余所有店员中都已经完善，
			//	type=0 说明 除了当前店员，其余所有店员中还有未完善得，
			//UserOrgRelation 表状态修改为 0
			List<UserOrgRelation> userOrgRelations = userOrgRelationMapper.selectList
					(new QueryWrapper<UserOrgRelation>().eq("user_id", uuid).eq("user_type", 6));
			if (userOrgRelations != null) {
				for (int i = 0; i < userOrgRelations.size(); i++) {
					//修改人员组织关系表状态
					userOrgRelations.get(i).setStatus(0);
					userOrgRelationMapper.updateById(userOrgRelations.get(i));
					//计算当前店铺排除当前id以后得人员信息完整情况
					Integer type = userOrgRelationMapper.getIsWs(userOrgRelations.get(i).getOrgSeq(), uuid);
					storeMapper.update(new UserStoreInfo().setIsInfoComplete(type).setUpdateTime(LocalDateTime.now()).setUpdateUser(JwtTokenUtil.currUser()), new QueryWrapper<UserStoreInfo>()
							.eq("org_seq", userOrgRelations.get(i).getOrgSeq()));
				}
			}
			responseBase.setCode(1).setMessage("删除成功");
		} catch (Exception e) {
			log.error(e.getMessage());
			responseBase.setCode(0).setMessage("删除失败》》》》》》" + e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚
		}
		return responseBase;
	}

	@Override
	public ResponseBase resetPwd(String authCode) {
		ResponseBase responseBase = new ResponseBase();
		UserLoginInfo userLoginInfo = new UserLoginInfo();
		userLoginInfoMapper.update(userLoginInfo.setPassword(MD5Util.toMD5("888888")), new QueryWrapper<UserLoginInfo>().eq("auth_code", authCode));
		responseBase.setCode(1).setMessage("密码重置成功");
		return responseBase;
	}
}
