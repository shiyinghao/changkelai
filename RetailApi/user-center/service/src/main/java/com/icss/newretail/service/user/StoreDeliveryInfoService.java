package com.icss.newretail.service.user;

import com.icss.newretail.model.*;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 门店配送信息表 服务类
 * </p>
 *
 * @author Mc_Jc
 * @since 2020-04-23
 */
public interface StoreDeliveryInfoService  {

    ResponseBase addStoreDeliveryInfo(StoreDeliveryInfoDTO storeDeliveryInfoDTO);

    ResponseBase updateStoreDeliveryInfo(StoreDeliveryInfoDTO storeDeliveryInfoDTO);

    ResponseBase deleteStoreDeliveryInfo(String orgSeq);

    ResponseResult<StoreDeliveryInfoDTO> queryStoreDeliveryInfoOne(String orgSeq);

    ResponseResultPage<StoreDeliveryInfoDTO> queryStoreDeliveryInfo(PageData<StoreDeliveryInfoDTO> page);
}
