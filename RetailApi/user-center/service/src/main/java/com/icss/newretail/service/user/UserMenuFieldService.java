package com.icss.newretail.service.user;

import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.UserMenuFieldDTO;
import com.icss.newretail.model.UserMenuFieldParamDTO;

public interface UserMenuFieldService {

	ResponseRecords<UserMenuFieldDTO> queryUserMenuFieldByParam(UserMenuFieldParamDTO param);
	
	ResponseBase insertUserFieldRelation(UserMenuFieldParamDTO param);
}
