package com.icss.newretail.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.service.data.DataService;
import com.icss.newretail.service.goods.GoodsTradeService;
import com.icss.newretail.service.member.MemberService;
import com.icss.newretail.service.trade.MyOrderService;
import com.icss.newretail.service.trade.TradeService;
import com.icss.newretail.service.user.ForeignService;
import com.icss.newretail.user.dao.*;
import com.icss.newretail.user.entity.*;
import com.icss.newretail.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.icss.newretail.api.user.AuthMaApi.randomCode;

/**
 * @author jc
 * @date 2020/3/23 20:06
 */
@Service
@Slf4j
public class ForeignServiceImpl implements ForeignService {

	//当前时间
	private static final String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	//集团名称 默认
	private static String spCode = "220741";
	//用户姓名 默认
	private static String loginName = "wly-zmyd";
	//登录密码 默认
	private static String password = "b$axOtvQ65";
	//提交检测方式
	private static String f = "1";
	//电话号码
	private static String phone;
	//模板内容
	private static String messageContent;
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	@Autowired
	protected UserUmsMapper userUmsMapper;
	@Autowired
	protected UserUmsTemplateMapper userUmsTemplateMapper;
	@Autowired
	private StoreMapper storeMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private UserOrganizationMapper userOrganizationMapper;

	@Autowired
	private UserOrganizationYdtMapper userOrganizationYdtMapper;

	@Autowired
	private AuthMaServiceImpl authMaService;

	@Autowired
	private UserOrgRelationMapper userOrgRelationMapper;

	@Autowired
	private LoginMapper loginMapper;

	@Autowired
	private TUserAppletContentMapper appletContentMapper;


	@RpcReference(microserviceName = "member-service", schemaId = "MemberApi")
	private MemberService memberService;
	@RpcReference(microserviceName = "data-service", schemaId = "dataApi")
	private DataService dataService;
	@RpcReference(microserviceName = "trade-service", schemaId = "MyOrderApi")
	private MyOrderService myOrderService;
	@RpcReference(microserviceName = "trade-service", schemaId = "TradeApi")
	private TradeService tradeService;
	@RpcReference(microserviceName = "goods-service", schemaId = "goodsTrade")
	private GoodsTradeService goodsTradeService;


	@Autowired
	private TUserAppletShareMapper userAppletShareMapper;

