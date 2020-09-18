package com.icss.newretail.user.service.impl;

import java.util.List;
import java.util.Map;

import com.icss.newretail.model.GrantUserDTO;
import com.icss.newretail.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.service.user.GrantPasswordService;
import com.icss.newretail.user.dao.GrantPasswordMapper;
import com.icss.newretail.util.MD5Util;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: GrantPasswordServiceImpl
 * @Description:  终端授权改价密码service实现类
 * @author jc
 * @date 2019年8月2日
 *
 */
@Service
@Slf4j
public class GrantPasswordServiceImpl implements GrantPasswordService {

	@Autowired
	private GrantPasswordMapper grantPasswordMapper;

	/**
	 * 门店终端授权改价账号列表
	 */
	@Override
	public ResponseRecords<GrantUserDTO> getGrantUserList(String orgSeq) {
		ResponseRecords<GrantUserDTO> responseRecords = new ResponseRecords<>();
		try {
			List<GrantUserDTO> grantUserDTOS = grantPasswordMapper.getGrantUserList(orgSeq);
			responseRecords.setCode(1);
			responseRecords.setRecords(grantUserDTOS);
			responseRecords.setMessage("获取成功");
		} catch (Exception e) {
			responseRecords.setCode(0);
			responseRecords.setMessage(e.getMessage());
			log.error("GrantPasswordServiceImpl|getGrantUserList获取失败" + e.getMessage());
		}
		return responseRecords;
	}

	/**
	 * 校验终端授权改价密码
	 */
	@Override
	public ResponseBase checkGrantPassword(String userId, String password, String orgSeq) {
		ResponseBase responseBase = new ResponseBase();
		try {
			GrantUserDTO grantUserDTO = grantPasswordMapper.getGrantPassword(userId, orgSeq);
			if (grantUserDTO != null && !StringUtils.isEmpty(grantUserDTO.getPassword())) {
				String pwd = MD5Util.toMD5(password);
				if (pwd.equals(grantUserDTO.getPassword())) {
					responseBase.setCode(1);
					responseBase.setMessage("密码校验成功");
				} else {
					responseBase.setCode(2);
					responseBase.setMessage("密码校验错误");
				}
			} else {
				responseBase.setCode(2);
				responseBase.setMessage("该店铺未设置授权密码");
			}
		} catch (Exception e) {
			responseBase.setCode(0);
			responseBase.setMessage("密码校验失败");
			log.error("GrantPasswordServiceImpl|checkGrantPassword密码校验失败" + e.getMessage());
		}
		return responseBase;
	}

}
