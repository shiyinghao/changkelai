/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.WechatInfoDTO;
import com.icss.newretail.service.user.WechatInfoService;
import com.icss.newretail.user.dao.WechatInfoMapper;
import com.icss.newretail.user.dao.WechatOrgCardMapper;
import com.icss.newretail.user.entity.WechatInfo;
import com.icss.newretail.util.Object2ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 店铺公众号信息 服务实现类
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
@Service
@Slf4j
public class WechatInfoServiceImpl implements WechatInfoService {
  @Autowired
  private WechatInfoMapper wechatInfoMapper;

  @Autowired
  private WechatOrgCardMapper wechatOrgCardMapper;



  @Override
  public ResponseResult<WechatInfoDTO> getWechatInfoByOrgSeq(String orgSeq) {
    ResponseResult<WechatInfoDTO> responseResult = new ResponseResult<>();
    try {
      WechatInfoDTO wechatInfoDTO = wechatInfoMapper.getWechatInfoByOrgSeq(orgSeq);
      responseResult.setCode(1);
      responseResult.setMessage("根据orgSeq获取公众号信息成功");
      responseResult.setResult(wechatInfoDTO);
    } catch (Exception e) {
      responseResult.setMessage(e.getMessage());
      log.error("根据orgSeq获取公众号信息异常---" + e.getMessage());
    }
    return responseResult;
  }

  /**
   * 根据corpId获取公众号信息
   */
  @Cacheable(value = "userServiceImplCache", key = "#root.methodName+#p0")
  public ResponseResult<WechatInfoDTO> getWechatInfoByCorpId(String corpId) {
    WechatInfo wechatInfo = wechatInfoMapper.selectOne(new QueryWrapper<WechatInfo>().eq("corp_id", corpId));
    WechatInfoDTO wechatInfoDTO = Object2ObjectUtil.parseObject(wechatInfo, WechatInfoDTO.class);
    ResponseResult<WechatInfoDTO> responseResult = new ResponseResult<WechatInfoDTO>();
    responseResult.setCode(1);
    responseResult.setResult(wechatInfoDTO);
    return responseResult;
  }

  /**
   * 根据corpCode获取公众号信息
   */
  @Cacheable(value = "userServiceImplCache", key = "#root.methodName+#p0")
  public ResponseResult<WechatInfoDTO> getWechatInfoByCorpCode(String corpCode) {
    WechatInfo wechatInfo = wechatInfoMapper.selectOne(new QueryWrapper<WechatInfo>().eq("corp_code", corpCode));
    WechatInfoDTO wechatInfoDTO = Object2ObjectUtil.parseObject(wechatInfo, WechatInfoDTO.class);
    ResponseResult<WechatInfoDTO> responseResult = new ResponseResult<WechatInfoDTO>();
    responseResult.setCode(1);
    responseResult.setResult(wechatInfoDTO);
    return responseResult;
  }

  /**
   * 根据state获取公众号信息
   */
  @Cacheable(value = "userServiceImplCache", key = "#root.methodName+#p0")
  public ResponseResult<WechatInfoDTO> getWechatInfoByState(String state) {
    WechatInfo wechatInfo = wechatInfoMapper.selectOne(new QueryWrapper<WechatInfo>().eq("corp_key", state));
    WechatInfoDTO wechatInfoDTO = Object2ObjectUtil.parseObject(wechatInfo, WechatInfoDTO.class);
    ResponseResult<WechatInfoDTO> responseResult = new ResponseResult<WechatInfoDTO>();
    responseResult.setCode(1);
    responseResult.setResult(wechatInfoDTO);
    return responseResult;
  }

  /**
   * 获取公众号店铺二维码
   */
  @Override
  public ResponseResult<String> getWxCardQrcode(String orgSeq, String faceId) {
    ResponseResult<String> responseResult = new ResponseResult<String>();

    return responseResult;
  }

//  @Override
//  public String getWechatInfoByUserId(String shareId) {
//    return wechatInfoMapper.getWechatInfoByUserId(shareId);
//  }
//
//  @Override
//  public String getorgSeqByUserId(String shareId) {
//    return  wechatInfoMapper.getorgSeqByUserId(shareId);
//  }


}
