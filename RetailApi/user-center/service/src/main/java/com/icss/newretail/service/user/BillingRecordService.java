package com.icss.newretail.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icss.newretail.model.*;

/**
 * <p>
 * 店铺开票记录 服务类
 * </p>
 *
 * @author zhangzhijia
 * @since 2020-05-26
 */
public interface BillingRecordService{

    ResponseBase addBillingRecord(BillingRecordDTO para);

    ResponseResultPage<BillingRecordDTO> queryBillingRecord(PageData<BillingRecordDTO> para);

    ResponseBase updateBillingRecordById(BillingRecordDTO para);

    ResponseResult<BillingRecordDTO> queryBillingRecordById(String orgseq,String uuid);


    ResponseResultPage<BillingRegionDTO> queryRegionTel(PageData<BillingRecordDTO> para);
}
