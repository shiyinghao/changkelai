package com.icss.newretail.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.UserMenuFieldDTO;
import com.icss.newretail.model.UserMenuFieldParamDTO;
import com.icss.newretail.service.user.UserMenuFieldService;
import com.icss.newretail.user.dao.UserMenuFieldMapper;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.ToolUtil;

import io.vertx.ext.auth.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserMenuFieldServiceImpl implements UserMenuFieldService {
	@Autowired
	private UserMenuFieldMapper userMenuFieldMapper;

	@Override
	public ResponseRecords<UserMenuFieldDTO> queryUserMenuFieldByParam(UserMenuFieldParamDTO param) {
		ResponseRecords<UserMenuFieldDTO> result = new ResponseRecords<UserMenuFieldDTO>();
		try {
			if (param != null && !ToolUtil.isEmpty(param.getMenuCode())) {
				param.setUserId(JwtTokenUtil.currUser());
				List<UserMenuFieldDTO> fieldList = userMenuFieldMapper.queryUserMenuFieldByParam(param);
				if (ToolUtil.isEmpty(fieldList)) {
					fieldList = new ArrayList<UserMenuFieldDTO>();
				}
				result.setCode(1);
				result.setRecords(fieldList);
				result.setMessage("菜单字段列表查询成功");
			}else{
				result.setCode(0);
				result.setMessage("缺少菜单字段列表查询信息参数，查询失败");
			}
		} catch (Exception ex) {
			log.error("UserMenuFieldServiceImpl|queryUserMenuFieldByParam->" + ex.getLocalizedMessage());
			ex.printStackTrace();
			result.setCode(0);
			result.setMessage("菜单字段列表查询成功");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseBase insertUserFieldRelation(UserMenuFieldParamDTO param) {
		ResponseBase result = new ResponseBase();
		try {
			if (param != null && !ToolUtil.isEmpty(param.getFieldList()) && !ToolUtil.isEmpty(param.getMenuCode())) {
				String userId = JwtTokenUtil.currUser();
				param.setUserId(userId);
				userMenuFieldMapper.deleteUserFieldRelation(param);
				int cnt = 0;
				for (String fieldCode : param.getFieldList()) {
					UserMenuFieldDTO fieldItem = new UserMenuFieldDTO();
					fieldItem.setRelationId(UUID.randomUUID().toString());
					fieldItem.setFieldCode(fieldCode);
					fieldItem.setUserId(userId);
					fieldItem.setMenuCode(param.getMenuCode());
					cnt += userMenuFieldMapper.insertUserFieldRelation(fieldItem);
				}
				if(cnt == param.getFieldList().size()){
					result.setCode(1);
					result.setMessage("保存用户关联菜单字段列表成功");
				}else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
					result.setCode(0);
					result.setMessage("用户关联菜单字段与实际添加数不一致，请刷新重试");
				}
			}else{
				result.setCode(0);
				result.setMessage("缺少用户关联菜单字段信息参数，保存失败");
			}
		} catch (Exception ex) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
			log.error("UserMenuFieldServiceImpl|insertUserFieldRelation->" + ex.getLocalizedMessage());
			ex.printStackTrace();
			result.setCode(0);
			result.setMessage("保存用户关联菜单字段列表失败");
		}
		return result;
	}

	
}
