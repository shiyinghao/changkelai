package com.icss.newretail.service.user;

import java.util.List;

import com.icss.newretail.model.OrgTreeNodeDTO;
import com.icss.newretail.model.OrganizationDTO;
import com.icss.newretail.model.OrganizationTreeParameterDTO;
import com.icss.newretail.model.OrgsParameterDTO;
import com.icss.newretail.model.ShopInfoDTO;
import com.icss.newretail.model.UserInfoDTO;
import com.icss.newretail.model.UsersParameterDTO;

public interface OrganizationTradeService {

	List<OrganizationDTO> queryOrgChildrenByParam(OrganizationTreeParameterDTO param);
	
	List<OrgTreeNodeDTO> queryOrgByParam(OrganizationTreeParameterDTO param);
	
	List<String> queryShopListByParam(OrgsParameterDTO param);
	
	List<ShopInfoDTO> queryShopInfoListByParam(OrgsParameterDTO param);
	
	List<OrganizationDTO> queryOrgListByParam(OrgsParameterDTO param);
	
	List<UserInfoDTO> queryUserListByParam(UsersParameterDTO param);
	
	List<OrganizationDTO> querySameWarZoneChildren(String orgSeq);
	
	List<UserInfoDTO> getShopEmployeesList(UsersParameterDTO param);
	
	ShopInfoDTO getShopInfo(OrganizationTreeParameterDTO param);
}
