package com.icss.newretail.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 日期时间序列化格式
 * 
 * 
 * @author shenjian
 * @date May 23, 2019
 */
public class CustomJsonLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
  @Override
  public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    String valueStr = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
    gen.writeString(valueStr);
  }
}
