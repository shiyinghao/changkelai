package com.icss.newretail.service.user;


import com.icss.newretail.model.*;

/**
 * <p>
 * 门店等级规则 服务类
 * </p>
 *
 * @author yanghu
 * @since 2020-04-02
 */
public interface StoreGradelevelRuleService{

    ResponseResultPage<StoreGradelevelRuleDTO> queryStoreGradelevelLule(PageData<StoreGradelevelRuleRequst> pageData);

    ResponseBase addStoreGradelevelLule(StoreGradelevelRuleRequst storeGradelevelRuleRequst);

    ResponseBase deleteStoreGradelevelLule(String uuids);

    ResponseBase updateStoreGradelevelLule(StoreGradelevelRuleRequst storeGradelevelRuleRequst);

    ResponseRecords<StoreGradelevelRuleDTO> queryStoreGradelevelRule();

    ResponseRecords<StoreGradelevelRuleDTO> queryStoreRule(StoreRuleDTO storeRuleDTO);

    ResponseResult<StoreGradelevelRuleDTO> queryRuleById(String storeLevelId);

    ResponseResult<StoreGradelevelRuleDTO> queryGradelevel(Integer score);
}
