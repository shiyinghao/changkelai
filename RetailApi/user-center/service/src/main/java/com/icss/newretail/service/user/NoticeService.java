package com.icss.newretail.service.user;

import java.util.List;

import com.icss.newretail.model.NoticeDTO;
import com.icss.newretail.model.NoticeRequest;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;

public interface NoticeService {

  public ResponseBase release(NoticeDTO notifyMsg);

  public ResponseBase createNotice(NoticeDTO notice);

  public ResponseBase modifyNotice(NoticeDTO notice);

  public ResponseBase deleteNotice(String noticeId);

  public ResponseResult<NoticeDTO> queryNoticeById(String noticeId);

  public ResponseResultPage<NoticeDTO> queryNotices(PageData<NoticeRequest> pageData);
  
  public ResponseBase releaseComm(NoticeDTO notifyMsg,int type,int pushmethod, List<String> orgSeqList);
  
}
