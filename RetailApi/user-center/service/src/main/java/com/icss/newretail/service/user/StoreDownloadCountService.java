package com.icss.newretail.service.user;

import com.icss.newretail.model.*;
import org.springframework.http.ResponseEntity;

/**
 * @author : yanghu
 * @date : Created in 2020/7/7 21:00
 * @description : 店铺下载排名查询服务接口类
 * @modified By :
 * @version: : 1.0.0
 */

public interface StoreDownloadCountService {
    ResponseResultPage<WarZoneDownloadCountDTO> getWarZoneCount(PageData<DownloadCountReq> pageData);

    ResponseResultPage<WarZoneDownloadCountDTO> getBase(PageData<DownloadCountReq> pageData);

    ResponseResultPage<WarZoneDownloadCountDTO> getStore(PageData<DownloadCountReq> pageData);

    ResponseEntity<byte[]> exportWarZoneDate(DownloadCountReq req);

    ResponseEntity<byte[]> exportBase(DownloadCountReq req);

    ResponseEntity<byte[]> exportStore(DownloadCountReq req);

    ResponseBase addCardAmount(String orgSeq, String type , String cardId);
}
