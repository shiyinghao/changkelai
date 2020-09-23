package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.AuthorityParamsDTO;
import com.icss.newretail.model.MUserOrganizationDTO;
import com.icss.newretail.model.ShopGoodsParamDTO;
import com.icss.newretail.model.UserOrganizationDTO;
import com.icss.newretail.model.UserStoreInfoDTO;
import com.icss.newretail.user.entity.UserOrganization;
import com.icss.newretail.user.entity.UserOrganizationInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ShopGoodsDetailMapper extends BaseMapper<UserOrganizationInfo> {

	UserStoreInfoDTO queryShopDetailById(@Param("request") ShopGoodsParamDTO param);

	List<AuthorityParamsDTO> queryShopUserDetail(@Param("request") AuthorityParamsDTO condition);

	List<MUserOrganizationDTO> getWarzone(ShopGoodsParamDTO para);

	List<MUserOrganizationDTO> getStoreList(ShopGoodsParamDTO para);

	MUserOrganizationDTO getWarzoneName(@Param("request") ShopGoodsParamDTO orgSeq);

	List<MUserOrganizationDTO> getStoreListByWar(@Param("request") ShopGoodsParamDTO para);

	MUserOrganizationDTO getWarzoneNameByOrgSeq(@Param("request") ShopGoodsParamDTO para);

	List<UserStoreInfoDTO> queryStoreListByKey(@Param("request") ShopGoodsParamDTO para);
}
