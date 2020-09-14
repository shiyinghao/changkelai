package com.icss.newretail.config.redis.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisLock {
	protected RedisTemplate<String, String> redisTemplate;
	/**
	 * 重试时间
	 */
	private static final int DEFAULT_ACQUIRY_RETRY_MILLIS = 100;
	/**
	 * 锁的后缀
	 */
	private static final String LOCK_SUFFIX = "_redis_lock";
	/**
	 * 锁的key
	 */
	private String lockKey;
	/**
	 * 锁超时时间，防止线程在入锁以后，防止阻塞后面的线程无法获取锁，要避免出现锁过期情况，业务处理时间必须在锁过期时间内
	 */
	private int expireMsecs = 60 * 1000;
	/**
	 * 线程获取锁的等待时间
	 */
	private int timeoutMsecs = 10 * 1000;
	/**
	 * 是否锁定标志
	 */
	private volatile boolean locked = false;

	/**
	 * 构造器
	 * 
	 * @param redisTemplate
	 * @param lockKey
	 *            锁的key
	 */
	public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey) {
		this.redisTemplate = redisTemplate;
		this.lockKey = lockKey + LOCK_SUFFIX;
	}

	/**
	 * 构造器
	 * 
	 * @param redisTemplate
	 * @param lockKey
	 *            锁的key
	 * @param timeoutMsecs
	 *            获取锁的超时时间
	 */
	public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey, int timeoutMsecs) {
		this(redisTemplate, lockKey);
		this.timeoutMsecs = timeoutMsecs;
	}

	/**
	 * 构造器
	 * 
	 * @param redisTemplate
	 * @param lockKey
	 *            锁的key
	 * @param timeoutMsecs
	 *            获取锁的超时时间
	 * @param expireMsecs
	 *            锁的有效期
	 */
	public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs) {
		this(redisTemplate, lockKey, timeoutMsecs);
		this.expireMsecs = expireMsecs;
	}

	public String getLockKey() {
		return lockKey;
	}

	/**
	 * 封装jedis方法
	 * 
	 * @param key
	 * @return
	 */
	private String get(final String key) {
		Object obj = redisTemplate.opsForValue().get(key);
		return obj != null ? obj.toString() : null;
	}

	/**
	 * 封装jedis方法
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	private boolean setNX(final String key, final String value) {
//		Jedis jedis = new Jedis();
//		jedis.set(key, value, "NX", "EX", timeoutMsecs);
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}
	
	/**
	 * 封装jedis方法
	 * 
	 * @param key
	 * @return
	 */
	private void expire(final String key) {
		redisTemplate.expire(key, timeoutMsecs, TimeUnit.MICROSECONDS);
	}

	/**
	 * 封装jedis方法
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	private String getSet(final String key, final String value) {
		Object obj = redisTemplate.opsForValue().getAndSet(key, value);
		return obj != null ? (String) obj : null;
	}

	/**
	 * 获取锁
	 * 
	 * @return 获取锁成功返回ture，超时返回false
	 * @throws InterruptedException
	 */
	public boolean lock() throws InterruptedException, Exception {
		int timeout = timeoutMsecs;
		while (timeout >= 0) {
			long expires = System.currentTimeMillis() + expireMsecs + 1;
			String expiresStr = String.valueOf(expires); // 锁到期时间
			if (this.setNX(lockKey, expiresStr)) {
				expire(lockKey);
				locked = true;
				return true;
			}
			// redis里key的时间
			String currentValue = this.get(lockKey);
			// 判断锁是否已经过期，过期则重新设置并获取（需要保证分布式环境下多台机器时间统一或差别不大，否则没有nginx本身过期时间支撑的话，这里可能有问题）
			if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {
				System.out.println("出现了获取锁过期");
				// 设置锁并返回旧值
				String oldValue = this.getSet(lockKey, expiresStr);
				// 比较锁的值，如果不一致则可能是其他锁已经修改了值并获取（这里虽然保证了第一个getSet的线程拿到了锁，但是多个线程同时getSet后redis里的值可能已经被改成任意一个值，释放锁需要释放掉这部分）
				if (oldValue != null && oldValue.equals(currentValue)) {
					expire(lockKey);
					locked = true;
					return true;
				}
			}
			timeout -= DEFAULT_ACQUIRY_RETRY_MILLIS;
			// 延时
			Thread.sleep(DEFAULT_ACQUIRY_RETRY_MILLIS);
		}
		if (timeout < 0) {
			throw new Exception("出现了获取锁超时");
		}
		return false;
	}

	/**
	 * 释放获取到的锁
	 */
	public void unlock() {
		// 当A线程执行到解锁这步之前，锁过期了。B线程因此重新获取到锁，那么A线程删除的可能就是B线程的锁。
		// 所以要避免出现锁过期情况，业务处理时间必须在锁过期时间内
		if (locked) {
			redisTemplate.delete(lockKey);
			locked = false;
		}
	}
}