	private static String buildRequestBody(String messageContent, String userNumber, String serialNumber) {
		if (null == messageContent || null == userNumber || null == serialNumber) {
			return null;
		}
		JSONObject parm = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		map.put("SpCode", spCode);
		map.put("LoginName", loginName);
		map.put("Password", password);
		map.put("MessageContent", messageContent);
		map.put("UserNumber", userNumber);
		map.put("SerialNumber", serialNumber);
		map.put("ScheduleTime", time);
		map.put("f", f);
		StringBuilder sb = new StringBuilder();
		String temp = "";
		for (String s : map.keySet()) {
			try {
				temp = URLEncoder.encode(map.get(s), "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			sb.append(s).append("=").append(temp).append("&");
		}

		return sb.deleteCharAt(sb.length() - 1).toString();
	}

	/**
	 * 将url参数转换成map
	 *
	 * @param param aa=11&bb=22&cc=33
	 * @return
	 */
	public static Map<String, Object> getUrlParams(String param) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		if (StringUtils.isEmpty(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}

	@Override
	public UserOrganizationRequest getOrganizationInfo(String orgSeq) {
		UserOrganization userOrganization = userOrganizationMapper.selectById(orgSeq);
		UserOrganizationRequest userOrganizationRequest = new UserOrganizationRequest();
		if (userOrganization == null) {
			return userOrganizationRequest;
		}
		if (userOrganization != null) {
			BeanUtils.copyProperties(userOrganization, userOrganizationRequest);
		}
		return userOrganizationRequest;
	}

	@Override
	public UserStoreInfoDTO getStoreInfo(String orgSeq) {
		UserStoreInfo userStore = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("org_seq", orgSeq).eq("status", 1));
		UserStoreInfoDTO userstoreinfodto = new UserStoreInfoDTO();
		if (userStore != null) {
			BeanUtils.copyProperties(userStore, userstoreinfodto);
		}
		return userstoreinfodto;
	}

	@Override
	public List<UserStoreInfoDTO> getStoreInfoInIDLevel(ForeignInStoreRequest foreignInStoreRequest) {
		List<UserStoreInfoDTO> list = new ArrayList<UserStoreInfoDTO>();
		List<Object> listseqs = new ArrayList<Object>();
		List<Object> Levels = new ArrayList<Object>();
		if(foreignInStoreRequest.getIds()!=null && foreignInStoreRequest.getIds().length>0) {
			for (int i = 0; i < foreignInStoreRequest.getIds().length; i++) {
				listseqs.add(foreignInStoreRequest.getIds()[i]);
			}
		}
		if(foreignInStoreRequest.getLevels()!=null && foreignInStoreRequest.getLevels().length>0) {
			for (int i = 0; i < foreignInStoreRequest.getLevels().length; i++) {
				Levels.add(foreignInStoreRequest.getLevels()[i]);
			}
		}
		// in 方式传递参数in("org_seq",1,2,3,4,5)
		QueryWrapper<UserStoreInfo> queryWrapper = new QueryWrapper<UserStoreInfo>().in("org_seq",listseqs).in("gradelevel_id", Levels).eq("status", 1);
		List<UserStoreInfo> listMap = storeMapper.selectList(queryWrapper);
		if(listMap!=null) {
			for (int i = 0; i < listMap.size(); i++) {
				UserStoreInfoDTO userStoreInfoDTO = new UserStoreInfoDTO();
				BeanUtils.copyProperties(listMap.get(i), userStoreInfoDTO);
				list.add(userStoreInfoDTO);
			}
		}
		return list;
	}

	@Override
	public List<OrganizationDTO> getStoreOrgInfo(String orgSeq, String orgSeqZQName, String orgSeqJDName, String MDorgSeqName, String authCode) {
		UserOrganization userOrganization = userOrganizationMapper.selectById(orgSeq);
		String orgType = userOrganization.getOrgType().toString();
		List<OrganizationDTO> list = userOrganizationMapper.getStoreOrgSeq(orgSeq, orgType, orgSeqZQName, orgSeqJDName, MDorgSeqName, authCode);
		return list;
	}

	@Override
	public UserAndStoreInfoRequest getUserAndStoreInfo(String userId,String orgSeq) {
		UserAndStoreInfoRequest userAndStoreInfos = new UserAndStoreInfoRequest();
		//查询店铺信息
		if (orgSeq != null) {
			UserStoreInfo userStoreInfo = new UserStoreInfo();
			UserStoreInfoDTO userStoreInfoDTO = new UserStoreInfoDTO();
			userStoreInfo = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("org_seq", orgSeq));
			if (userStoreInfo != null) {
				BeanUtils.copyProperties(userStoreInfo, userStoreInfoDTO);
			}
			userAndStoreInfos.setStoreInfoDTO(userStoreInfoDTO);
		}
		//查询人员详情
		if (userId != null) {
			UserInfo userInfo = new UserInfo();
			UserInfoDTO userInfoDto = new UserInfoDTO();
			userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("user_id", userId));
			if (userInfo != null) {
				BeanUtils.copyProperties(userInfo, userInfoDto);
			}
			if (orgSeq != null) {
				MemAmtAndSerCountDTO memAmtAndSerCount = memberService.getMemAmtAndSerCount(userId, orgSeq);
				//查询出会员数量和服务次数
				userInfoDto.setMemberAmount(memAmtAndSerCount.getMemberAmount());
				userInfoDto.setServiceCount(memAmtAndSerCount.getServiceCount());
				//取出会员id集合,该店员下面所有会员消费金额
//				List<String> mList = memAmtAndSerCount.getMemberIdList();
				BigDecimal totalMoney = BigDecimal.ZERO;
//				for (String memberId : mList) {
				//取数据中心
//					BigDecimal money = dataService.queryMemberAmount(memberId, orgSeq).getResult();
				//通过会员id 查出消费的总金额  实时交易中心
//					ResponseResult<MemberOrderAmountDTO> result1 = myOrderService.queryOrderAmount("", orgSeq);
//					BigDecimal money = result1.getResult().getAmount();
//					totalMoney = totalMoney.add(money);

				//通过会员id 查出消费的总金额  +当天实时交易中心
				BigDecimal totalYearmoney = dataService.queryMemberAmountByUserId("", orgSeq, userId).getResult();
				totalMoney = totalMoney.add(totalYearmoney);
				BigDecimal todayMoney = tradeService.selectMoneyByMemAndOrg(userId, orgSeq, "");
					totalMoney= totalMoney.add(todayMoney);

//				}
				userInfoDto.setTotalMoney(totalMoney);
			}
			userAndStoreInfos.setUserInfoDTO(userInfoDto);
		}
		return userAndStoreInfos;
	}

