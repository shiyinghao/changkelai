package com.icss.newretail.service.user;

import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.StoreBonuspointReq;

/**
 * <p>
 * 门店积分绑定表 服务类
 * </p>
 *
 * @author yanghu
 * @date  2020-04-14
 */
public interface StoreBonuspointService {

    /**
     * 更新门店积分    传入的是积分
     * @param storeBonuspointReq
     * @return
     */
    ResponseBase updateStoreScore(StoreBonuspointReq storeBonuspointReq);

    /**
     * 会员消费金额 -----
     * 传入的是
     * @param storeBonuspointReq
     * @return
     */
    ResponseBase addStoreScore(StoreBonuspointReq storeBonuspointReq);
}
