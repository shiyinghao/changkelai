package com.icss.newretail.service.user;

import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.UserMessageInfoDTO;
import com.icss.newretail.model.UserMessageReadRecordDTO;

/**
 * <p>
 * 消息通知表 服务类
 * </p>
 *
 * @author syh
 * @since 2020-08-20
 */
public interface UserMessageInfoService  {

    ResponseBase createUserMessage(UserMessageInfoDTO userMessageInfoDTO);

    ResponseBase updateUserMessage(UserMessageInfoDTO userMessageInfoDTO);

    ResponseResultPage<UserMessageInfoDTO> queryUserMessage(PageData<UserMessageInfoDTO> pageData);

    ResponseResultPage<UserMessageReadRecordDTO> queryUserMessageInfo(PageData<UserMessageReadRecordDTO> pageData);

    ResponseBase addUserMessageInfo(UserMessageReadRecordDTO userMessageInfoDTO);
}
