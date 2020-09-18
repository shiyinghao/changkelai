package com.icss.newretail.api.user;

import com.icss.newretail.model.*;
import com.icss.newretail.service.user.StoreCardService;
import org.apache.ibatis.annotations.Param;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : yanghu
 * @date : Created in 2020/7/16 17:06
 * @description : 各种名片api    +  商家服务记录 -- 店铺信息 跨库查询
 * @modified By :
 * @version: : 1.0.0
 */

@RestSchema(schemaId = "StoreCard")
@RequestMapping("/v1/StoreCard")
public class StoreCardApi {


    @Autowired
    StoreCardService storeCardService;


    /**
     * 获取商品专用名片
     * @return
     */
    @GetMapping("queryGoodsUsaCard")
    public ResponseResult<StoreCardDTO> queryGoodsUsaCard() {
        return storeCardService.queryGoodsUsaCard();
    }

    /**
     * 查询活动可用名片
     * @return
     */
    @GetMapping("queryActivityUsaCard")
    public ResponseRecords<StoreCardDTO> queryActivityUsaCard() {
        return storeCardService.queryActivityUsaCard();
    }

    /**
     * 查询店铺可用名片
     * @return
     */
    @GetMapping("queryStoreUsableCard")
    public ResponseRecords<StoreUsableCardDTO> queryStoreUsableCard(@RequestParam(name = "orgSeq") String orgSeq) {
        return storeCardService.queryStoreUsableCard(orgSeq);
    }

    /**
     *  查询店铺卡片配置
     * @param storeCardReq
     * @return
     */
    @PostMapping("queryStoreCardConfig")
    public ResponseResult<CardConfigDTO> queryStoreCardConfig(@RequestBody StoreCardReq storeCardReq ) {
        return storeCardService.queryStoreCardConfig(storeCardReq);
    }


    /**
     *  更新门店卡片
     * @param storeCardReq
     * @return
     */
    @PostMapping("updateStoreCardConfig")
    public ResponseBase updateStoreCardConfig(@RequestBody StoreCardReq storeCardReq ) {
        return storeCardService.updateStoreCardConfig(storeCardReq);
    }


    /**
     * 分页查询
     *
     * @param pageData
     * @return
     */
    @PostMapping("queryStoreCardInfo")
    public ResponseResultPage<StoreCardDTO> queryStoreCardInfo(@RequestBody PageData<StoreCardDTO> pageData) {

        return storeCardService.queryStoreCardInfo(pageData);

    }

    /**
     * 根据id查询
     *
     * @param uuid
     * @return
     */
    @PostMapping("queryStoreCardInfoByUuid")
    public ResponseResult<StoreCardDTO> queryStoreCardInfoByUuid(@RequestParam(name = "uuid") String uuid) {

        return storeCardService.queryStoreCardInfoByUuid(uuid);

    }

    /**
     * 新增
     *
     * @param storeCardDTO
     * @return
     */
    @PostMapping("addStoreCardInfo")
    public ResponseBase addStoreCardInfo(@RequestBody StoreCardDTO storeCardDTO) {

        return storeCardService.addStoreCardInfo(storeCardDTO);

    }

    /**
     * 根据id修改
     *
     * @param storeCardDTO
     * @return
     */
    @PostMapping("alterStoreCardInfo")
    public ResponseBase alterStoreCardInfo(@RequestBody StoreCardDTO storeCardDTO) {

        return storeCardService.alterStoreCardInfo(storeCardDTO);

    }


    /**
     *  商家服务记录---rpc调用
     * @param orgSeq
     * @param userId
     * @return
     */
    @PostMapping("queryStoreInfo")
    public ResponseResult<ServiceRecordDTO> queryStoreInfo(@Param("orgSeq") String orgSeq,@Param("userId") String userId) {
        return storeCardService.queryStoreInfo(orgSeq,userId);
    }






}
