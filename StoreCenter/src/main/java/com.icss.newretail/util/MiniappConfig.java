package com.icss.newretail.util;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;

import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.WechatInfoDTO;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;

public class MiniappConfig {
	private static ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

	/**
	 * 根据corpId获取WechatInfo
	 * 
	 * @param appId
	 * @param secret
	 * @return
	 */
	@Cacheable(value = "MiniappConfigCache", key = "#root.methodName+#p0")
	public static WechatInfoDTO getWechatInfo(String appId) {
		HashMap<String, Object> query = new HashMap<String, Object>();
		query.put("corpId", appId);
		RestTemplate restTemplate = RestTemplateBuilder.create();
		ResponseResult<WechatInfoDTO> wechatInfoDTOResult = restTemplate
				.getForObject(
						"cse://user-service/v1/wechatInfo/getWechatInfoByCorpId?corpId={1}",
						ResponseResult.class, appId);
		WechatInfoDTO wechatInfoDTO = wechatInfoDTOResult.getResult();
		return wechatInfoDTO;
	}

	/**
	 * 根据corpId获取WxMaConfig
	 * 
	 * @param appId
	 * @param secret
	 * @return
	@Cacheable(value = "MiniappConfigCache", key = "#root.methodName+#p0+#p1")
	public static WxMaInMemoryConfig getWxMaConfig(String appId,
			String secret) {
		WxMaInMemoryConfig config = new WxMaInMemoryConfig();
		config.setAppid(appId);
		config.setSecret(secret);
		return config;
	}
	 */

	/**
	 * 根据corpId获取wxMaService
	 * 
	 * @param appId
	 * @param secret
	 * @return
	public static WxMaService getWxMaService(String appId, String secret) {
		WxMaService wxMaService = (WxMaService) concurrentHashMap.get(appId);
		if (wxMaService == null) {
			WxMaInMemoryConfig config = getWxMaConfig(appId, secret);
			wxMaService = new WxMaServiceImpl();
			wxMaService.setWxMaConfig(config);
			concurrentHashMap.put(appId, wxMaService);
		}
		return wxMaService;
	}
	 */

	/**
	 * 根据corpId获取wxMaService
	 * 
	 * @param appId
	 * @param secret
	 * @return

	public static WxMaService getWxMaService(String appId) {
		WechatInfoDTO wechatInfoDTO = getWechatInfo(appId);
		return getWxMaService(wechatInfoDTO.getCorpId(),
				wechatInfoDTO.getCorpSecret());
	}
	 */
}
