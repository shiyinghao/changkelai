/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.api.user;

import javax.validation.constraints.NotEmpty;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.WechatInfoDTO;
import com.icss.newretail.service.user.WechatInfoService;

import io.swagger.annotations.ApiParam;

/**
 * 微信相关配置服务
 * 
 * @author zhangzhijia
 * @date May 10, 2019
 */
@RestSchema(schemaId = "wechatInfo")
@RequestMapping(path = "/v1/wechatInfo")
public class WechatInfoApi {

  @Autowired
  private WechatInfoService wechatInfoService;

  /**
   * 根据orgSeq获取公众号信息
   * @param orgSeq
   * @return
   */
  @GetMapping("getWechatInfoByOrgSeq")
  public ResponseResult<WechatInfoDTO> getWechatInfoByOrgSeq(@ApiParam(name = "orgSeq", value = "组织编码") String orgSeq) {
    return wechatInfoService.getWechatInfoByOrgSeq(orgSeq);
  }

  /**
   * 根据corpId获取公众号信息
   */
  @GetMapping(path = "getWechatInfoByCorpId")
  public ResponseResult<WechatInfoDTO> getWechatInfoByCorpId(String corpId) {
    return wechatInfoService.getWechatInfoByCorpId(corpId);
  }

  /**
   * 根据corpCode获取公众号信息
   */
  @GetMapping(path = "getWechatInfoByCorpCode")
  public ResponseResult<WechatInfoDTO> getWechatInfoByCorpCode(String corpCode) {
    return wechatInfoService.getWechatInfoByCorpCode(corpCode);
  }

  /**
   * 根据state获取公众号信息
   */
  @GetMapping(path = "getWechatInfoByState")
  public ResponseResult<WechatInfoDTO> getWechatInfoByState(String state) {
    return wechatInfoService.getWechatInfoByState(state);
  }

  /**
   * 
   * @Title: getWxCardQrcode
   * @Description: 根据组织编码获取二维码（没有则生成二维码，有若过期则生成更新 返回二维码url）
   * @param @param orgSeq
   * @param @param faceId
   * @param @return    参数
   * @return ResponseResult<String>    返回类型
   * @throws
   */
  @GetMapping(path = "getWxCardQrcode")
  public ResponseResult<String> getWxCardQrcode(
      @ApiParam(name = "orgSeq", value = "组织编码", required = true) @NotEmpty(message = "组织编码不能为空") String orgSeq,
      @ApiParam(name = "faceId", value = "人脸id", required = false) String faceId) {
    return wechatInfoService.getWxCardQrcode(orgSeq, faceId);
  }
}
