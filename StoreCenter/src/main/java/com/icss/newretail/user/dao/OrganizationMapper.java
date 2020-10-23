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

    List<OrganizationDTO> getUpOrg();

    List<OrganizationDTO> getSonOrg(@Param("upOrgSeq") String upOrgSeq);

    int queryCount(@Param("orgSeq") String orgList,@Param("userType") Integer UserType);

    List<OrganizationTypeDTO> getUserOrgType(@Param("orgType") String orgType,@Param("orgTypeName") String orgTypeName);

    List<OrgReslutDTO> queryStoreOrgseq(@Param("orgSeq") String orgSeq);

    OrganizationDTO getOrgSeqType(@Param("orgSeq") String orgSeq);

}