	@Override
	public String getIdByToday() {
//		String id =new IdGeneratorFactoryUtil().generateOrderId("user");
		return "";
	}

	@Override
	public ResponseBase getUmsDto(UmsParameter umsParameter) {
		ResponseBase responseBase = new ResponseBase();
		String genCode = randomCode();
		Boolean type = false;
		try {
			if (null != umsParameter.getMsgType() && umsParameter.getMsgType() == 1) {
				// 验证手机号
				UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel", umsParameter.getPhone()).eq("status", 1));
				if (userInfo != null) {
					String userId = userInfo.getUserId();
					List<OrgList> orgLists = loginMapper.findOrgByUserId(userId);
					List<OrgList> orgResLists = orgLists.stream().filter(
							orgList -> orgList.getUserType() == 4
									|| orgList.getUserType() == 5
									|| orgList.getUserType() == 6)
							.collect(Collectors.toList());
					if (CollectionUtils.isEmpty(orgResLists)) {
						responseBase.setCode(0).setMessage("账号不存在!");
						return responseBase;
					}
				} else {
					responseBase.setCode(0).setMessage("账号不存在或已禁用");
					return responseBase;
				}
				//生成随机验证码
				umsParameter.setParmA(genCode);
				type = true;
			}

			UserUmsTemplate ums = new UserUmsTemplate();
			ums = userUmsTemplateMapper.selectOne(new QueryWrapper<UserUmsTemplate>().eq("umsCode", umsParameter.getCode()));
			umsParameter.setMessageContent(ums.getMessageContent());
			umsParameter.setSpCode(ums.getSpCode());
			umsParameter.setPassWord(ums.getPassWord());
			umsParameter.setLoginName(ums.getLoginName());


			UserUmsReponse userUmsReponse = senMessage(umsParameter);
			if (userUmsReponse.getResultStatus() == "0" || "0".equals(userUmsReponse.getResultStatus())) {
				responseBase.setCode(1);
				responseBase.setMessage("短信发送成功");
			} else {
				responseBase.setCode(0);
				responseBase.setMessage(userUmsReponse.getDescription());
			}
			//无论是否发送成功，都需要保存在表中
			String uuid = UUID.randomUUID().toString();
			userUmsReponse.setUuid(uuid);
			UserUms userUms = new UserUms();
			if (null != umsParameter.getMsgType() && umsParameter.getMsgType() == 1) {
				userUms.setVerificationCode(genCode);
			}
			userUms.setCreateTime(LocalDateTime.now());
			if (userUmsReponse != null) {
				BeanUtils.copyProperties(userUmsReponse, userUms);
				userUmsMapper.insert(userUms);
				if (type) {
					//插入验证码
					UserAuthCodeReq userAuthCodeReq = new UserAuthCodeReq();
					userAuthCodeReq.setCode(genCode);
					userAuthCodeReq.setPhone(umsParameter.getPhone());
					userAuthCodeReq.setSendTime(LocalDateTime.now());
					authMaService.insert(userAuthCodeReq);
				}
			}
		} catch (Exception e) {
			log.error("短信发送失败:{}", e.getMessage());
			e.printStackTrace();
			responseBase.setCode(0);
			responseBase.setMessage("短信发送失败");
		}
		return responseBase;
	}

