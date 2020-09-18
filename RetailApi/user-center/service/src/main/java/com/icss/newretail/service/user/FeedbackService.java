package com.icss.newretail.service.user;

import com.icss.newretail.model.FeedbackDTO;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;

public interface FeedbackService {
    ResponseBase addFeedback(FeedbackDTO para);

    ResponseResultPage<FeedbackDTO> queryFeedback(PageData<FeedbackDTO> para);

    ResponseResult<FeedbackDTO> queryFeedbackInfoById(String uuid);

    ResponseBase updateFeedbackById(FeedbackDTO para);
}
