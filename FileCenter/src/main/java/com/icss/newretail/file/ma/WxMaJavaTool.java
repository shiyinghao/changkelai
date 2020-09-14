package com.icss.newretail.file.ma;

import java.util.concurrent.ConcurrentHashMap;

import org.redisson.api.RedissonClient;

import cn.binarywang.wx.miniapp.api.WxMaService;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName: WxJavaTool
 * @Description: 微信公众号配置
 */
public class WxMaJavaTool {
	private static ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
	/**
	 * 获取WxMaService
	 * @param appId
	 * @param secret
	 * @param jedisPool
	 * @param redissonClient
	 * @return
	 */
	public static WxMaService getWxMaService(String appId, String secret, JedisPool jedisPool, RedissonClient redissonClient) {
		WxMaService wxService = (WxMaService) concurrentHashMap.get(appId);
		if (wxService != null) {
			return wxService;
		}
		synchronized ("WxMaService:".concat(appId).intern()) {	
			wxService = (WxMaService) concurrentHashMap.get(appId);
			if (wxService != null) {
				return wxService;
			}	
			wxService = new WxMaServiceImplFix();
			wxService.setWxMaConfig(new WxMaRedisWithLockConfigImpl(appId, secret, jedisPool, redissonClient));
			concurrentHashMap.put(appId, wxService);
		}
		return wxService;
	}
}
