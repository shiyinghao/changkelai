package com.icss.newretail.api.user;

import com.icss.newretail.model.ActivationCountDTO;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.service.user.IStoreActivationService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jc
 * @date 2020/7/28 17:06
 */
@RestSchema(schemaId = "StoreActivation")
@RequestMapping(path = "storeActivation")
public class StoreActivationApi {

	@Autowired
	private IStoreActivationService iStoreActivationService;

	/**
	 * 商家活跃度数据查询
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@PostMapping("queryActivationCount")
	public ResponseResult<ActivationCountDTO> queryActivationCount(@RequestParam String beginDate,@RequestParam String endDate){
		return iStoreActivationService.queryActivationCount(beginDate, endDate);
	}

}
