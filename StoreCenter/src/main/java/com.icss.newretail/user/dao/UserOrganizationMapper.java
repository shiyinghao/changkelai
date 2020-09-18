package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.OrganizationDTO;
import com.icss.newretail.user.entity.UserOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织机构 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2020-03-17
 */
public interface UserOrganizationMapper extends BaseMapper<UserOrganization> {

    /**
     * 根据战区编码查询门店
     */
    List<OrganizationDTO> getStoreOrgSeq(@Param("orgSeq") String orgSeq, @Param("orgType") String orgType,
                                         @Param("orgSeqZQName") String orgSeqZQName, @Param("orgSeqJDName") String orgSeqJDName,
                                         @Param("MDorgSeqName") String MDorgSeqName, @Param("authCode") String authCode);

    /**
     * 根据战区编码查询门店
     */
    List<OrganizationDTO> getStoreOrgInfoByList(@Param("list") List<String> list);


}