	@Override
	public UserAndStoreInfoRequest getUserAndStoreByAuthCodeTel(String authCode, String phone) {
		UserAndStoreInfoRequest userAndStoreInfos = new UserAndStoreInfoRequest();
		//查询店铺信息
		if (authCode != null) {
			UserStoreInfo userStoreInfo = new UserStoreInfo();
			UserStoreInfoDTO userStoreInfoDTO = new UserStoreInfoDTO();
			userStoreInfo = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("auth_code",authCode).groupBy("auth_code"));
			BeanUtils.copyProperties(userStoreInfo,userStoreInfoDTO);
			userAndStoreInfos.setStoreInfoDTO(userStoreInfoDTO);
		}
		//查询人员详情
		if (phone != null) {
			UserInfo userInfo = new UserInfo();
			UserInfoDTO userInfoDto = new UserInfoDTO();
			userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel",phone).groupBy("tel"));
			BeanUtils.copyProperties(userInfo,userInfoDto);
			userAndStoreInfos.setUserInfoDTO(userInfoDto);
		}
		return userAndStoreInfos;
	}

	@Override
	public List<OrganizationDTO> getStoreOrgInfoByList(List<String> orgSeqList) {
		List<OrganizationDTO> organizationDTOS = new ArrayList<OrganizationDTO>();
		if (orgSeqList != null) {
			organizationDTOS = userOrganizationMapper.getStoreOrgInfoByList(orgSeqList);
		}
		return organizationDTOS;
	}

	/**
	 * 数组转话未in语句
	 */
	public StringBuffer getInSql(String[] str) {
		StringBuffer idsStr = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			if (i > 0) {
				idsStr.append(",");
			}
			idsStr.append("'").append(str[i]).append("'");
		}
		return idsStr;
	}

	public UserUmsReponse senMessage(UmsParameter umsParameter) throws Exception {
		UserUmsReponse userUmsReponse = new UserUmsReponse();
		//短信发送 https接口
		String url = "https://api.ums86.com:9600/sms/Api/Send.do";
		//返回格式
		//result=&description=错误描述&faillist=失败号码列表
		//短信内容,最大402个字或字符（短信内容要求的编码为gb2312或gbk），短信发送必须按照短信模板，否则就会报模板不符

		Map<String, Object> map = new HashMap<>();
		map.put("A", umsParameter.getParmA());
		if (umsParameter.getParmB() != null) {
			map.put("B", umsParameter.getParmB());
		}
		if (umsParameter.getParmC() != null) {
			map.put("C", umsParameter.getParmC());
		}

		spCode = umsParameter.getSpCode();
		loginName = umsParameter.getLoginName();
		password = umsParameter.getPassWord();
		userUmsReponse.setCode(umsParameter.getCode());
		//模板id
		String code = umsParameter.getCode();
		messageContent = umsParameter.getMessageContent();
		messageContent = new PlaceHolderReplaceUtils().replaceWithMap(messageContent, map);
		//手机号码 手机号码(多个号码用”,”分隔)，最多1000个号码
		if (umsParameter.getPhone() != null) {
			phone = umsParameter.getPhone();
		}
		//流水号，20位数字，唯一 （规则自定义,建议时间格式精确到毫秒）必填参数，与回执接口中的流水号一一对应，不传后面回执接口无法查询数据
		SnowFlakeUtil snk = new SnowFlakeUtil();
		long id = snk.nextId();
		String ranid = String.format("%02d", new Random().nextInt(99));
		String serialNumber = id + ranid;
		System.out.println(serialNumber);
		System.out.println(messageContent);
		String body = buildRequestBody(messageContent, phone, serialNumber);
		if (null == body || body.isEmpty()) {
			System.out.println("body is null.");
			return userUmsReponse;
		}
		Writer out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();

		HttpsURLConnection connection = null;
		InputStream is = null;

		try {
			URL realUrl = new URL(url);
			connection = (HttpsURLConnection) realUrl.openConnection();

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(true);
			//请求方法
			connection.setRequestMethod("POST");
			//请求Headers参数
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.connect();
			out = new OutputStreamWriter(connection.getOutputStream());
			out.write(body); //发送请求Body参数
			out.flush();
			out.close();
			int status = connection.getResponseCode();
			if (200 == status) { //200
				is = connection.getInputStream();
			} else { //400/401
				is = connection.getErrorStream();
			}
			in = new BufferedReader(new InputStreamReader(is, "GBK"));
			String line = "";
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			Map<String,Object> mapUrl = getUrlParams(result.toString()); //打印响应消息实体
			String result1 = mapUrl.get("result").toString();
			String description =  mapUrl.get("description").toString();
			String faillist =  mapUrl.get("faillist")+"";
			String taskid =  mapUrl.get("taskid")+"";
			userUmsReponse.setResultStatus(result1);
			userUmsReponse.setDescription(description);
			userUmsReponse.setFaillist(faillist);
			userUmsReponse.setTaskid(taskid);
			userUmsReponse.setF(Integer.parseInt(f));
			userUmsReponse.setSerialNumber(serialNumber);
			userUmsReponse.setCheduleTime(time);
			userUmsReponse.setPhone(phone);
			userUmsReponse.setMessageContent(messageContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != is) {
					is.close();
				}
				if (null != in) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			}

		return userUmsReponse;
	}

	@Override
	@Transactional
	public ResponseBase readExcel() {
		ResponseBase res = new ResponseBase();
		List<List<String>> list = ReadExcel.readExcel("C:\\Users\\12243\\Desktop\\001.xlsx");
//		[
//		[新青藏营销战区, 新疆基地, 阿克苏地区, 1832, 海富花园店, 新疆, 阿克苏, 80.268650, 41.179410],
//		[新青藏营销战区, 新疆基地, 阿克苏地区, 2120, 新天南门店, 新疆, 阿克苏, 83.013591, 41.719291],
//		[甘宁蒙西营销战区, 蒙西基地, 阿拉善盟, 1348, 锡林南路店, 内蒙, 阿拉善, 105.692474, 38.844791]
//		]

		for (int i = 0; i < list.size(); i++) {
			List<String> listAll = list.get(i);
			System.out.println(listAll);

			return res;
//			//战区orgseq
//			String name = listAll.get(0);
//			String lastName = "1";
//			if (i != 0) {
//				lastName = list.get(i - 1).get(0);
//			}
//			String uuidZq = UUID.randomUUID().toString();
//			//插入战区数据，相同名称只插入一次
//			UserOrganizationYdt userOrganizationYdtZQ = new UserOrganizationYdt();
//			userOrganizationYdtZQ.setOrgSeq(uuidZq);
//			userOrganizationYdtZQ.setOrgName(name);
//			//战区
//			userOrganizationYdtZQ.setLevel(2);
//			userOrganizationYdtZQ.setUpOrgSeq("1");
//			userOrganizationYdtZQ.setStatus(1);
//			userOrganizationYdtZQ.setCreateUser(JwtTokenUtil.currUser());
//			userOrganizationYdtZQ.setCreateTime(LocalDateTime.now());
//			userOrganizationYdtZQ.setUpdateUser(JwtTokenUtil.currUser());
//			userOrganizationYdtZQ.setUpdateTime(LocalDateTime.now());
//			UserOrganizationYdt userOrganizationYdtZQName = userOrganizationYdtMapper.selectOne(new QueryWrapper<UserOrganizationYdt>()
//					.eq("org_name", name));
//			if (userOrganizationYdtZQName != null && userOrganizationYdtZQName.getOrgSeq() != null) {
//				uuidZq = userOrganizationYdtZQName.getOrgSeq();
//			} else {
//				userOrganizationYdtMapper.insert(userOrganizationYdtZQ);
//			}
//			//插入基地
//			String nameJD = listAll.get(1);
//			String lastNameJD = "1";
//			if (i != 0) {
//				lastNameJD = list.get(i - 1).get(1);
//			}
//			String uuidJD = UUID.randomUUID().toString();
//			UserOrganizationYdt userOrganizationYdtJD = new UserOrganizationYdt();
//			userOrganizationYdtJD.setOrgSeq(uuidJD);
//			userOrganizationYdtJD.setOrgName(nameJD);
//			//
//			userOrganizationYdtJD.setLevel(3);
//			userOrganizationYdtJD.setUpOrgSeq(uuidZq);
//			userOrganizationYdtJD.setStatus(1);
//			userOrganizationYdtJD.setCreateUser(JwtTokenUtil.currUser());
//			userOrganizationYdtJD.setCreateTime(LocalDateTime.now());
//			userOrganizationYdtJD.setUpdateUser(JwtTokenUtil.currUser());
//			userOrganizationYdtJD.setUpdateTime(LocalDateTime.now());
//			UserOrganizationYdt userOrganizationYdtJDName = userOrganizationYdtMapper.selectOne(new QueryWrapper<UserOrganizationYdt>()
//					.eq("org_name", nameJD));
//			if (userOrganizationYdtJDName != null && userOrganizationYdtJDName.getOrgSeq() != null) {
//				uuidJD = userOrganizationYdtJDName.getOrgSeq();
//			} else {
//				userOrganizationYdtMapper.insert(userOrganizationYdtJD);
//			}
//			//插入门店
//
//			String nameMD = listAll.get(4);
//			//插入战区数据，相同名称只插入一次
//			if (nameMD != null && nameMD != "null" && nameMD != "" && !"null".equals(nameMD)) {
//				System.out.println("nameMD>>>>>>>>>>" + nameMD);
//				UserOrganizationYdt userOrganizationYdtMD = new UserOrganizationYdt();
//				userOrganizationYdtMD.setOrgSeq(listAll.get(3));
//				userOrganizationYdtMD.setOrgName(nameMD);
//				//战区
//				userOrganizationYdtMD.setLevel(4);
//				userOrganizationYdtMD.setUpOrgSeq(uuidJD);
//				userOrganizationYdtMD.setStatus(1);
//				userOrganizationYdtMD.setCreateUser(JwtTokenUtil.currUser());
//				userOrganizationYdtMD.setCreateTime(LocalDateTime.now());
//				userOrganizationYdtMD.setUpdateUser(JwtTokenUtil.currUser());
//				userOrganizationYdtMD.setUpdateTime(LocalDateTime.now());
//				userOrganizationYdtMapper.insert(userOrganizationYdtMD);
//			}
		}
		return res;
	}

	@Override
	public Map<String, Object> getAccessToken(String appID, String appScret) {
		Map<String, Object> map = new HashMap<>();
		// 访问微信服务器
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret="
				+ appScret;
		String access_token = null;
		String expires_in = null;
		try {
			URL getUrl = new URL(url);
			HttpURLConnection http = (HttpURLConnection) getUrl.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] b = new byte[size];
			is.read(b);
			String message = new String(b, "UTF-8");
			net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(message);
			String errcode = json.get("errcode") == null ? "0" : json.get("errcode").toString();
			String errmsg = json.get("errmsg") == null ? "0" : json.get("errmsg").toString();
			if ("0".equals(errcode)) {
				access_token = json.getString("access_token");
				expires_in = json.getString("expires_in");
				map.put("access_token", access_token);
				map.put("expires_in", expires_in);
			} else {
				map.put("errcode", errcode);
				map.put("errmsg", errmsg);
				return map;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.error("getAccessToken接口报错》》》》》》》》获取微信【access_token】报错", e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("getAccessToken接口报错》》》》》》》》获取微信【access_token】报错", e);
		}
		return map;
	}

	@Override
	public ResponseResult getAppletShare(String userId, String orgSeq, String phones, Integer type) {
		ResponseResult responseResult = new ResponseResult();
		TUserAppletShare userAppletShare = new TUserAppletShare();
		String shareAppletPic = "";
		userAppletShare = userAppletShareMapper.selectOne(
				new QueryWrapper<TUserAppletShare>().eq("type", type)
						.eq("user_id", userId)
						.eq("org_seq", orgSeq).eq("spare_one", phones).groupBy("user_id"));
		if (userAppletShare == null) {
			responseResult.setResult("失败").setCode(0);
			responseResult.setResult(shareAppletPic);
			return responseResult;
		}
		shareAppletPic = userAppletShare.getShareAppletPic() == null ? "0" : userAppletShare.getShareAppletPic().toString();
		if (shareAppletPic == null || "0".equals(shareAppletPic)) {
			responseResult.setResult("失败").setCode(0);
		} else {
			responseResult.setResult("成功").setCode(1);
		}
		responseResult.setResult(shareAppletPic);
		return responseResult;
	}

	@Override
	public ResponseBase insertByAppletShare(String userId, String orgSeq, String url, String phone, Integer type) {
		ResponseBase responseBase = new ResponseBase();
		try {
			TUserAppletShare userAppletShare = new TUserAppletShare();
			userAppletShare.setUuid(UUID.randomUUID().toString())
					.setCreateTime(LocalDateTime.now())
					.setOrgSeq(orgSeq)
					.setUserId(userId)
					.setShareAppletPic(url)
					.setType(type)
					.setSpareOne(phone);
			userAppletShareMapper.insert(userAppletShare);
		} catch (Exception e) {
			log.error("insertByAppletShare>>>>>>>>>>>报错，无法正常插入", e);
			responseBase.setCode(0);
			responseBase.setMessage("失败");
			return responseBase;
		}
		responseBase.setCode(1);
		responseBase.setMessage("成功");
		return responseBase;
	}

	@Override
	public List<UserOrgRelationDTO> getAllOrgReletion() {
		List<UserOrgRelationDTO> userOrgRelationDTOS = new ArrayList<>();
		UserOrgRelation userOrgRelation = new UserOrgRelation();
		List<UserOrgRelation> userOrgRelations = userOrgRelationMapper.selectList(new QueryWrapper<UserOrgRelation>().in("user_type", 4, 5, 6).eq("status", 1));
		if (userOrgRelations != null && userOrgRelations.size() > 0) {
			for (int i = 0; i < userOrgRelations.size(); i++) {
				if (userOrgRelations.get(i) != null) {
					UserOrgRelationDTO userStoreInfo = new UserOrgRelationDTO();
					BeanUtils.copyProperties(userOrgRelations.get(i), userStoreInfo);
					UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("user_id", userStoreInfo.getUserId()));
					UserStoreInfo userStoreInfo1 = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("org_seq", userStoreInfo.getOrgSeq()));
					//转化一下 然后拼接
					if (userInfo != null && userStoreInfo1 != null) {
						String str = userInfo.getTel() + '-' + userStoreInfo1.getAuthCode();
						userStoreInfo.setUuid(str);
						userOrgRelationDTOS.add(userStoreInfo);
					}
				}
			}
		}
		return userOrgRelationDTOS;
	}

	@Override
	public List<MemberMemberInfoDTO> getAllMemberMemberList(List<MemberMemberInfoDTO> lists) {
//		List<MemberMemberInfoDTO> list = new ArrayList<>();
		for (int i = 0; i < lists.size(); i++) {
			if (ToolUtil.notEmpty(lists.get(i).getOrgSeq())) {
				UserOrganizationRequest userOrganizationRequest = getOrganizationInfo(lists.get(i).getOrgSeq());
				UserStoreInfoDTO userStoreInfoDTO = getStoreInfo(lists.get(i).getOrgSeq());
				if (ToolUtil.notEmpty(userOrganizationRequest.getOrgName())) {
					lists.get(i).setMDorgSeqName(userOrganizationRequest.getOrgName());
					lists.get(i).setJDName(userStoreInfoDTO.getBaseName());
					lists.get(i).setUpOrgZone(userStoreInfoDTO.getUpOrgZone());
				}
				if (ToolUtil.notEmpty(userStoreInfoDTO)) {
					lists.get(i).setAuthCode(userStoreInfoDTO.getAuthCode());
				}
//				lists.add(lists.get(i));
			}
		}
		return lists;
	}

	@Override
	public UserAndStoreInfoRequest getGoodsAndStoreByAuthCodeTel(String authCode, String goodSeq, String phone) {
		UserAndStoreInfoRequest userAndStoreInfos = new UserAndStoreInfoRequest();
		//查询店铺信息
		if (authCode != null) {
			UserStoreInfo userStoreInfo = new UserStoreInfo();
			UserStoreInfoDTO userStoreInfoDTO = new UserStoreInfoDTO();
			userStoreInfo = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("auth_code", authCode).groupBy("auth_code"));
			BeanUtils.copyProperties(userStoreInfo, userStoreInfoDTO);
			userAndStoreInfos.setStoreInfoDTO(userStoreInfoDTO);
		}
		//查询人员详情
		if (goodSeq != null) {
			GoodsArrayParamDTO pram = new GoodsArrayParamDTO();
			pram.setGoodsSeq(goodSeq);
			List<GoodsInfoDetailDTO> goods = goodsTradeService.queryGoodsListByParam(pram);
			userAndStoreInfos.setGoodsList(goods);
		}
		//查询人员详情
		if (phone != null) {
			UserInfo userInfo = new UserInfo();
			UserInfoDTO userInfoDto = new UserInfoDTO();
			userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("tel", phone).groupBy("tel"));
			BeanUtils.copyProperties(userInfo, userInfoDto);
			userAndStoreInfos.setUserInfoDTO(userInfoDto);
		}
		return userAndStoreInfos;
	}

	@Override
	public TUserAppletContentDTO getAppletById(Integer uuid) {
		TUserAppletContentDTO appletContentDTO = new TUserAppletContentDTO();
		TUserAppletContent appletContent = appletContentMapper.selectById(uuid);
		BeanUtils.copyProperties(appletContent, appletContentDTO);
		return appletContentDTO;
	}

	@Override
	public Integer insertAppletById(String content) {
		TUserAppletContent appletContent = new TUserAppletContent();
		Integer id = appletContentMapper.insert(appletContent.setContent(content).setCreateTime(LocalDateTime.now()));
		return appletContent.getUuid();
	}

	@Override
	public ResponseResultPage<UserUmsReponse> getUmsList(PageData<UserUmsReponse> pram) {
		ResponseResultPage<UserUmsReponse> result = new ResponseResultPage<UserUmsReponse>();
		Page<UserUmsReponse> page = new Page(pram.getCurrent(), pram.getSize());
		Page<UserUmsReponse> resultPage = userUmsMapper.getUmsList(page, pram.getCondition());
		result.setCode(1);
		result.setMessage("信息查询成功，共有" + resultPage.getRecords().size() + "条数据");
		result.setSize(pram.getSize());
		result.setCurrent(pram.getCurrent());
		result.setTotal(resultPage.getTotal());
		result.setRecords(resultPage.getRecords());
		return result;
	}
}

