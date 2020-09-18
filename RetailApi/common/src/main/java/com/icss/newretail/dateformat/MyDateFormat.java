package com.icss.newretail.dateformat;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDateFormat extends DateFormat {
  private static final long serialVersionUID = 1L;

  private DateFormat dateFormat;

  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

  public MyDateFormat(DateFormat dateFormat) {
    this.dateFormat = dateFormat;
  }

  @Override
  public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
    return dateFormat.format(date, toAppendTo, fieldPosition);
  }

  @Override
  public Date parse(String source, ParsePosition pos) {
    Date date = null;
    try {
      date = simpleDateFormat.parse(source, pos);
    } catch (Exception e) {
      date = dateFormat.parse(source, pos);
    }
    return date;
  }

  @Override
  public Date parse(String source) throws ParseException {
    Date date = null;
    try {
      date = simpleDateFormat.parse(source); 
    } catch (Exception e) {
      date = dateFormat.parse(source);
    }
    return date;
  }

  @Override
  public Object clone() {
    Object format = dateFormat.clone();
    return new MyDateFormat((DateFormat) format);
  }
}
