package com.icss.newretail.api.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.icss.newretail.model.*;
import com.icss.newretail.service.user.ReviewService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jc
 * @date 2020/3/23 17:51
 */
@RestSchema(schemaId = "review")
@RequestMapping(path = "/v1/review")
public class ReviewApi {

	@Autowired
	private ReviewService reviewService;

	/**
	 * 店铺验证
	 *
	 * @param authCode
	 * @return
	 */
	@PostMapping("shopAuth")
	public ResponseResult<LoginUserDTO> shopAuth(@RequestParam String authCode) {
		return reviewService.shopAuth(authCode);
	}


	/**
	 * 商户店铺申请
	 *
	 * @param userReviewInfoDTO
	 * @return
	 */
	@PostMapping("applyShop")
	public ResponseBase applyShop(@RequestBody UserReviewInfoDTO userReviewInfoDTO) {
		return reviewService.applyShop(userReviewInfoDTO);
	}

	/**
	 * 查询商户店铺申请信息
	 *
	 * @return
	 */
	@PostMapping("queryUserReviewInfo")
	public ResponseResultPage<UserReviewInfoDTO> queryUserReviewInfo(@RequestBody PageData<UserReviewRequest> pageData) {
		return reviewService.queryUserReviewInfo(pageData);
	}



	/**
	 *查询店铺申请详情
	 */
	@GetMapping("queryReviewInfo")
	public ResponseResult queryReviewInfo(@RequestParam String uuid) {
		return reviewService.queryReviewInfo(uuid);
	}



	/**
	 * 初审
	 * @param examination
	 * @return
	 */
	@PostMapping("preliminaryExamination")
	public ResponseBase preliminaryExamination(@RequestBody PreliminaryExamination examination) {
		return reviewService.preliminaryExamination(examination);
	}

	/**
	 * 复审
	 * @param examination
	 * @return
	 */
	@PostMapping("finalReview")
	public ResponseBase finalReview(@RequestBody PreliminaryExamination examination) {
		return reviewService.finalReview(examination);
	}
	
	/**
	 * 复审巡店
	 * @param examination
	 * @return
	 */
	@PostMapping("finalReview2")
	public ResponseBase finalReview2(@RequestBody PreliminaryExamination examination) {
		return reviewService.finalReview2(examination);
	}
	/**
	 * 系统管理员新增编辑门店
	 * @param userStoreInfoDTO
	 * @return
	 */
	@PostMapping("addStore")
	public ResponseBase addStore(@RequestBody UserStoreInfoDTO userStoreInfoDTO) {
		return reviewService.addStore(userStoreInfoDTO);
	}

	/**
	 * 查询店铺信息详情
	 */
	@GetMapping("queryUserStoreInfo")
	public ResponseResult<UserStoreInfoDTO>  queryUserStoreInfo(@RequestParam String uuid) {
		return reviewService.queryUserStoreInfo(uuid);
	}


	/**
	 * 云店项目用到接口 ===>>>店员管理（增加，编辑店员）
	 * 说明 ===>>>由于云店这边一家门店,pc端只有授权码登录 ,统一用一个账号登录
	 *
	 * @param userclerk
	 * @return
	 */
	@PostMapping("editClerk")
	public ResponseBase editClerk(@RequestBody UserClerkDTO userclerk) {
		return reviewService.editClerk(userclerk);
	}

	/**
	 * 删除店员
	 */
	@PostMapping("deleteClerk")
	public ResponseBase deleteClerk(@RequestParam String uuid) {
		return reviewService.deleteClerk(uuid);
	}

	/**
	 * 巡店项目接口 ===>>>店员管理（增加，编辑店员）
	 *
	 * @param userclerk
	 * @return
	 */
	@PostMapping("editStoreClerk")
	public ResponseBase editStoreClerk(@RequestBody UserClerkDTO userclerk) {
		return reviewService.editStoreClerk(userclerk);
	}
	/**
	 * 店员信息查询
	 * @param userclerk
	 * @return
	 */
	@PostMapping("qryClerk")
	public  ResponseResultPage<UserInfoDTO> qryClerk(@RequestBody  PageData<UserClerkRequest> userclerk) {
		return reviewService.qryClerk(userclerk);
	}

