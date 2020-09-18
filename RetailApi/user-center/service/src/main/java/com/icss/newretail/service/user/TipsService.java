package com.icss.newretail.service.user;

import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.TipsDTO;
import com.icss.newretail.model.TipsRequest;

public interface TipsService {
  
  public ResponseResult<TipsDTO> getTips(String appId);

  public ResponseBase disagreeTips(String tipsId);

  public ResponseBase agreeTips(String tipsId);

  public ResponseBase createTips(TipsDTO device);

  public ResponseBase modifyTips(TipsDTO device);

  public ResponseBase deleteTips(String tipsId);
  
  public ResponseResult<TipsDTO> queryTipsById(String deviceId);

  public ResponseResultPage<TipsDTO> queryTips(PageData<TipsRequest> pageData);
}
