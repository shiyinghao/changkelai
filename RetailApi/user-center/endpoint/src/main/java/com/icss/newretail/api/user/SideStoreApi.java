package com.icss.newretail.api.user;

import com.icss.newretail.model.*;
import com.icss.newretail.service.user.SideStoreService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * 身边专卖店(五粮液小程序首页入口)
 */
@RestSchema(schemaId = "sideStore")
@RequestMapping(path = "/v1/sideStore")
public class SideStoreApi {

    @Autowired
    private SideStoreService sideStoreService;

    /**
     * 为你推荐接口
     *
     * @param consumerInfoRequest 会员Id,经度,纬度,半径范围
     * @return
     * @Author wangyao
     */
    @PostMapping(path = "recommend")
    public ResponseResult<RecommendStoreDTO> recommend(@RequestBody @Valid ConsumerInfoRequest consumerInfoRequest) {
        return sideStoreService.recommend(consumerInfoRequest);
    }

    /**
     * 足迹接口
     *
     * @return
     * @Author wangyao
     */
    @PostMapping(path = "footPrint")
    public ResponseResultPage<RecommendStoreDTO> footPrint(@RequestBody @Valid FootPrintRequest footPrintRequest) {
        return sideStoreService.footPrint(footPrintRequest);
    }

    /**
     * 模糊搜索 --足迹表中的门店
     * wangyao
     * @param searchStoreRequest
     * @return
     */
    @PostMapping(path="searchStore")
    public ResponseResultPage<RecommendStoreDTO> searchStore(@RequestBody @Valid SearchStoreRequest searchStoreRequest){
        return sideStoreService.searchStore(searchStoreRequest);
    }
}