	/**
	 * 店员详情信息查询
	 * @param uuid
	 * @return
	 */
	@GetMapping("qryClerkUserInfo")
	public ResponseResult<UserInfoDTO> qryClerkUserInfo(@RequestParam String uuid) {
		return reviewService.qryClerkUserInfo(uuid);
	}


	/**
	 * 店铺信息查询
	 * @param userStoreInfo
	 * @return
	 */
	@PostMapping("qryStore")
	public  ResponseResultPage<UserQryStoreDTO> qryStore(@RequestBody  PageData<UserStoreRequest> userStoreInfo) {
		return reviewService.qryStore(userStoreInfo);
	}

	/**
	 * 店铺强关开
	 *
	 * @param uuid       门店id
	 * @param openStatus 开店-1， 闭店-0
	 * @return
	 */
	@PostMapping("storeAdministration")
	public ResponseBase storeAdministration(@RequestParam String uuid, @RequestParam Integer openStatus) {
		return reviewService.storeAdministration(uuid, openStatus);
	}

	/**
	 * 获取所有战区信息
	 */
	@GetMapping("getAllZone")
	public Map<String, Object> getAllZone() {
		return reviewService.getAllZone();
	}

	/**
	 * 获取所有战区信息
	 */
	@GetMapping("getAllZoneList")
	public ResponseRecords getAllZoneList() {
		return reviewService.getAllZoneList();
	}
	/**
	 * 获取所有基地信息
	 */
	@GetMapping("getAllBase")
	public  Map<String,Object> getAllBase(@RequestParam String code) {
		return reviewService.getAllBase(code);
	}

	/**
	 * 获取所有门店信息
	 */
	@GetMapping("getAllShop")
	public  Map<String,Object> getAllShop(@RequestParam String code) {
		return reviewService.getAllShop(code);
	}

	/**
	 * 获取基地业务员信息
	 */
	@GetMapping("getBaseUserInfo")
	public  Map<String,Object> getBaseUserInfo(@RequestParam String  orgSeq) {
		return reviewService.getBaseUserInfo(orgSeq);
	}

	/**
	 * 店铺申请信息查询接口（用于申请用户查看申请状态）
	 */
	@GetMapping("getStoreByAuthCode")
	public  ResponseResult<UserReviewInfoDTO> getStoreByAuthCode(@RequestParam String  authCode) {
		return reviewService.getStoreByAuthCode(authCode);
	}

	/**
	 * 店铺编辑申请
	 */
	@PostMapping("editStore")
	public  ResponseBase editStore(@RequestBody UserReviewInfoDTO userReviewInfoDTO) {
		return reviewService.editStore(userReviewInfoDTO);
	}

	/**
	 * 店铺初审
	 */
	@PostMapping("shopPreliminary")
	public  ResponseBase shopPreliminary(@RequestBody PreliminaryExamination examination) {
		return reviewService.shopPreliminary(examination);
	}



	/**
	 * 店铺复审
	 */
	@PostMapping("storeFinalReview")
	public  ResponseBase storeFinalReview(@RequestBody PreliminaryExamination examination) {
		return reviewService.storeFinalReview(examination);
	}

	/**
	 * 店主，店长号码认证(仅仅用于验证店长店主)
	 */
	@PostMapping("phoneVerification")
	public  ResponseBase phoneVerification(@RequestParam String phone,@RequestParam Integer type) {
		return reviewService.phoneVerification(phone,type);
	}


	/**
	 * 店铺申请信息查询接口（用于申请用户查看申请状态）
	 */
	@GetMapping("getReviewId")
	public  ResponseResult<UserReviewInfoDTO> getReviewId(@RequestParam String  reviewId) {
		return reviewService.getReviewId(reviewId);
	}

	/**
	 * 查询门店店员信息
	 *
	 * @param pageData
	 * @return
	 */
	@PostMapping("queryStoreUserInfo")
	public ResponseResultPage<StoreUserInfoDTO> queryStoreUserInfo(@RequestBody PageData<StoreUserInfoDTO> pageData) {
		return reviewService.queryStoreUserInfo(pageData);
	}


