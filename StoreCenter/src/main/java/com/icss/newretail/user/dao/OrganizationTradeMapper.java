package com.icss.newretail.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.OrgTreeNodeDTO;
import com.icss.newretail.model.OrganizationDTO;
import com.icss.newretail.model.OrganizationTreeParameterDTO;
import com.icss.newretail.model.OrgsParameterDTO;
import com.icss.newretail.model.ShopInfoDTO;
import com.icss.newretail.model.UserInfoDTO;
import com.icss.newretail.model.UsersParameterDTO;
import com.icss.newretail.user.entity.UserOrganization;

@Repository
public interface OrganizationTradeMapper extends BaseMapper<UserOrganization> {

	List<OrganizationDTO> queryJTChildrenByParam(OrganizationTreeParameterDTO param);
    List<OrganizationDTO> queryZQChildrenByParam(OrganizationTreeParameterDTO param);
    List<OrganizationDTO> queryJDChildrenByParam(OrganizationTreeParameterDTO param);
    List<OrganizationDTO> queryMDByParam(OrganizationTreeParameterDTO param);
    
    List<OrgTreeNodeDTO> queryJTOrgByParam(OrganizationTreeParameterDTO param);
    List<OrgTreeNodeDTO> queryZQOrgByParam(OrganizationTreeParameterDTO param);
    List<OrgTreeNodeDTO> queryJDOrgByParam(OrganizationTreeParameterDTO param);
    List<OrgTreeNodeDTO> queryMDOrgParam(OrganizationTreeParameterDTO param);
    
    List<OrganizationDTO> queryOrgListByParam(OrgsParameterDTO param);
    List<String> queryShopListByParam(OrgsParameterDTO param);
    List<ShopInfoDTO> queryShopInfoListByParam(OrgsParameterDTO param);
    List<UserInfoDTO> queryUserListByParam(UsersParameterDTO param);
    List<UserInfoDTO> getShopEmployeesList(UsersParameterDTO param);
    
    List<OrganizationDTO> querySameWarZoneChildren(@Param("orgSeq") String orgSeq);
    
    ShopInfoDTO getShopInfo(OrganizationTreeParameterDTO param);
}
