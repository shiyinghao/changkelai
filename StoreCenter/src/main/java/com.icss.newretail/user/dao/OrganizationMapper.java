package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.user.entity.UserOrganization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationMapper extends BaseMapper<UserOrganization> {

    List<StoreFunctionDTO> queryStoreFunctions(@Param("orgId") String orgId);

    Page<OrganizationDTO> queryOrganizations(@Param("page") Page<OrganizationDTO> page,
                                             @Param("organizationRequest") OrganizationRequest para);
    
    
    List<OrganizationTypeDTO> getUserOrgType(@Param("orgType") String orgType,@Param("orgTypeName") String orgTypeName);

    List<OrganizationDTO> getUpOrg();

    List<OrganizationDTO> getActiveUpOrg(@Param("orgSeq")String orgSeq);

    List<OrganizationDTO> getSonOrg(@Param("upOrgSeq") String upOrgSeq);

    ResponseResult<OrganizationDTO> queryTopById(String orgSeq);

    List<OrgReslutDTO> queryStoreOrgseq(@Param("orgSeq") String orgSeq);

    List<OrganizationChildDTO> queryOrganizationById(String orgSeq);

    OrganizationChildDTO queryOrganization(String orgSeq);

    OrganizationDTO getOrgSeqType(@Param("orgSeq") String orgSeq);

    int queryCount(@Param("orgSeq") String orgList,@Param("userType") Integer UserType);

    OrganizationChildDTO queryZqOrgSeq(String orgSeq);

    UserStoreInfoDTO queryStoreById(String orgSeq);

    List<OrganizationDTO> getUpOrgQuery(String upOrgSeq);
}
