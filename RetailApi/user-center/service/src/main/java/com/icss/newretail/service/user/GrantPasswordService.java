package com.icss.newretail.service.user;

import java.util.Map;

import com.icss.newretail.model.GrantUserDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;

/**
 * @ClassName: GrantPasswordService
 * @Description: 终端授权改价密码service接口
 * @author jc
 * @date 2019年8月2日
 *
 */
public interface GrantPasswordService {

	public ResponseRecords<GrantUserDTO> getGrantUserList(String orgSeq);

	public ResponseBase checkGrantPassword(String userId, String password, String orgSeq);
}
