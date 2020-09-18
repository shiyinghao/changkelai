package com.icss.newretail.service.user;

import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.WechatInfoDTO;

/**
 * <p>
 * 店铺公众号信息 服务类
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
public interface WechatInfoService {

  /**
   * 根据orgSeq获取公众号信息
   * @param orgSeq
   * @return
   */
  ResponseResult<WechatInfoDTO> getWechatInfoByOrgSeq(String orgSeq);

  /**
   * 根据corpId获取公众号信息
   */
  public ResponseResult<WechatInfoDTO> getWechatInfoByCorpId(String corpId);

  /**
   * 根据corpCode获取公众号信息
   */
  public ResponseResult<WechatInfoDTO> getWechatInfoByCorpCode(String corpCode);

  /**
   * 根据state获取公众号信息
   */
  public ResponseResult<WechatInfoDTO> getWechatInfoByState(String state);

  /**
   * 获取二维码url
   */
  public ResponseResult<String> getWxCardQrcode(String orgSeq, String faceId);

}
