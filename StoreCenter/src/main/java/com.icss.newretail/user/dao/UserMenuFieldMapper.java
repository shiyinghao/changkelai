package com.icss.newretail.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.UserMenuFieldDTO;
import com.icss.newretail.model.UserMenuFieldParamDTO;
import com.icss.newretail.user.entity.UserOrganization;

@Repository
public interface UserMenuFieldMapper extends BaseMapper<UserOrganization> {

	List<UserMenuFieldDTO> queryUserMenuFieldByParam(UserMenuFieldParamDTO param);
   
	int deleteUserFieldRelation(UserMenuFieldParamDTO param);
    
	int insertUserFieldRelation(UserMenuFieldDTO param);
}
