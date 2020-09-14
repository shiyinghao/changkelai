package com.icss.newretail.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import lombok.Data;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @description: RedisPool简单使用
 **/
@Configuration
//@ConfigurationProperties(prefix = "spring.redis.jedis")
@Data
public class JedisConfig {
	@Autowired
	private JedisPoolConfig poolConfig;

	@Value("${spring.redis.host}")
	private String hostName;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;
	@Bean
	public JedisPool jedisPool() {
		JedisPool jedisPool;
		if (!StringUtils.isEmpty(password)) {
			jedisPool = new JedisPool(poolConfig, hostName, port, timeout, password);
		} else {
			jedisPool = new JedisPool(poolConfig, hostName, port, timeout);
		}
		return jedisPool;
	}
}

@Configuration
class PoolConfig {
	@Value("${spring.redis.jedis.pool.min-idle}")
	private int minIdle;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.jedis.pool.max-total}")
	private int maxTotal;

	@Bean
	public JedisPoolConfig poolConfig2() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMinIdle(minIdle);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxTotal(maxTotal);
		return jedisPoolConfig;
	}
}