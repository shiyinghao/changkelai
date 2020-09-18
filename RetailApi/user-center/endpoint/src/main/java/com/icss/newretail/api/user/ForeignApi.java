package com.icss.newretail.api.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.icss.newretail.model.*;
import com.icss.newretail.service.user.ForeignService;
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
 * @author Ydt
 * @date 2020/3/23 17:51
 */
@RestSchema(schemaId = "foreign")
@RequestMapping(path = "/v1/foreign")
public class ForeignApi {

	@Autowired
	private ForeignService foreignService;
	@Autowired
	private ReviewService reviewService;

	/**
	 * 根据orgSeq查询组织信息
	 */
	@GetMapping("getOrganizationInfo")
	public UserOrganizationRequest getOrganizationInfo(@RequestParam String orgSeq) {
		return foreignService.getOrganizationInfo(orgSeq);
	}

	/**
	 * 查询门店信息
	 */
	@PostMapping("getStoreInfo")
	public  UserStoreInfoDTO getStoreInfo(@RequestParam String  orgSeq) {
		return foreignService.getStoreInfo(orgSeq);
	}
	
	/**
	 * 根据信息查询门店数据
	 */
	@PostMapping("getStoreInfoInIDLevel")
	public  List<UserStoreInfoDTO> getStoreInfoInIDLevel(@RequestBody ForeignInStoreRequest foreignInStoreRequest  ) {
		return foreignService.getStoreInfoInIDLevel(foreignInStoreRequest);
	}

	/**
	 * 通过orgSeq查询当前组织下门店组织信息
	 */
	@PostMapping("getStoreOrgInfo")
	public List<OrganizationDTO> getStoreOrgInfo(@RequestParam String orgSeq, @RequestParam(value = "deliveryType", required = false) String orgSeqZQName,
												 @RequestParam(value = "deliveryType", required = false) String orgSeqJDName,
												 @RequestParam(value = "deliveryType", required = false) String MDorgSeqName,
												 @RequestParam(value = "deliveryType", required = false) String authCode) {
		return foreignService.getStoreOrgInfo(orgSeq, orgSeqZQName, orgSeqJDName, MDorgSeqName, authCode);
	}

	/**
	 * 通过orgseq user_id查询店铺详情，人员详情
	 */
	@GetMapping("getUserAndStoreInfo")
	public UserAndStoreInfoRequest getUserAndStoreInfo(@RequestParam String userId,@RequestParam String orgSeq){
		return foreignService.getUserAndStoreInfo(userId,orgSeq);
	}

	/**
	 * id生成器
	 */
	@GetMapping("getIdByToday")
	public String getIdByToday() {
		return foreignService.getIdByToday();
	}

	/**
	 * 根据模板id查询模板内容
	 */
	@PostMapping("getUmsDto")
	public ResponseBase getUmsDto(@RequestBody UmsParameter umsParameter ){
		return foreignService.getUmsDto(umsParameter);
	}

	/**
	 * 通过AuthCode，phone 查询人员信息以及店铺信息
	 */
	@GetMapping("getUserAndStoreByAuthCodeTel")
	public UserAndStoreInfoRequest getUserAndStoreByAuthCodeTel(@RequestParam String authCode, @RequestParam String phone) {
		return foreignService.getUserAndStoreByAuthCodeTel(authCode, phone);
	}

	/**
	 * 通过AuthCode，goodsSeq 查询店铺信息商品信息
	 */
	@GetMapping("getGoodsAndStoreByAuthCodeTel")
	public UserAndStoreInfoRequest getGoodsAndStoreByAuthCodeTel(@RequestParam String authCode, @RequestParam String goodSeq, @RequestParam String phone) {
		return foreignService.getGoodsAndStoreByAuthCodeTel(authCode, goodSeq, phone);
	}

	/**
	 * 通过 id查询分享吗信息
	 */
	@GetMapping("getAppletById")
	public TUserAppletContentDTO getAppletById(@RequestParam Integer uuid) {
		return foreignService.getAppletById(uuid);
	}

	/**
	 * 插入 分享内容 返回id
	 */
	@PostMapping("insertAppletById")
	public Integer insertAppletById(@RequestParam String content) {
		return foreignService.insertAppletById(content);
	}

