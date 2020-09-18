package com.icss.newretail.service.user;

import com.icss.newretail.model.*;

/**
 * @author : yanghu
 * @date : Created in 2020/7/16 17:10
 * @description : TODO
 * @modified By :
 * @version: : TODO
 */

public interface StoreCardService {

    ResponseResultPage<StoreCardDTO> queryStoreCardInfo(PageData<StoreCardDTO> pageData);

    ResponseResult<StoreCardDTO> queryStoreCardInfoByUuid(String uuid);

    ResponseBase addStoreCardInfo(StoreCardDTO storeCardDTO);

    ResponseBase alterStoreCardInfo(StoreCardDTO storeCardDTO);

    ResponseBase updateStoreCardConfig(StoreCardReq storeCardReq);

    ResponseResult<CardConfigDTO> queryStoreCardConfig(StoreCardReq storeCardReq);

    ResponseRecords<StoreUsableCardDTO> queryStoreUsableCard(String orgSeq);

    ResponseResult<StoreCardDTO> queryGoodsUsaCard();

    ResponseRecords<StoreCardDTO> queryActivityUsaCard();

    ResponseResult<ServiceRecordDTO> queryStoreInfo(String orgSeq , String userId);
}
