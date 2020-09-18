package com.icss.newretail.api.user;

import com.icss.newretail.model.*;
import com.icss.newretail.service.user.StoreBonuspointRuleService;
import io.swagger.annotations.ApiParam;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.constraints.NotEmpty;

/**
 * --门店积分表规则表  t_user_store_bonuspoint_rule   --没有状态
 * @author  yanghu
 * 2020.04.02
 * --测试完毕
 */

@RestSchema(schemaId = "StoreBonuspointApi")
@RequestMapping(path="/v1/StoreBonuspoint")
public class StoreBonuspointRuleApi {

    @Autowired
    private StoreBonuspointRuleService storeBonuspointRuleService;

    /**
     * --门店积分规则--
     */
    @PostMapping(path = "addStoreBonuspoint")
    public ResponseBase addStoreBonuspoint(@RequestBody StoreBonuspointRuleRequst storeBonuspointRuleRequst){
        return storeBonuspointRuleService.addStoreBonuspoint(storeBonuspointRuleRequst);
    }

    /**
     *
     * --根据id删除 门店积分规则--
     */
    @PostMapping(path = "deleteStoreBonuspoint")
    public ResponseBase deleteStoreBonuspoint(@NotEmpty(message = "参数uuid不能为空") @ApiParam(name = "uuids", value = "uuids", required = true) String uuids){
        return storeBonuspointRuleService.deleteStoreBonuspoint(uuids);
    }

    /**
     *  --根据id修改 门店积分规则表
     */
    @PostMapping(path = "updateStoreBonuspoint")
    public ResponseBase updateStoreBonuspoint(@RequestBody StoreBonuspointRuleRequst storeBonuspointRuleRequst){
        if (storeBonuspointRuleRequst.getUuid()==null||storeBonuspointRuleRequst.getUuid().equals("")){
            return  new ResponseBase(0, "uuid不能为空");
        }
        return storeBonuspointRuleService.updateStoreBonuspoint(storeBonuspointRuleRequst);
    }

    /**
     * 查询 ----查询门店积分规则表
     */
    @PostMapping(path = "queryStoreBonuspoint")
    public ResponseResultPage<StoreBonuspointRuleDTO> queryStoreBonuspoint(@RequestBody PageData<StoreBonuspointRuleRequst> pageData){
        return storeBonuspointRuleService.queryStoreBonuspoint(pageData);
    }

    /**
     * 查询   单个查询
     *
     */
    @PostMapping(path = "queryStoreBonuspointOne")
    public ResponseResult<StoreBonuspointRuleDTO> queryStoreBonuspointOne(){
        return storeBonuspointRuleService.queryStoreBonuspointOne();
    }

}