	/**
	 * 查询工行建档模板
	 *
	 * @param
	 * @return
	 */
	@GetMapping("queryUserIcbcTemp")
	public ResponseResult<List<UserIcbcTempDTO>> queryUserIcbcTemp(@RequestParam Integer type) {
		return reviewService.queryUserIcbcTemp(type);
	}

	/**
	 * 保存店铺申请信息
	 *
	 * @param
	 * @return
	 */
	@PostMapping("saveUserReviewInfo")
	public ResponseBase saveUserReviewInfo(@RequestBody UserReviewInfoDTO userReviewInfoDTO) {
		return reviewService.saveUserReviewInfo(userReviewInfoDTO);
	}

	/**
	 * 工行模板维护
	 */
	@PostMapping("edidUserIcbcTemp")
	public ResponseBase edidUserIcbcTemp(@RequestBody UserIcbcTempDTO UserIcbcTempDTO) {
		return reviewService.edidUserIcbcTemp(UserIcbcTempDTO);
	}

	/**
	 * 银行审核接口（待定）
	 *
	 * @param icbcAnswerRequest
	 * @return
	 */
	@PostMapping("jumpUserReviewInfo")
	public ResponseBase jumpUserReviewInfo(@RequestBody IcbcAnswerRequest icbcAnswerRequest) {
		return reviewService.jumpUserReviewInfo(icbcAnswerRequest);
	}

	/**
	 * 解锁支付信息
	 *
	 * @param uuid
	 * @return
	 */
	@PostMapping("paymentInformation")
	public ResponseBase paymentInformation(@RequestParam String uuid, @RequestParam Integer isSwith) {
		return reviewService.paymentInformation(uuid, isSwith);
	}


	/**
	 * 增加店铺申请编码
	 *
	 * @return
	 */
	@PostMapping("addAuhCode")
	public ResponseBase addAuhCode(@RequestBody AddAuthCode addAuthCode) {
		return reviewService.addAuhCode(addAuthCode);
	}

	/**
	 * 新增账号
	 *
	 * @param dto
	 * @return
	 */
	@PostMapping("addAccount")
	public ResponseBase addAccount(@RequestBody StoreAccountDTO dto) {
		return reviewService.addAccount(dto);
	}

	/**
	 * 查询登录店铺授权码信息
	 *
	 * @param pageData
	 * @return
	 */
	@PostMapping("getAllAuthCode")
	public ResponseResultPage<UserStoreVerifyDTO> getAllAuthCode(@RequestBody PageData<AddAuthCode> pageData) {
		return reviewService.getAllAuthCode(pageData);
	}

	/**
	 * 查询登录店铺授权码信息
	 *
	 * @param uuid
	 * @return
	 */
	@PostMapping("deleteLoginAuthCode")
	public ResponseBase deleteLoginAuthCode(@RequestParam String uuid) {
		return reviewService.deleteLoginAuthCode(uuid);
	}

	/**
	 * 所有店铺信息汇总查询
	 *
	 * @param userStoreInfo
	 * @return
	 */
	@PostMapping("qryStoreAmount")
	public ResponseRecords<UserQryStoreDTO> qryStoreAmount(@RequestBody UserStoreRequest userStoreInfo) {
		return reviewService.qryStoreAmount(userStoreInfo);
	}

	/**
	 * 总部重置密码
	 */
	@GetMapping("resetPwd")
	public ResponseBase resetPwd(@RequestParam String authCode) {
		return reviewService.resetPwd(authCode);
	}


