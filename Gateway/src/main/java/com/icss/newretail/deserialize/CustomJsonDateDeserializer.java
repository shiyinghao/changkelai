package com.icss.newretail.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        String dateStr = jsonParser.getText();
        Date date = null;
        try {
            if (dateStr.indexOf("T") > -1) {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA).parse(dateStr);
                return date;
            } else if (dateStr.length() > 10) {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse(dateStr);
            } else {
                date = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(dateStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
