package com.icss.newretail.api.user;

import com.icss.newretail.model.*;
import com.icss.newretail.service.user.ShopGoodsDetailService;

import io.swagger.annotations.ApiParam;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestSchema(schemaId = "detail")
@RequestMapping(path = "/v1/detail")
public class ShopGoodsDetailApi {

	@Autowired
	private ShopGoodsDetailService shopGoodsDetailService;

	/**
	 * 查询门店详情
	 *
	 * @param: 店铺编码org_seq
	 * @param: 经度：
	 *             lng
	 * @param: 纬度：
	 *             lat 测试：orgSeq='d70a8cfb-ea5f-442d-a558-dd6115100d80'
	 * @return
	 */
	@PostMapping(path = "queryShopDetailById")
	public ResponseResult<UserStoreInfoDTO> queryShopDetailById(@RequestBody ShopGoodsParamDTO param) {

		return shopGoodsDetailService.queryShopDetailById(param);
	}

	/**
	 * 查询门店人员信息
	 *
	 * @param
	 * 
	 * @return
	 */
	@PostMapping(path = "queryShopUserDetail")
	public ResponseRecords<AuthorityParamsDTO> queryShopUserDetail(@RequestBody AuthorityParamsDTO para) {

		return shopGoodsDetailService.queryShopUserDetail(para);
	}

	// /**
	// * 设置门店人员的权限（新增）
	// * 注释
	// * @param
	// * @return
	// */

	// @PostMapping(path = "addAuthority")
	// public ResponseBase addAuthority(@RequestBody AuthorityParamsDTO para) {
	//
	// return shopGoodsDetailService.addAuthority(para);
	// }

	// /**
	// * 设置 门店人员的权限（修改）
	// *
	// * @param
	// * @return
	// */

	// @DeleteMapping(path = "updateAuthority")
	// public ResponseBase updateAuthority(@RequestBody AuthorityParamsDTO para)
	// {
	//
	// return shopGoodsDetailService.updateAuthority(para);
	// }

	// /**
	// * 设置 门店人员的权限（删除）
	// *
	// * @param
	// * @return
	// */
	//
	// @DeleteMapping(path = "delAuthority")
	// public ResponseBase delAuthority(@RequestBody AuthorityParamsDTO para) {
	//
	// return shopGoodsDetailService.delAuthority(para);
	// }

	// 查询战区
	@PostMapping(path = "getWarzoneList")
	public ResponseRecords<MUserOrganizationDTO> getWarzoneList(@RequestBody ShopGoodsParamDTO para) {

		return shopGoodsDetailService.getWarzoneList(para);
	}

	// 根据战区查询下面的门店 参数：战区orgSeq
	@PostMapping(path = "getStoreListByWar")
	public ResponseRecords<MUserOrganizationDTO> getStoreListByWar(@RequestBody ShopGoodsParamDTO para) {

		return shopGoodsDetailService.getStoreListByWar(para);
	}

	// 查询门店
	@PostMapping(path = "getStoreList")
	public ResponseRecords<MUserOrganizationDTO> getStoreList(@RequestBody ShopGoodsParamDTO para) {

		return shopGoodsDetailService.getStoreList(para);
	}

	// 查询战区名称 根据orgSeq 一条
	@PostMapping(path = "getWarzoneName")
	public ResponseResult<MUserOrganizationDTO> getWarzoneName(@RequestBody ShopGoodsParamDTO para) {

		return shopGoodsDetailService.getWarzoneName(para);
	}

	// ----ynaghu -----
	// @data 2020.04.19
	// 保存门店人员权限
	@PostMapping(path = "saveAuthority")
	public ResponseBase saveAuthority(@RequestBody UserAuthorityRelationReq para) {
		return shopGoodsDetailService.saveAuthority(para);
	}

	// 查询权限列表 查表 t_user_shop_applet_authortiry
	@PostMapping(path = "queryAuthority")
	public ResponseRecords<ShopAppletAuthortiryDto> queryAuthority() {

		return shopGoodsDetailService.queryAuthority();
	}

	// //删除人员权限
	// @PostMapping(path = "deleteAuthority")
	// public ResponseBase deleteAuthority(@RequestBody UserAuthorityRelationDto
	// para) {
	// return shopGoodsDetailService.deleteAuthority(para);
	// }

	// -------------------------
	// 更新 t_user_shop_applet_authortiry
	@PostMapping(path = "updateAppletAuthority")
	public ResponseBase updateAppletAuthority(@RequestBody ShopAppletAuthortiryDto para) {
		return shopGoodsDetailService.updateAppletAuthority(para);
	}

	// 增加 t_user_shop_applet_authortiry
	@PostMapping(path = "addAppletAuthority")
	public ResponseBase addAppletAuthority(@RequestBody ShopAppletAuthortiryDto para) {
		return shopGoodsDetailService.addAppletAuthority(para);
	}

	// 删除 t_user_shop_applet_authortiry
	@PostMapping(path = "deleteAppletAuthority")
	public ResponseBase deleteAppletAuthority(@ApiParam(name = "uuid", value = "删除权限", required = true) String uuid) {
		return shopGoodsDetailService.deleteAppletAuthority(uuid);
	}

	/**
	 * 获取权限
	 * 
	 * @param appletAuthorityDTO
	 * @return
	 */
	@PostMapping(path = "getAuthority")
	public ResponseResult<AppletAuthorityDTO> getAuthority(@RequestBody AppletAuthorityDTO appletAuthorityDTO) {
		return shopGoodsDetailService.getAuthority(appletAuthorityDTO);
	}

	// 查询战区名称 根据门店orgSeq 一条记录
	@PostMapping(path = "getWarzoneNameByOrgSeq")
	public ResponseResult<MUserOrganizationDTO> getWarzoneNameByOrgSeq(@RequestBody ShopGoodsParamDTO para) {

		return shopGoodsDetailService.getWarzoneNameByOrgSeq(para);
	}

	// 查询门店 模糊查询
	@PostMapping(path = "queryStoreListByKey")
	public ResponseRecords<UserStoreInfoDTO> queryStoreListByKey(@RequestBody ShopGoodsParamDTO para) {

		return shopGoodsDetailService.queryStoreListByKey(para);
	}

}
