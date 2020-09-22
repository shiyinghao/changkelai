package com.icss.newretail.api.user;

import com.icss.newretail.model.*;
import com.icss.newretail.service.user.AuthMaService;
import io.swagger.annotations.ApiOperation;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestSchema(schemaId = "authMa")
@RequestMapping(path = "/v1/authMa")
public class AuthMaApi {
	@Autowired
	private AuthMaService authMaService;

	public static String randomCode() {
		StringBuilder str = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			str.append(random.nextInt(10));
		}
		return str.toString();
	}

	/**
	 * 商家端登录曾经是否微信小程序
	 *
	 * @param loginMaRequest
	 * @return
	 */
	@PostMapping(path = "loginShop")
	public ResponseResult<LoginMaDTO> loginShop(@RequestBody @Valid LoginMaRequest loginMaRequest) {
		return authMaService.loginShop(loginMaRequest);
	}

	/**
	 * 商家微信端账号密码登录
	 *
	 * @param loginRequestByPwd
	 * @return
	 */
	@PostMapping(path = "loginByPassword")
	public ResponseResult<LoginByPwdDTO> loginByPassword(@RequestBody @Valid LoginRequestByPwd loginRequestByPwd) {
		return authMaService.loginByPassword(loginRequestByPwd);
	}

	/**
	 * 小程序保存用户手机号
	 *
	 * @param saveUserInfoRequest
	 * @return
	 */
	@PostMapping(path = "saveUserMobile")
	public ResponseBase saveUserMobile(
			@RequestBody @Valid SaveUserInfoRequest saveUserInfoRequest) {
		return authMaService.saveUserMobile(saveUserInfoRequest);
	}

	/**
	 * 效验token有效性
	 *
	 * @return
	 */
	@ApiOperation(value = "效验token")
	@GetMapping("/checkToken")
	public ResponseBase checkToken() {
		return authMaService.checkToken();
	}

	/**
	 * 调用华为发送短信验证码给客户
	 *
	 * @return
	 */
	@GetMapping(path = "sendMsg")
	public ResponseBase sendMsg(@RequestParam String phone) {
		String genCode = randomCode();
		try {
//			SendSmsUtil.senMessage(phone, genCode);
		} catch (Exception e) {
			return new ResponseBase().setMessage("发送短信验证码失败");
		}
		UserAuthCodeReq userAuthCodeReq = new UserAuthCodeReq();
		userAuthCodeReq.setCode(genCode);
		userAuthCodeReq.setPhone(phone);
		userAuthCodeReq.setSendTime(LocalDateTime.now());
		ResponseBase responseBase = authMaService.insert(userAuthCodeReq);
		return responseBase;
	}

	/**
	 * 手机验证码登录,尚未加邀请码
	 *
	 * @param loginByAuthCode
	 * @return
	 * @Author wangyao
	 */
	@PostMapping(path = "loginByAuthCode")
	public ResponseResult<LoginAuthDTO> loginByAuthCode(@RequestBody @Valid LoginByAuthCode loginByAuthCode) {
		return authMaService.loginByAuthCode(loginByAuthCode);
	}

	/**
	 * 判断token中 用户是否已绑定店铺接口
	 *
	 * @param
	 * @return
	 * @Author wangyao
	 */
	@PostMapping(path = "queryStoreByUserId")
	public List<UserStoreNameDTO> queryStoreByUserId(String token) {
		return authMaService.queryStoreByUserId(token);
	}

	/**
	 * 商家端通过手机验证码登录
	 *
	 * @param loginByAuthCode
	 * @return
	 */
	@PostMapping(path = "loginByPhone")
	public ResponseResult<LoginUserDTO> loginByPhone(@RequestBody LoginByAuthCode loginByAuthCode) {
		return authMaService.loginByPhone(loginByAuthCode);
	}

	/**
	 * 业务员注册并登录
	 *
	 * @param loginByAuthCode
	 * @return
	 */
	@PostMapping(path = "register")
	public ResponseResult<LoginUserDTO> register(@RequestBody LoginByAuthCode loginByAuthCode) {
		return authMaService.register(loginByAuthCode);
	}

	/**
	 * 登录token检测
	 * @return
	 */
	@PostMapping(path = "tokenCheck")
	public ResponseResult<LoginUserDTO> tokenCheck() {
		return authMaService.tokenCheck();
	}

	/**
	 * 商家端登录记录
	 * @param dto
	 * @return
	 */
	@PostMapping(path = "addLoginRecord")
	public ResponseResult<Long> addLoginRecord(@RequestBody ShopLoginRecordDTO dto){
		return authMaService.addLoginRecord(dto);
	}

	/**
	 * 商家端登出维护登出时间
	 * @param id
	 * @return
	 */
	@PostMapping(path = "addLogoutRecord")
	public ResponseBase addLogoutRecord(@RequestParam Long id){
		return authMaService.addLogoutRecord(id);
	}

}
