package com.icss.newretail.api.user;

import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.StoreBonuspointReq;
import com.icss.newretail.service.user.StoreBonuspointService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * --门店积分-----
 * @author  yanghu
 * -----2020.04.14
 */
@RestSchema(schemaId = "StoreBonuspointScore")
@RequestMapping(path = "/v1/storeBonuspointScore")
public class StoreBonuspointApi {

    @Autowired
    private StoreBonuspointService storeBonuspointService;


    /**
     * ---用于分享
     * --根据门店编码  增加门店积分  生成积分记录   门店积分变动   修改门店等级--
     */
    @PostMapping(path = "updateStoreScore")
    public ResponseBase updateStoreScore(@RequestBody StoreBonuspointReq storeBonuspointReq){
        return storeBonuspointService.updateStoreScore(storeBonuspointReq);
    }

    /**
     * 消费者端   会员消费   店铺获得积分
     * @param storeBonuspointReq
     * @return
     */
    @PostMapping(path = "addStoreScore")
    public ResponseBase addStoreScore(@RequestBody StoreBonuspointReq storeBonuspointReq){
        return storeBonuspointService.addStoreScore(storeBonuspointReq);
    }
}
