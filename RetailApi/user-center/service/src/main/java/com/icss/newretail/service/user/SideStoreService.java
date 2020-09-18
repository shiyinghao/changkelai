package com.icss.newretail.service.user;

import com.icss.newretail.model.*;

public interface SideStoreService {

    ResponseResult<RecommendStoreDTO> recommend(ConsumerInfoRequest consumerInfoRequest);

    ResponseResultPage<RecommendStoreDTO> footPrint(FootPrintRequest footPrintRequest);

    ResponseResultPage<RecommendStoreDTO> searchStore(SearchStoreRequest searchStoreRequest);
}