	/**
	 * 通过List<orgSeq>查询当前组织下门店组织信息
	 */
	@PostMapping("getStoreOrgInfoByList")
	public List<OrganizationDTO> getStoreOrgInfoByList(@RequestBody List<String> orgSeqList) {
		return foreignService.getStoreOrgInfoByList(orgSeqList);
	}

	/**
	 * excel插入门店组织关联表
	 */
	@PostMapping("readExcel")
	public ResponseBase readExcel() {
		return foreignService.readExcel();
	}

	/**
	 * 获取微信token值
	 */
	@GetMapping("getAccessToken")
	public Map<String, Object> getAccessToken(@RequestParam String appID, @RequestParam String appScret) {
		return foreignService.getAccessToken(appID, appScret);
	}

	/**
	 * 通过userid  orgseq查询小程序分享码
	 */
	@GetMapping("getAppletShare")
	public ResponseResult getAppletShare(@RequestParam String userId, @RequestParam String orgSeq, @RequestParam String phones, @RequestParam Integer type) {
		return foreignService.getAppletShare(userId, orgSeq, phones, type);
	}

	/**
	 * 通过userid  orgseq查询小程序分享码
	 */
	@PostMapping("getAppletShare")
	public ResponseBase insertByAppletShare(@RequestParam String userId, @RequestParam String orgSeq, @RequestParam String url, @RequestParam String phone, @RequestParam Integer type) {
		return foreignService.insertByAppletShare(userId, orgSeq, url, phone, type);
	}


	/**
	 * 查询所有的店员店主信息
	 */
	@PostMapping("getAllOrgReletion")
	public List<UserOrgRelationDTO> getAllOrgReletion() {
		return foreignService.getAllOrgReletion();
	}

	/**
	 * 查询所有的店员店主信息
	 */
	@PostMapping("getAllMemberMemberList")
	public List<MemberMemberInfoDTO> getAllMemberMemberList(@RequestBody List<MemberMemberInfoDTO> lists) {
		return foreignService.getAllMemberMemberList(lists);
	}

	/**
	 * 查询电话号码 短信列表
	 */
	@PostMapping("getUmsList")
	public ResponseResultPage<UserUmsReponse> getUmsList(@RequestBody PageData<UserUmsReponse> pram) {
		return foreignService.getUmsList(pram);
	}


