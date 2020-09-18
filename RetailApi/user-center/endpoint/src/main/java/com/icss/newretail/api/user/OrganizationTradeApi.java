package com.icss.newretail.api.user;

import java.util.List;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icss.newretail.model.OrgTreeNodeDTO;
import com.icss.newretail.model.OrganizationDTO;
import com.icss.newretail.model.OrganizationTreeParameterDTO;
import com.icss.newretail.model.OrgsParameterDTO;
import com.icss.newretail.model.ShopInfoDTO;
import com.icss.newretail.model.UserInfoDTO;
import com.icss.newretail.model.UsersParameterDTO;
import com.icss.newretail.service.user.OrganizationTradeService;

import io.swagger.annotations.ApiParam;

@RestSchema(schemaId = "organizationTrade")
@RequestMapping(path = "/v1/organizationTrade")
public class OrganizationTradeApi {
	@Autowired
	private OrganizationTradeService organizationTradeService;

	/**
	 * 获取组织机构树
	 * @return
	 */
	@PostMapping(path = "queryOrgChildrenByParam")
	public List<OrganizationDTO> queryOrgChildrenByParam(@RequestBody OrganizationTreeParameterDTO param) {
		return organizationTradeService.queryOrgChildrenByParam(param);
	}
	
	/**
	 * 获取组织机构树
	 * @return
	 */
	@PostMapping(path = "queryOrgByParam")
	public List<OrgTreeNodeDTO> queryOrgByParam(@RequestBody OrganizationTreeParameterDTO param) {
		return organizationTradeService.queryOrgByParam(param);
	}
	
	
	/**
	 * 获取人员列表
	 * @return
	 */
    @PostMapping(path = "queryUserListByParam")
	public List<UserInfoDTO> queryUserListByParam(@RequestBody UsersParameterDTO param) {
		return organizationTradeService.queryUserListByParam(param);
	}
    
    /**
	 * 条件筛选门店列表（单串）
	 * @return
	 */
	@PostMapping(path = "queryShopListByParam")
	public List<String> queryShopListByParam(@RequestBody OrgsParameterDTO param) {
		return organizationTradeService.queryShopListByParam(param);
	}
	
	/**
   	 * 条件筛选门店列表（信息）
   	 * @return
   	 */
    @PostMapping(path = "queryShopInfoListByParam")
    public List<ShopInfoDTO> queryShopInfoListByParam(@RequestBody OrgsParameterDTO param) {
		return organizationTradeService.queryShopInfoListByParam(param);
	}   
    
    /**
	 * 获取组织列表
	 * @return
	 */
    @PostMapping(path = "queryOrgListByParam")
	public List<OrganizationDTO> queryOrgListByParam(@RequestBody OrgsParameterDTO param) {
		return organizationTradeService.queryOrgListByParam(param);
	}
    
    /**
	 * 获取人员列表
	 * @return
	 */
    @PostMapping(path = "querySameWarZoneChildren")
	public List<OrganizationDTO> querySameWarZoneChildren(@ApiParam(name = "orgSeq",value = "组织编码",required = true) String orgSeq) {
		return organizationTradeService.querySameWarZoneChildren(orgSeq);
	}
    
    /**
	 * 获取员工列表
	 * @return
	 */
    @PostMapping(path = "getShopEmployeesList")
	public List<UserInfoDTO> getShopEmployeesList(@RequestBody UsersParameterDTO param) {
		return organizationTradeService.getShopEmployeesList(param);
	}
    
    /**
   	 * 获取门店信息
   	 * @return
   	 */
    @PostMapping(path = "getShopInfo")
    public ShopInfoDTO getShopInfo(@RequestBody OrganizationTreeParameterDTO param) {
		return organizationTradeService.getShopInfo(param);
	}

}
