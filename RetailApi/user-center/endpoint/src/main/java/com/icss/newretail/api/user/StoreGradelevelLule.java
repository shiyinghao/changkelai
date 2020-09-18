package com.icss.newretail.api.user;


import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.StoreGradelevelRuleDTO;
import com.icss.newretail.model.StoreGradelevelRuleRequst;
import com.icss.newretail.model.StoreRuleDTO;
import com.icss.newretail.service.user.StoreGradelevelRuleService;
import io.swagger.annotations.ApiParam;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;


/**
 * --门店等级规则表 t_user_store_gradelevel_rule
 * @author  yanghu
 * 2020.04.02
 * 测试通过
 */

//路径单词错误  无法修改  需要和前端联合修改
@RestSchema(schemaId = "StoreGradelevelLule")
@RequestMapping(path="/v1/StoreGradelevelLule")
public class StoreGradelevelLule {

    @Autowired
    private StoreGradelevelRuleService storeGradelevelRuleService;


    /**
     * --增加门店等级规则--
     */
    @PostMapping(path = "addStoreGradelevelLule")
    public ResponseBase addStoreGradelevelLule(@RequestBody StoreGradelevelRuleRequst storeGradelevelRuleRequst){
        return storeGradelevelRuleService.addStoreGradelevelLule(storeGradelevelRuleRequst);
    }

    /**
     *
     * --根据id停用门店等级规则-
     */
    @PostMapping(path = "deleteStoreGradelevelLule")
    public ResponseBase deleteStoreGradelevelLule(@NotEmpty(message = "参数uuid不能为空") @ApiParam(name = "uuid", value = "uuid", required = true) String uuids){
        return storeGradelevelRuleService.deleteStoreGradelevelLule(uuids);
    }

    /**
     *  --根据id修改 门店等级规则
     */
    @PostMapping(path = "updateStoreGradelevelLule")
    public ResponseBase updateStoreGradelevelLule(@RequestBody StoreGradelevelRuleRequst storeGradelevelRuleRequst){
        if (storeGradelevelRuleRequst.getUuid()==null||storeGradelevelRuleRequst.getUuid().equals("")){
            return  new ResponseBase(0, "uuid不能为空");
        }
        return storeGradelevelRuleService.updateStoreGradelevelLule(storeGradelevelRuleRequst);
    }

    /**
     * 查询 ----查询门店等级规则
     */
    @PostMapping(path = "queryStoreGradelevelLule")
    public ResponseResultPage<StoreGradelevelRuleDTO> queryStoreGradelevelLule(@RequestBody PageData<StoreGradelevelRuleRequst> pageData){
        return storeGradelevelRuleService.queryStoreGradelevelLule(pageData);
    }

    /**
     * 查询门店等级规则
     */
    @PostMapping(path = "queryStoreGradelevelRule")
    public ResponseRecords<StoreGradelevelRuleDTO> queryStoreGradelevelRule( ){
        return storeGradelevelRuleService.queryStoreGradelevelRule();
    }

    /**
     * 修改卡劵时显示门店等级规则
     */
    @PostMapping(path = "queryStoreRule")
    public ResponseRecords<StoreGradelevelRuleDTO> queryStoreRule(@RequestBody StoreRuleDTO storeRuleDTO){
        return storeGradelevelRuleService.queryStoreRule(storeRuleDTO);
    }

    /**
     * 根据等级id 查出门店等级信息
     */
    @GetMapping(path = "queryRuleById")
    public ResponseResult<StoreGradelevelRuleDTO> queryRuleById(@RequestParam() String storeLevelId){
        return storeGradelevelRuleService.queryRuleById(storeLevelId);
    }


    /**
     * --根据门店积分值查询门店等级
     * -----yanghu
     * @param score    需要查询门店的积分值
     * @return
     */
    @GetMapping(path = "queryGradelevel")
    public ResponseResult<StoreGradelevelRuleDTO> queryGradelevel(@RequestParam(value = "score",required = true) Integer score){
        return storeGradelevelRuleService.queryGradelevel(score);
    }

}
