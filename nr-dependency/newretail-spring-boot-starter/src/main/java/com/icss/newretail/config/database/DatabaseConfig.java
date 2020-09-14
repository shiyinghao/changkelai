package com.icss.newretail.config.database;

import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jc
 * @date 2020/6/7 16:33
 */
@Configuration
public class DatabaseConfig {
	@Bean
	public WallConfig wallConfig() {
		WallConfig wallConfig = new WallConfig();
		wallConfig.setMultiStatementAllow(true);
		return wallConfig;
	}

	@Bean
	public WallFilter wallFilter() {
		WallFilter wallFilter = new WallFilter();
		wallFilter.setConfig(wallConfig());
		return wallFilter;
	}
}
