package com.icss.newretail.api.user;


import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.UserMessageInfoDTO;
import com.icss.newretail.model.UserMessageReadRecordDTO;
import com.icss.newretail.service.user.UserMessageInfoService;
import com.icss.newretail.service.user.UserMessageReadRecordService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestSchema(schemaId = "userMessage")
@RequestMapping(path = "/v1/userMessage")
public class UserMessageApi {

    @Autowired
    private UserMessageInfoService userMessageInfoService;

    @Autowired
    private UserMessageReadRecordService userMessageReadRecordService;


    @PostMapping(path = "createUserMessage")
    public ResponseBase createUser(@RequestBody UserMessageInfoDTO userMessageInfoDTO) {
        return userMessageInfoService.createUserMessage(userMessageInfoDTO);
    }


    @PostMapping(path = "updateUserMessage")
    public ResponseBase updateUserMessage(@RequestBody UserMessageInfoDTO userMessageInfoDTO) {
        return userMessageInfoService.updateUserMessage(userMessageInfoDTO);
    }

    @PostMapping(path = "queryUserMessage")
    public ResponseResultPage<UserMessageInfoDTO> queryUserMessage(@RequestBody PageData<UserMessageInfoDTO> pageData) {
        ResponseResultPage<UserMessageInfoDTO> result = new ResponseResultPage<>();
        if (pageData != null && pageData.getCondition() != null) {
            return userMessageInfoService.queryUserMessage(pageData);
        } else {
            result.setCode(0);
            result.setMessage("传入的查询参数分页对象不能为空");
            return result;
        }
    }

    //查看详情
    @PostMapping(path = "queryUserMessageInfo")
    public ResponseResultPage<UserMessageReadRecordDTO> queryUserMessageInfo(@RequestBody PageData<UserMessageReadRecordDTO> pageData) {
        ResponseResultPage<UserMessageReadRecordDTO> result = new ResponseResultPage<>();
        if (pageData != null && pageData.getCondition().getMessageId() != null) {
            return userMessageInfoService.queryUserMessageInfo(pageData);
        } else {
            result.setCode(0);
            result.setMessage("传入的查询参数分页对象不能为空以及消息ID不能为空");
            return result;
        }
    }

    //商家端点击查看详情时 记录
    @PostMapping(path = "addUserMessageInfo")
    public ResponseBase addUserMessageInfo(@RequestBody UserMessageReadRecordDTO userMessageInfoDTO) {
        return userMessageInfoService.addUserMessageInfo(userMessageInfoDTO);
    }


}
