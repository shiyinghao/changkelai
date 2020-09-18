package com.icss.newretail.api.user;

import com.icss.newretail.model.*;
import com.icss.newretail.service.user.StoreDownloadCountService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.invocation.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

/**
 * @author : yanghu
 * @date : Created in 2020/7/7 21:01
 * @description : 统计门店活动名片和店铺名片下载次数，以及战区、基地下载排名情况。
 * @modified By :
 * @version: : 1.0.0
 */

@RestSchema(schemaId = "storeDownloadCount")
@RequestMapping(path = "/v1/storeDownloadCount")
public class StoreDownloadCountApi {

    @Autowired
    StoreDownloadCountService storeDownloadCountService;

    //战区查询

    @PostMapping("/getWarZoneCount")
    public ResponseResultPage<WarZoneDownloadCountDTO> getWarZoneCount(@RequestBody PageData<DownloadCountReq> pageData) {
        return storeDownloadCountService.getWarZoneCount(pageData);
    }


    //基地查询
    @PostMapping("/getBase")
    public ResponseResultPage<WarZoneDownloadCountDTO> getBase(@RequestBody PageData<DownloadCountReq> pageData) {
        return storeDownloadCountService.getBase(pageData);
    }

    //门店查询
    @PostMapping("/getStore")
    public ResponseResultPage<WarZoneDownloadCountDTO> getStore(@RequestBody PageData<DownloadCountReq> pageData) {
        return storeDownloadCountService.getStore(pageData);
    }


    //导出战区数据

    @PostMapping("/exportWarZoneDate")
    @ApiResponses({
            @ApiResponse(code = 200, response = File.class, message = "")
    })
    public ResponseEntity<byte[]> exportWarZoneDate(@RequestBody DownloadCountReq req) {
        return storeDownloadCountService.exportWarZoneDate(req);
    }

    //导出战区数据

    @PostMapping("/exportBase")
    @ApiResponses({
            @ApiResponse(code = 200, response = File.class, message = "")
    })
    public ResponseEntity<byte[]> exportBase(@RequestBody DownloadCountReq req) {
        return storeDownloadCountService.exportBase(req);
    }
    //导出战区数据

    @PostMapping("/exportStore")
    @ApiResponses({
            @ApiResponse(code = 200, response = File.class, message = "")
    })
    public ResponseEntity<byte[]> exportStore(@RequestBody DownloadCountReq req) {
        return storeDownloadCountService.exportStore(req);
    }


    @PostMapping("/addCardAmount")
    //type 1活动名片   2商品名片    3店铺名片
    public ResponseBase  addCardAmount(@RequestParam String orgSeq,@RequestParam String type ,@RequestParam(required = false) String cardId) {
        return storeDownloadCountService.addCardAmount(orgSeq,type,cardId);
    }

}
