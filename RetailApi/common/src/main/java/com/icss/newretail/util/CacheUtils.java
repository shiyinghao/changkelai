package com.icss.newretail.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class CacheUtils {


  public static String getCache(String key){
    String value ="";
    RestTemplate restTemplate = RestTemplateBuilder.create();
    String serviceName = "file-service";
    Object responseResult = restTemplate.getForObject("cse://" + serviceName + "/v1/core/getUnitNameFromCache?key=" + key, Object.class);
    if (responseResult != null) {
      String jsonStr = JSONObject.toJSONString(responseResult);
      JSONObject jsonObject = JSONObject.parseObject(jsonStr);
      if (jsonObject.getInteger("code") == 1) {
        value = jsonObject.getString("result");
      }
    }
//    return coreService.getUnitNameFromCache(key).getResult();
    return value;
  }

  public static String getCache(String key,String token){
    String value ="";
    RestTemplate restTemplate = RestTemplateBuilder.create();
    HttpHeaders headers = new HttpHeaders();
    headers.set("token",token);
    String serviceName = "file-service";
    HttpEntity httpEntity = new HttpEntity(headers);
    ResponseEntity<Object> request = restTemplate.getForEntity("cse://" + serviceName + "/v1/core/getUnitNameFromCache?key=" + key, Object.class,httpEntity);
    if (request != null) {
      String jsonStr = JSONObject.toJSONString(request.getBody());
      JSONObject jsonObject = JSONObject.parseObject(jsonStr);
      if (jsonObject.getInteger("code") == 1) {
        value = jsonObject.getString("result");
      }
    }
//    return coreService.getUnitNameFromCache(key).getResult();
    return value;
  }
}
