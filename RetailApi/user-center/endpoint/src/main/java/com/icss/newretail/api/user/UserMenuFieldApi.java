package com.icss.newretail.api.user;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.UserMenuFieldDTO;
import com.icss.newretail.model.UserMenuFieldParamDTO;
import com.icss.newretail.service.user.UserMenuFieldService;

@RestSchema(schemaId = "userMenuField")
@RequestMapping(path = "/v1/userMenuField")
public class UserMenuFieldApi {
	@Autowired
	private UserMenuFieldService userMenuFieldService;

	/**
	 * 根据参数获取列表字段
	 * @param
	 * @return
	 */
	@PostMapping(path = "queryUserMenuFieldByParam")
	public ResponseRecords<UserMenuFieldDTO> queryUserMenuFieldByParam(@RequestBody UserMenuFieldParamDTO param){
		return userMenuFieldService.queryUserMenuFieldByParam(param);
	}
	
	/**
	 * 增加定制列表字段
	 * @param
	 * @return
	 */
	@PostMapping(path = "insertUserFieldRelation")
	public ResponseBase insertUserFieldRelation(@RequestBody UserMenuFieldParamDTO param) {
		return userMenuFieldService.insertUserFieldRelation(param);
	}
}
