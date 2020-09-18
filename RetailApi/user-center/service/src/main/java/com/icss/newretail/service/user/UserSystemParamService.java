package com.icss.newretail.service.user;

import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.UserSystemParamDTO;
import com.icss.newretail.model.UserSystemParamValueDTO;

/**
 * <p>
 * 系统参数表 服务类
 * </p>
 *
 * @author jc
 * @since 2020-03-26
 */
public interface UserSystemParamService {

    ResponseRecords<UserSystemParamDTO> queryUserSystemParam(String name, String code);

    ResponseBase createSystemParam(UserSystemParamDTO systemParam);

    ResponseBase modifySystemParam(UserSystemParamDTO systemParam);

    ResponseBase deleteSystemParam(String uuid);

    ResponseRecords<UserSystemParamValueDTO> queryUserSystemParamValue(String paramId);

    ResponseBase deleteSystemParamValue(String uuid);

    ResponseBase modifySystemParamValue(UserSystemParamValueDTO systemParamValue);

    ResponseBase createSystemParamValue(UserSystemParamValueDTO systemParamValue);

    ResponseResultPage<UserSystemParamDTO> querySystemParam(PageData<UserSystemParamDTO> pageData);

    ResponseResult<UserSystemParamDTO> querybindexpiredDay(String code);
}
