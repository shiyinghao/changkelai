package com.icss.newretail.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间序列化格式
 * 
 * @author shenjian
 * @date May 23, 2019
 */
public class CustomJsonDateSerializer extends JsonSerializer<Date> {
  @Override
  public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    String valueStr = format.format(value);
    gen.writeString(valueStr);
  }
}
