package com.icss.newretail.service.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.servicecomb.swagger.invocation.Response;

public interface HwCloudService {
  /**
   * 华为云市场回调接口
   * 详见 https://support.huaweicloud.com/accessg-marketplace/saas_topic_0000001.html
   * @return
   */
  public Response saasproduce(HttpServletRequest request);
}
