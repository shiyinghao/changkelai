package com.icss.newretail.service.user;

import com.icss.newretail.model.ActivationCountDTO;
import com.icss.newretail.model.ResponseResult;

/**
 * @author jc
 * @date 2020/7/28 17:34
 */
public interface IStoreActivationService {

	ResponseResult<ActivationCountDTO> queryActivationCount(String beginDate, String endDate);

}