	/**
	 * 入网审核总部导出
	 *
	 * @param pageData
	 * @return
	 * @Author yebaba
	 */
	@PostMapping(path = "queryStoreUserExcel")
	@ApiResponses({
			@ApiResponse(code = 200, response = File.class, message = "")
	})
	public ResponseEntity<byte[]> queryStoreUserExcel(@RequestBody PageData<StoreUserInfoDTO> pageData) throws UnsupportedEncodingException {
		ByteArrayOutputStream byteOutPutStream = new ByteArrayOutputStream();
		String fileName = "wly-StoreUserProfile-" + System.currentTimeMillis() + ".xlsx";
		HttpHeaders headers = new HttpHeaders();
		//指定以流的形式下载文件
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		//指定文件名
		headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
		pageData.setSize(100000);
		pageData.setCurrent(1);
		ResponseResultPage<StoreUserInfoDTO> page = reviewService.queryStoreUserInfo(pageData);
		if (page.getTotal() == 0) {
			return new ResponseEntity<>(byteOutPutStream.toByteArray(), headers, HttpStatus.OK);
		}
		List<StoreUserInfoDTO> userQryStoreDTOList = page.getRecords();
		List<StoreUserInfoExcel> userStoreExcelList = new ArrayList<>();
		if (userQryStoreDTOList != null && userQryStoreDTOList.size() > 0) {
			for (int i = 0; i < userQryStoreDTOList.size(); i++) {
				StoreUserInfoExcel userStoreExcel = new StoreUserInfoExcel();
				BeanUtils.copyProperties(userQryStoreDTOList.get(i), userStoreExcel);
				//店铺类型(1-一代店,二代店、、2-三代店，3-四代店，4-体验店，5-专柜)
				String statusName = "";
				String academicName = "";
				String sexName = "";
				String userTpye = "";
				switch (userQryStoreDTOList.get(i).getStatus()) {
					case 0:
						statusName = "已禁用";
						break;
					case 1:
						statusName = "已启用";
						break;
					default:
						statusName = "已删除";
						break;
				}
//				(value = "学历(0-高中以下 1-大专 2-本科 3-硕士")
				switch (userQryStoreDTOList.get(i).getAcademic() == null ? 4 : userQryStoreDTOList.get(i).getAcademic()) {
					case 0:
						academicName = "高中以下";
						break;
					case 1:
						academicName = "大专";
						break;
					case 2:
						academicName = "本科";
						break;
					case 3:
						academicName = "硕士";
						break;
					default:
						academicName = "-";
						break;
				}
				switch (userQryStoreDTOList.get(i).getSex() == null ? 2 : userQryStoreDTOList.get(i).getSex()) {
					case 0:
						sexName = "女";
						break;
					case 1:
						sexName = "男";
						break;
					default:
						sexName = "-";
						break;
				}
//				@ApiModelProperty(value = "职位(4店主5店长6店员）")
				switch (Integer.parseInt(userQryStoreDTOList.get(i).getUserType() == null ? "7" : userQryStoreDTOList.get(i).getUserType())) {
					case 4:
						userTpye = "店主";
						break;
					case 5:
						userTpye = "店长";
						break;
					case 6:
						userTpye = "店员";
						break;
					default:
						userTpye = "-";
						break;
				}
				userStoreExcel.setStatusName(statusName);
				userStoreExcel.setAcademicName(academicName);
				userStoreExcel.setSexName(sexName);
				userStoreExcel.setUserType(userTpye);
				userStoreExcelList.add(userStoreExcel);
			}
		}
		List<StoreUserInfoExcel> rows = CollUtil.newArrayList(userStoreExcelList);
		String[] codeNames = {"realName", "sexName", "userType", "tel", "saleAmount", "memberCount",
				"storeName", "jdName", "zqName", "idCard", "authCode", "employeeNo",
				"academicName", "workYear", "createTime", "statusName"};
		String[] strName = {"名称", "性别", "职位", "电话", "销售额", "会员数", "店铺名称",
				"基地名称", "战区名称", "身份证号码", "店铺授权编码", "员工编号", "学历", "工作年限", "创建时间", "启用状态"};
		ExcelWriter writer = ExcelUtil.getBigWriter();
		// 定义单元格
		writer.setColumnWidth(-1, 25);
		for (int i = 0; i < codeNames.length; i++) {
			writer.addHeaderAlias(codeNames[i], strName[i]);
		}
		writer.renameSheet("StoreUserProfile");
		// 一次性写出内容，使用默认样式
		writer.write(rows, true);
		writer.flush(byteOutPutStream);
		writer.close();
		return new ResponseEntity<>(byteOutPutStream.toByteArray(), headers, HttpStatus.OK);
	}
}
