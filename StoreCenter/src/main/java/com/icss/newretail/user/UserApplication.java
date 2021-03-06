package com.icss.newretail.user;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.icss.newretail.deserialize.CustomJsonDateDeserializer;
import com.icss.newretail.deserialize.CustomJsonLocalDateTimeDeserializer;
import com.icss.newretail.serialize.CustomJsonDateSerializer;
import com.icss.newretail.serialize.CustomJsonLocalDateTimeSerializer;
import org.apache.servicecomb.common.rest.codec.RestObjectMapperFactory;
import org.apache.servicecomb.springboot2.starter.EnableServiceComb;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author jc
 * @date 2020/5/25 10:38
 */
@SpringBootApplication(scanBasePackages={"com.icss.newretail"})
@EnableServiceComb
@MapperScan("com.icss.newretail.user.dao")
public class UserApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
		//日期序列化及反序列化
		SimpleModule module = new SimpleModule();
		module.addSerializer(Date.class, new CustomJsonDateSerializer());
		module.addSerializer(LocalDateTime.class, new CustomJsonLocalDateTimeSerializer());
		module.addDeserializer(Date.class, new CustomJsonDateDeserializer());
		module.addDeserializer(LocalDateTime.class, new CustomJsonLocalDateTimeDeserializer());
		RestObjectMapperFactory.getRestObjectMapper().registerModule(module);
		RestObjectMapperFactory.getConsumerWriterMapper().registerModule(module);
	}
}
