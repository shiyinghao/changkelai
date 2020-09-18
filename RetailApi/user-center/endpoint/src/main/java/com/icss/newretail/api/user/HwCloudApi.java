package com.icss.newretail.api.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.extend.annotations.ResponseHeaders;
import org.apache.servicecomb.swagger.invocation.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icss.newretail.model.HwCloudResponseBase;
import com.icss.newretail.service.user.HwCloudService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;


@RestSchema(schemaId = "HwCloudApi")
@RequestMapping(path = "/v1/hwCloud")
public class HwCloudApi {
  @Autowired
  private HwCloudService hwCloudService;

  /**
   * 华为云市场回调消息处理
   * 
   * @param request
   * @return
   */
  @GetMapping(path = "saasproduce")
  @ApiResponses({
      @ApiResponse(code = 200, response = HwCloudResponseBase.class, message = "")})
  @ResponseHeaders({@ResponseHeader(name = "Body-Sign", response = String.class)})
  public Response saasproduce(HttpServletRequest request) {
    return hwCloudService.saasproduce(request);
  }

}
