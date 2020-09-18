package com.icss.newretail.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * localdatetime类型反序列化
 *
 * @Author: jc
 * @CreateDate: 2019/10/25 10:58
 */
public class CustomJsonLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
	@Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		String dateStr = jsonParser.getText();
		LocalDateTime localDateTime = null;
		if (dateStr.indexOf("T") > -1){
			localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA));
		} else {
			localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
		}
		return localDateTime;
	}
}
