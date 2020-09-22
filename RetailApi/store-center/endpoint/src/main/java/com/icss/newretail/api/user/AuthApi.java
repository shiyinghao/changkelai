package com.icss.newretail.api.user;

import com.icss.newretail.model.LoginInRequest;
import com.icss.newretail.model.LoginRequest;
import com.icss.newretail.model.LoginUserDTO;
import com.icss.newretail.model.LoginUserPasswordDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.service.user.AuthService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

//import org.apache.servicecomb.transport.highway.message.LoginRequest;

@RestSchema(schemaId = "auth")
@RequestMapping(path = "/v1/auth")
public class AuthApi {
	@Autowired
	private AuthService authService;

	/**
	 * 登录
	 *
	 * @param loginRequest
	 * @return
	 */
	@PostMapping(path = "login")
	public ResponseResult<LoginUserDTO> login(@RequestBody @Valid LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}

	/**
	 * 登出
	 *
	 * @param
	 * @return
	 */
	@PostMapping(path = "logout")
	public ResponseBase logout(@RequestHeader(required = false) String Authorization) {
		return authService.logout(Authorization);
	}

	/**
	 * 校验登录密码
	 *
	 * @param
	 * @return
	 */
	@PostMapping(path = "checkPassword")
	public ResponseBase checkPassword(
			@RequestBody LoginUserPasswordDTO para) {
		return authService.checkPassword(para);
	}

	/**
	 * 验证welink生成token接口
	 *
	 * @param
	 * @return
	 */
	@PostMapping(path = "telLogin")
	public ResponseResult<LoginUserDTO> telLogin(
			@RequestBody LoginUserPasswordDTO para) {
		return authService.telLogin(para);
	}

	/**
	 * 巡店店长登录 手机+密码
	 *
	 * @param
	 * @return
	 */
	@PostMapping(path = "loginIn")
	public ResponseResult<LoginUserDTO> loginIn(@RequestBody LoginInRequest loginInRequest) {
		return authService.loginIn(loginInRequest);
	}

	/**
	 * 忘记密码发送验证码
	 *
	 * @param authId
	 * @param phone
	 * @return
	 */
	@PostMapping("sendVerificationCode")
	public ResponseResult<String> sendVerificationCode(@RequestParam String authId, @RequestParam String phone) {
		return authService.sendVerificationCode(authId, phone);
	}

	/**
	 * 重置密码
	 * @param authId
	 * @param password
	 * @return
	 */
	@PostMapping("resetPassword")
	public ResponseBase resetPassword(@RequestParam String authId, @RequestParam String password) {
		return authService.resetPassword(authId, password);
	}

}
