package com.icss.newretail.service.user;

import com.icss.newretail.model.LoginAuthDTO;
import com.icss.newretail.model.LoginByAuthCode;
import com.icss.newretail.model.LoginByPwdDTO;
import com.icss.newretail.model.LoginMaDTO;
import com.icss.newretail.model.LoginMaRequest;
import com.icss.newretail.model.LoginRequestByPwd;
import com.icss.newretail.model.LoginUserDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.SaveUserInfoRequest;
import com.icss.newretail.model.ShopLoginRecordDTO;
import com.icss.newretail.model.UserAuthCodeReq;
import com.icss.newretail.model.UserStoreNameDTO;

import java.util.List;

public interface AuthMaService {

    ResponseResult<LoginMaDTO> loginShop(LoginMaRequest loginMaRequest);

    ResponseResult<LoginByPwdDTO> loginByPassword(LoginRequestByPwd loginRequestByPwd);

    ResponseResult<LoginAuthDTO> loginByAuthCode(LoginByAuthCode loginByAuthCode);

    ResponseBase saveUserMobile(SaveUserInfoRequest saveUserInfoRequest);

    ResponseBase checkToken();

    ResponseBase insert(UserAuthCodeReq userAuthCodeReq);

    public List<UserStoreNameDTO> queryStoreByUserId(String token);

    ResponseResult<LoginUserDTO> loginByPhone(LoginByAuthCode loginByAuthCode);

    ResponseResult<LoginUserDTO> register(LoginByAuthCode loginByAuthCode);

    ResponseResult<LoginUserDTO> tokenCheck();

    ResponseResult<Long> addLoginRecord(ShopLoginRecordDTO dto);

    ResponseBase addLogoutRecord(Long id);
}
