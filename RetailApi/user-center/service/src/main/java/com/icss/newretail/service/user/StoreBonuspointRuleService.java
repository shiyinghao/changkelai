package com.icss.newretail.service.user;


import com.icss.newretail.model.*;

/**
 * <p>
 * 门店积分绑定表 服务类
 * </p>
 *
 * @author yanghu
 * @since 2020-04-02
 */
public interface StoreBonuspointRuleService {

    ResponseBase addStoreBonuspoint(StoreBonuspointRuleRequst storeBonuspointRuleRequst);

    ResponseBase deleteStoreBonuspoint(String uuids);

    ResponseBase updateStoreBonuspoint(StoreBonuspointRuleRequst storeBonuspointRuleRequst);

    ResponseResultPage<StoreBonuspointRuleDTO> queryStoreBonuspoint(PageData<StoreBonuspointRuleRequst> pageData);

    ResponseResult<StoreBonuspointRuleDTO> queryStoreBonuspointOne();
}
