package com.icss.newretail.service.user;

import com.icss.newretail.model.*;

public interface ShopGoodsDetailService {

	ResponseResult<UserStoreInfoDTO> queryShopDetailById(ShopGoodsParamDTO param);

	ResponseRecords<AuthorityParamsDTO> queryShopUserDetail(AuthorityParamsDTO para);

	ResponseBase addAuthority(AuthorityParamsDTO para);

	ResponseBase updateAuthority(AuthorityParamsDTO para);

	ResponseBase delAuthority(AuthorityParamsDTO para);

	ResponseRecords<MUserOrganizationDTO> getWarzoneList(ShopGoodsParamDTO para);

	ResponseRecords<MUserOrganizationDTO> getStoreList(ShopGoodsParamDTO para);


	ResponseRecords<ShopAppletAuthortiryDto> queryAuthority();

	ResponseBase saveAuthority(UserAuthorityRelationReq para);

	ResponseBase deleteAuthority(UserAuthorityRelationDto para);

	ResponseBase updateAppletAuthority(ShopAppletAuthortiryDto para);

	ResponseBase addAppletAuthority(ShopAppletAuthortiryDto para);

	ResponseBase deleteAppletAuthority(String uuid);

	ResponseResult<MUserOrganizationDTO> getWarzoneName(ShopGoodsParamDTO para);

	ResponseResult<AppletAuthorityDTO> getAuthority(AppletAuthorityDTO dto);

	ResponseRecords<MUserOrganizationDTO> getStoreListByWar(ShopGoodsParamDTO para);

	ResponseResult<MUserOrganizationDTO> getWarzoneNameByOrgSeq(ShopGoodsParamDTO para);

	ResponseRecords<UserStoreInfoDTO> queryStoreListByKey(ShopGoodsParamDTO para);
}
