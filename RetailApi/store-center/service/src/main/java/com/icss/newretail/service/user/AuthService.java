package com.icss.newretail.service.user;

import com.icss.newretail.model.LoginInRequest;
import com.icss.newretail.model.LoginRequest;
import com.icss.newretail.model.LoginUserDTO;
import com.icss.newretail.model.LoginUserPasswordDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;

public interface AuthService {

	public String generateVerifyCode();

	public Integer verifyVerifyCode();

	public ResponseResult<LoginUserDTO> login(LoginRequest loginRequest);

	public ResponseResult<LoginUserDTO> loginWithVerifyCode(String authType, String authId, String password,
	                                                        String verifyCode);

	public ResponseBase logout(String token);

	public ResponseBase checkPassword(LoginUserPasswordDTO para);

	public ResponseResult<LoginUserDTO> telLogin(LoginUserPasswordDTO para);

	public ResponseResult<LoginUserDTO> loginIn(LoginInRequest loginRequest);

	ResponseResult<String> sendVerificationCode(String authId, String phone);

	ResponseBase resetPassword(String authId, String password);

}