	/**
	 * 店铺档案导出
	 *
	 * @param pageData
	 * @return
	 * @Author yebaba
	 */
	@PostMapping(path = "getStoreExcel")
	@ApiResponses({
			@ApiResponse(code = 200, response = File.class, message = "")
	})
	public ResponseEntity<byte[]> getStoreExcel(@RequestBody PageData<UserStoreRequest> pageData) throws UnsupportedEncodingException {
		ByteArrayOutputStream byteOutPutStream = new ByteArrayOutputStream();
		String fileName = "wly-StoreProfile-" + System.currentTimeMillis() + ".xlsx";
		HttpHeaders headers = new HttpHeaders();
		//指定以流的形式下载文件
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		//指定文件名
		headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
		pageData.setSize(Integer.MAX_VALUE);
		pageData.setCurrent(1);
		ResponseResultPage<UserQryStoreDTO> page = reviewService.qryStore(pageData);
		if (page.getTotal() == 0) {
			return new ResponseEntity<>(byteOutPutStream.toByteArray(), headers, HttpStatus.OK);
		}
		List<UserQryStoreDTO> userQryStoreDTOList = page.getRecords();
		List<UserStoreExcel> userStoreExcelList = new ArrayList<>();
		if (userQryStoreDTOList != null && userQryStoreDTOList.size() > 0) {
			for (int i = 0; i < userQryStoreDTOList.size(); i++) {
				UserStoreExcel userStoreExcel = new UserStoreExcel();
				BeanUtils.copyProperties(userQryStoreDTOList.get(i), userStoreExcel);
				//店铺类型(1-一代店,二代店、、2-三代店，3-四代店，4-体验店，5-专柜)
				String storeTypeName = "";
				String isIcbcName = "";
				String icbcSwitchName = "";
				String openStatusName = "";
				Integer storeType = userQryStoreDTOList.get(i).getStoreType() == null ? 0 : userQryStoreDTOList.get(i).getStoreType();
				switch (storeType) {
					case 1:
						storeTypeName = "一代店,二代店";
						break;
					case 2:
						storeTypeName = "三代店";
						break;
					case 3:
						storeTypeName = "四代店";
						break;
					case 4:
						storeTypeName = "体验店";
						break;
					case 5:
						storeTypeName = "专柜";
						break;
				}
				Integer isicbc = userQryStoreDTOList.get(i).getIsIcbc() == null ? 0 : userQryStoreDTOList.get(i).getIsIcbc();
				switch (isicbc) {
					case 1:
						isIcbcName = "是";
						break;
					case 2:
						isIcbcName = "否";
						break;
					case 0:
						isIcbcName = "否";
						break;
				}
				Integer icbcSwitch = userQryStoreDTOList.get(i).getIcbcSwitch() == null ? 0 : userQryStoreDTOList.get(i).getIcbcSwitch();
				switch (icbcSwitch) {
					case 1:
						icbcSwitchName = "已解锁";
						break;
					case 2:
						icbcSwitchName = "锁定";
						break;
					case 0:
						icbcSwitchName = "锁定";
						break;
				}
				Integer openStatus = userQryStoreDTOList.get(i).getOpenStatus() == null ? 0 : userQryStoreDTOList.get(i).getOpenStatus();
				switch (openStatus) {
					case 0:
						openStatusName = "已歇业";
						break;
					case 1:
						openStatusName = "正常营业";
						break;
				}
				userStoreExcel.setStoreTypeName(storeTypeName);
				userStoreExcel.setIsIcbcName(isIcbcName);
				userStoreExcel.setIcbcSwitchName(icbcSwitchName);
				userStoreExcel.setOpenStatusName(openStatusName);
				userStoreExcelList.add(userStoreExcel);
			}
		}
		List<UserStoreExcel> rows = CollUtil.newArrayList(userStoreExcelList);
		String[] codeNames = {"authCode", "storeName", "storeTypeName", "managerArea", "buildingArea", "practicalArea", "leaseholdArea",
				"warehouseArea", "openStatusName", "openTime", "openDate", "province", "city", "county", "companyName", "legalPerson", "legalPersonPhone",
				"storeOwnerName", "storeOwnerPhone", "storePhone", "storeAddressDetail", "storeLng", "storeLat", "createTime",
				"updateTime", "openBeginTime", "openEndTime", "merid", "agreement", "wechatId",
				"upOrgZone", "baseName", "createdDate", "isIcbcName", "icbcSwitchName", "icbcUpdateTime", "bankAccount", "bankName"};
		String[] strName = {"授权码", "店铺名称", "店铺类型", "经营面积", "建筑面积", "实用面积", "租赁面积", "仓库面积",
				"营业状态", "营业时间", "开业日期", "所属省", "所属城市", "所属区", "经销商公司", "法人", "法人手机号",
				"店主姓名", "店主手机号", "店铺电话", "店铺详细地址", "店铺经度", "店铺纬度", "店铺申请时间",
				"店铺审核时间", "营业开始时间", "营业结束时间", "工行Merid", "工行协议号", "微信识别码",
				"所属战区", "所属基地", "建档日期", "是否是工行对公账户", "支付信息状态", "档案更新时间", "银行账号", "银行名称"};
		//在桌面生产导出文件
//		FileSystemView fsv = FileSystemView.getFileSystemView();
//		String url = fsv.getHomeDirectory().getPath().toString() + "/" + fileName;
//        BigExcelWriter writer = ExcelUtil.getBigWriter(true);
		int maxRowNum = 60000;
		int sheets = rows.size() % 60000 == 0 ? (rows.size() / maxRowNum) : (rows.size() / maxRowNum + 1);
		ExcelWriter writer = ExcelUtil.getBigWriter();
		// 定义单元格
		StyleSet style = writer.getStyleSet();
		writer.setColumnWidth(-1, 20);
		for (int i = 0; i < codeNames.length; i++) {
			writer.addHeaderAlias(codeNames[i], strName[i]);
		}
//		if (sheets != 0) {
//			for (int i = 0; i < sheets; i++) {
//				//设置sheet名称
//				Integer j = i + 1;
//				writer.renameSheet("StoreProfile(" + j + ")");
//			}
//		} else {
//			writer.renameSheet("StoreProfile");
//		}
		writer.renameSheet("StoreProfile");
		// 一次性写出内容，使用默认样式
		writer.write(rows, true);
		writer.flush(byteOutPutStream);
		writer.close();
		return new ResponseEntity<>(byteOutPutStream.toByteArray(), headers, HttpStatus.OK);
	}

