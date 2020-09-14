package com.icss.newretail.config.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class RedissonConfig {
	@Value("${spring.redis.host}")
	private String hostName;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.timeout}")
	private int timeout;
	@Bean
	public RedissonClient getClient() {
		Config config = new Config();
		SingleServerConfig singleServerConfig = config.useSingleServer();
		singleServerConfig.setAddress(String.format("redis://%s:%s", hostName, port));
		if (!StringUtils.isEmpty(password)) {
			singleServerConfig.setPassword(password);
		}
		return Redisson.create(config);
	}
}
