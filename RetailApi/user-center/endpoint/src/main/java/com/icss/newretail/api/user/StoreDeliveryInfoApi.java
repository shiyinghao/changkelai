package com.icss.newretail.api.user;


import com.icss.newretail.model.*;
import com.icss.newretail.service.user.StoreDeliveryInfoService;
import io.swagger.annotations.ApiParam;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yanghu
 * @date 2020.04.24
 * 门店配送信息
 */
@RestSchema(schemaId = "StoreDeliveryInfo")
@RequestMapping(value = "/v1/StoreDeliveryInfo")
public class StoreDeliveryInfoApi {
    @Autowired
    private StoreDeliveryInfoService storeDeliveryInfoService;

    /**
     * 新增 门店配送信息
     *
     * @param storeDeliveryInfoDTO  门店配送信息DTO
     * @return
     */
    @PostMapping("addStoreDeliveryInfo")
    public ResponseBase addStoreDeliveryInfo(@RequestBody StoreDeliveryInfoDTO storeDeliveryInfoDTO) {
        return storeDeliveryInfoService.addStoreDeliveryInfo(storeDeliveryInfoDTO);
    }

    /**
     * 更新 门店配送信息
     * @param storeDeliveryInfoDTO 门店配送信息DTO
     * @return
     */
    @PostMapping("updateStoreDeliveryInfo")
    public ResponseBase updateStoreDeliveryInfo(@RequestBody StoreDeliveryInfoDTO storeDeliveryInfoDTO) {
        return storeDeliveryInfoService.updateStoreDeliveryInfo(storeDeliveryInfoDTO);
    }

    /**
     * 删除 门店配送信息
     * @param orgSeq  门店id
     * @return
     */
    @PostMapping("deleteStoreDeliveryInfo")
    public ResponseBase deleteStoreDeliveryInfo(@ApiParam(name = "orgSeq", value = "店铺id", required = true) String orgSeq) {
        return storeDeliveryInfoService.deleteStoreDeliveryInfo(orgSeq);
    }

    /**
     * 分页查询 门店配送信息DTO
     * @param page  分页参数
     */
    @PostMapping("queryStoreDeliveryInfo")
    public ResponseResultPage<StoreDeliveryInfoDTO> queryStoreDeliveryInfo(@RequestBody PageData<StoreDeliveryInfoDTO> page) {
        return storeDeliveryInfoService.queryStoreDeliveryInfo(page);
    }

    /**
     * 单个查询  门店配送信息
     * @param orgSeq  门店id
     */
    @GetMapping("queryStoreDeliveryInfoOne")
    public ResponseResult<StoreDeliveryInfoDTO> queryStoreDeliveryInfoOne(@ApiParam(name = "orgSeq", value = "店铺id", required = true) String orgSeq) {
        return storeDeliveryInfoService.queryStoreDeliveryInfoOne(orgSeq);
    }
}