	/**
	 * 入网审核总部导出
	 *
	 * @param pageData
	 * @return
	 * @Author yebaba
	 */
	@PostMapping(path = "getStoreRewExcel")
	@ApiResponses({
			@ApiResponse(code = 200, response = File.class, message = "")
	})
	public ResponseEntity<byte[]> getStoreRewExcel(@RequestBody PageData<UserReviewRequest> pageData) throws UnsupportedEncodingException {
		ByteArrayOutputStream byteOutPutStream = new ByteArrayOutputStream();
		String fileName = "wly-StoreReviewProfile-" + System.currentTimeMillis() + ".xlsx";
		HttpHeaders headers = new HttpHeaders();
		//指定以流的形式下载文件
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		//指定文件名
		headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
		pageData.setSize(100000);
		pageData.setCurrent(1);
		ResponseResultPage<UserReviewInfoDTO> page = reviewService.queryUserReviewInfo(pageData);
		if (page.getTotal() == 0) {
			return new ResponseEntity<>(byteOutPutStream.toByteArray(), headers, HttpStatus.OK);
		}
		List<UserReviewInfoDTO> userQryStoreDTOList = page.getRecords();
		List<UserStoreReviewExcel> userStoreExcelList = new ArrayList<>();
		if (userQryStoreDTOList != null && userQryStoreDTOList.size() > 0) {
			for (int i = 0; i < userQryStoreDTOList.size(); i++) {
				UserStoreReviewExcel userStoreExcel = new UserStoreReviewExcel();
				BeanUtils.copyProperties(userQryStoreDTOList.get(i), userStoreExcel);
				//店铺类型(1-一代店,二代店、、2-三代店，3-四代店，4-体验店，5-专柜)
				String storeTypeName = "";
				String reviewStatusName = "";
				String reviewStatusNameNext = "";
				switch (userQryStoreDTOList.get(i).getStoreType()) {
					case 1:
						storeTypeName = "一代店,二代店";
						break;
					case 2:
						storeTypeName = "三代店";
						break;
					case 3:
						storeTypeName = "四代店";
						break;
					case 4:
						storeTypeName = "体验店";
						break;
					case 5:
						storeTypeName = "专柜";
						break;
				}
				//(1-申请已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过，6-银行审核，7-银行审核通过，8-银行审核不通过)
				Integer reviewStatus = userQryStoreDTOList.get(i).getReviewStatus() == null ? 0 : userQryStoreDTOList.get(i).getReviewStatus();
				Integer number = userQryStoreDTOList.get(i).getNumber() == null ? 0 : userQryStoreDTOList.get(i).getNumber();
				if (reviewStatus == 1) {
					reviewStatusName = "申请已提交";
					reviewStatusNameNext = "战区审核";
				}
				if (reviewStatus == 2 && number == 0) {
					reviewStatusName = "初审通过";
					reviewStatusNameNext = "银行审核中";
				}
				if (reviewStatus == 2 && number != 0) {
					reviewStatusName = "初审通过";
					reviewStatusNameNext = "战区审核";
				}
				if (reviewStatus == 3) {
					reviewStatusName = "初审未通过";
					reviewStatusNameNext = "申请人重新提交";
				}
				if (reviewStatus == 4) {
					reviewStatusName = "复审未通过";
					reviewStatusNameNext = "申请人重新提交";
				}
				if (reviewStatus == 5 && number == 0) {
					reviewStatusName = "复审通过";
					reviewStatusNameNext = "开店完成";
				}
				if (reviewStatus == 5 && number != 0) {
					reviewStatusName = "复审通过";
					reviewStatusNameNext = "店铺信息修改成功";
				}
				if (reviewStatus == 6) {
					reviewStatusName = "银行审核中";
					reviewStatusNameNext = "总部审核";
				}
				if (reviewStatus == 7) {
					reviewStatusName = "银行审核通过";
					reviewStatusNameNext = "总部审核";
				}
				if (reviewStatus == 8) {
					reviewStatusName = "银行审核未通过";
					reviewStatusNameNext = "申请人重新提交";
				}
				userStoreExcel.setStoreTypeName(storeTypeName);
				userStoreExcel.setReviewStatusName(reviewStatusName);
				userStoreExcel.setReviewStatusNameNext(reviewStatusNameNext);
				userStoreExcelList.add(userStoreExcel);
			}
		}
		List<UserStoreReviewExcel> rows = CollUtil.newArrayList(userStoreExcelList);
//		String[] codeNames = {"authCode", "storeName", "storeTypeName", "managerArea", "buildingArea", "practicalArea", "leaseholdArea",
//				"warehouseArea", "openStatusName", "openTime", "openDate", "province", "city", "companyName", "legalPerson", "legalPersonPhone",
//				"storeOwnerName", "storeOwnerPhone", "storePhone", "storeAddressDetail", "storeLng", "storeLat", "createTime",
//				"updateTime", "openBeginTime", "openEndTime", "merid", "agreement", "wechatId",
//				"upOrgZone", "baseName", "storeCode", "createdDate", "isIcbcName", "icbcSwitchName", "icbcUpdateTime", "bankAccount", "bankName"};
//		String[] strName = {"授权码", "店铺名称", "店铺类型", "经营面积", "建筑面积", "实用面积", "租赁面积", "仓库面积",
//				"营业状态", "营业时间", "开业日期", "所属省", "所属城市", "经销商公司", "法人", "法人手机号",
//				"店主姓名", "店主手机号", "店铺电话", "店铺详细地址", "店铺经度", "店铺纬度", "店铺申请时间",
//				"店铺审核时间", "营业开始时间", "营业结束时间", "工行Merid", "工行协议号", "微信识别码",
//				"所属战区", "所属基地", "店铺编码", "建档日期", "是否是工行对公账户", "支付信息状态", "档案更新时间", "银行账号", "银行名称"};
		String[] codeNames = {"storeName", "storeTypeName", "province", "city", "companyName",
				"legalPerson", "legalPersonPhone", "storePhone", "storeAddressDetail",
				"storeLng", "storeLat", "upOrgZone", "baseName", "reviewStatusName", "reviewStatusNameNext"};
		String[] strName = {"店铺名称", "店铺类型", "所属省", "所属城市", "经销商公司", "法人", "法人手机号",
				"店铺电话", "店铺详细地址", "店铺经度", "店铺纬度", "所属战区", "所属基地", "审核状态(当前)", "审核状态(下一步)"};
//		//在桌面生产导出文件
//		FileSystemView fsv = FileSystemView.getFileSystemView();
//		String url = fsv.getHomeDirectory().getPath().toString() + "/" + fileName;
//		int maxRowNum = 60000;
//		int sheets = rows.size() % 60000 == 0 ? (rows.size() / maxRowNum) : (rows.size() / maxRowNum + 1);
		ExcelWriter writer = ExcelUtil.getBigWriter();
		// 定义单元格
		writer.setColumnWidth(-1, 25);
		for (int i = 0; i < codeNames.length; i++) {
			writer.addHeaderAlias(codeNames[i], strName[i]);
		}
//		if (sheets != 0) {
//			for (int i = 0; i < sheets; i++) {
//				//设置sheet名称
//				Integer j = i + 1;
//				writer.renameSheet("StoreReviewProfile(" + j + ")");
//			}
//		} else {
//			writer.renameSheet("StoreReviewProfile");
//		}
		writer.renameSheet("StoreReviewProfile");
		// 一次性写出内容，使用默认样式
		writer.write(rows, true);
		writer.flush(byteOutPutStream);
		writer.close();
		return new ResponseEntity<>(byteOutPutStream.toByteArray(), headers, HttpStatus.OK);
	}


}
