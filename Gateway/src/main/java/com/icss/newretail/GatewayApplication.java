package com.icss.newretail;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.icss.newretail.deserialize.CustomJsonDateDeserializer;
import com.icss.newretail.deserialize.CustomJsonLocalDateTimeDeserializer;
import com.icss.newretail.serialize.CustomJsonDateSerializer;
import com.icss.newretail.serialize.CustomJsonLocalDateTimeSerializer;
import org.apache.servicecomb.common.rest.codec.RestObjectMapperFactory;
import org.apache.servicecomb.springboot2.starter.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author jc
 * @date 2020/5/21 16:53
 */
@SpringBootApplication
@EnableServiceComb
public class GatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
		//日期序列化及反序列化
		SimpleModule module = new SimpleModule();
		module.addSerializer(Date.class, new CustomJsonDateSerializer());
		module.addSerializer(LocalDateTime.class, new CustomJsonLocalDateTimeSerializer());
		module.addDeserializer(Date.class, new CustomJsonDateDeserializer());
		module.addDeserializer(LocalDateTime.class, new CustomJsonLocalDateTimeDeserializer());
		RestObjectMapperFactory.getRestObjectMapper().registerModule(module);
		RestObjectMapperFactory.getConsumerWriterMapper().registerModule(module);
//		RestObjectMapperFactory.getConsumerWriterMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

	}
}

