package com.icss.newretail.api.user;

import com.icss.newretail.model.FeedbackDTO;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.service.user.FeedbackService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestSchema(schemaId = "feedbackapi")
@RequestMapping(path = "/v1/feedback")
public class FeedbackApi {

    @Autowired
    private FeedbackService feedbackService;

    /**
     *  新增反馈问题
     *
     * @return
     */
    @PostMapping(path = "addFeedback")
    public ResponseBase addFeedback(@RequestBody FeedbackDTO para) {
        return feedbackService.addFeedback(para);
    }

    /**
     * 门店查询当前反馈的问题 传参 门店orgSeq
     *
     * @return
     */
    @PostMapping(path = "queryFeedback")
    public ResponseResultPage<FeedbackDTO> queryFeedback(@RequestBody PageData<FeedbackDTO> para) {
        return feedbackService.queryFeedback(para);
    }

    /**
     * 根据反馈uuid查出明细 带出门店信息
     * @param uuid
     * @return
     */
    @GetMapping(path = "queryFeedbackInfoById")
    public ResponseResult<FeedbackDTO> queryFeedbackInfoById(@ApiParam(name = "uuid", value = "反馈id", required = true) String uuid) {
        ResponseResult<FeedbackDTO> result = new ResponseResult<>();
        if (StringUtils.isBlank(uuid)) {
            result.setCode(0);
            result.setMessage("传入的参数不能为空");
            return result;
        }
        return feedbackService.queryFeedbackInfoById(uuid);
    }


    /**
     *  修改反馈问题
     *
     * @return
     */
    @PostMapping(path = "updateFeedbackById")
    public ResponseBase updateFeedbackById(@RequestBody FeedbackDTO para) {
        return feedbackService.updateFeedbackById(para);
    }

}
