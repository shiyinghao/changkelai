package com.icss.newretail.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icss.newretail.model.OrgTreeNodeDTO;
import com.icss.newretail.model.OrganizationDTO;
import com.icss.newretail.model.OrganizationTreeParameterDTO;
import com.icss.newretail.model.OrgsParameterDTO;
import com.icss.newretail.model.ShopInfoDTO;
import com.icss.newretail.model.UserInfoDTO;
import com.icss.newretail.model.UsersParameterDTO;
import com.icss.newretail.service.user.OrganizationTradeService;
import com.icss.newretail.user.dao.OrganizationTradeMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrganizationTradeServiceImpl implements OrganizationTradeService {
	@Autowired
	private OrganizationTradeMapper organizationTradeMapper;

	@Override
	public List<OrganizationDTO> queryOrgChildrenByParam(OrganizationTreeParameterDTO param) {
		String orgType = param.getOrgType();
		if ("1".equals(orgType)) {//集团
			return organizationTradeMapper.queryJTChildrenByParam(param);
		}else if ("2".equals(orgType)) {//战区
			return organizationTradeMapper.queryZQChildrenByParam(param);
		}else if ("3".equals(orgType)) {//战区
			return organizationTradeMapper.queryJDChildrenByParam(param);
		}else if("4".equals(orgType)){//门店
			return organizationTradeMapper.queryMDByParam(param);
		}else{
			return null;
		}
	}

	@Override
	public List<OrgTreeNodeDTO> queryOrgByParam(OrganizationTreeParameterDTO param) {
		String orgType = param.getOrgType();
		if ("1".equals(orgType)) {//集团
			return organizationTradeMapper.queryJTOrgByParam(param);
		}else if ("2".equals(orgType)) {//战区
			return organizationTradeMapper.queryZQOrgByParam(param);
		}else if("3".equals(orgType)){//基地
			return organizationTradeMapper.queryJDOrgByParam(param);
		}else if("4".equals(orgType)){//门店
			return organizationTradeMapper.queryMDOrgParam(param);
		}else{
			return null;
		}
	}
	
	@Override
	public List<OrganizationDTO> queryOrgListByParam(OrgsParameterDTO param) {
		return organizationTradeMapper.queryOrgListByParam(param);
	}
	
	@Override
	public List<String> queryShopListByParam(OrgsParameterDTO param) {
		return organizationTradeMapper.queryShopListByParam(param);
	}
	
	@Override
	public List<ShopInfoDTO> queryShopInfoListByParam(OrgsParameterDTO param) {
		return organizationTradeMapper.queryShopInfoListByParam(param);
	}

	@Override
	public List<UserInfoDTO> queryUserListByParam(UsersParameterDTO param) {
		return organizationTradeMapper.queryUserListByParam(param);
	}

	@Override
	public List<OrganizationDTO> querySameWarZoneChildren(String orgSeq) {
		return organizationTradeMapper.querySameWarZoneChildren(orgSeq);
	}

	@Override
	public List<UserInfoDTO> getShopEmployeesList(UsersParameterDTO param) {
		return organizationTradeMapper.getShopEmployeesList(param);
	}

	@Override
	public ShopInfoDTO getShopInfo(OrganizationTreeParameterDTO param) {
		return organizationTradeMapper.getShopInfo(param);
	}
}
