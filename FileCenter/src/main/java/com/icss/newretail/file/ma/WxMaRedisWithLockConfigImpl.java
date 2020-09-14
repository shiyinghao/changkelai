package com.icss.newretail.file.ma;

import cn.binarywang.wx.miniapp.config.impl.WxMaRedisConfigImpl;
import org.redisson.api.RedissonClient;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.locks.Lock;

/**
 * 基于Redis的微信配置provider. jedisPool连接池 redisson分布式锁
 */
public class WxMaRedisWithLockConfigImpl extends WxMaRedisConfigImpl {
	private RedissonClient redissonClient;
	private Lock accessTokenLock;
	private Lock jsapiTicketLock;
	private Lock cardApiTicketLock;

	public WxMaRedisWithLockConfigImpl(String appId, String secret,
			JedisPool jedisPool, RedissonClient redissonClient) {
		super.setJedisPool(jedisPool);
		super.setAppid(appId);
		super.setMaId(appId);
		super.setSecret(secret);
		this.redissonClient = redissonClient;
		this.accessTokenLock = this.redissonClient
				.getLock("wx:accessTokenLock:".concat(appId));
		this.jsapiTicketLock = this.redissonClient
				.getLock("wx:jsapiTicketLock:".concat(appId));
		this.cardApiTicketLock = this.redissonClient
				.getLock("wx:cardApiTicketLock:".concat(appId));
	}
	@Override
	public Lock getAccessTokenLock() {
		return accessTokenLock;
	}
	@Override
	public Lock getJsapiTicketLock() {
		return jsapiTicketLock;
	}
	@Override
	public Lock getCardApiTicketLock() {
		return cardApiTicketLock;
	}
}
