package com.icss.newretail.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

  private static final String[] weekDays =
      {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"}; //星期数字

  private static final String[] weekDayNames = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"}; //星期名称

  public static void main(String[] args) {

//    System.out.println(DateUtils.now("YYYYMMdd"));
//    System.out.println(DateUtils.now("HH:mm:ss"));
//    System.out.println(DateUtils.getDayOfMonth("2019-05-22"));
	  int weekNum = DateUtils.getWeekByYear("2020-01-01");
	  System.out.println("2020-12-31".substring(5, 7));
	  System.out.println(weekNum);
	  System.out.println(DateUtils.getBegDateByWeekCount("2020", weekNum));
	  System.out.println(DateUtils.getEndDateByWeekCount("2020", weekNum));
  }

  /**
   * 取当前时间
   * 
   * @param model
   * @return
   */
  public static String now(String model) {
    LocalDateTime time = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(model); // 格式化输出
    return time.format(formatter);
  }


  /*
   * 格式化传入的日期/时间
   */
  public static String formatTime(String time, String model) {
    SimpleDateFormat formatterTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatter = new SimpleDateFormat(model);
    String timeFormatter = "";
    try {
      timeFormatter = formatter.format(formatterTime.parse(time));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return timeFormatter;
  }

  public static String formatTime(Date time, String model) {
    SimpleDateFormat formatter = new SimpleDateFormat(model);
    String timeFormatter = "";
    try {
      timeFormatter = formatter.format(time);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return timeFormatter;
  }

  /**
   * 时间格式化（毫秒数）
   * 
   * @param time
   * @param model
   * @return
   */
  public static String formatTime(Long time, String model) {
    SimpleDateFormat formatter = new SimpleDateFormat(model);
    String timeFormatter = "";
    try {
      timeFormatter = formatter.format(time);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return timeFormatter;
  }

  /**
   * 时间格式化（字符串）
   * 
   * @param time
   * @param inModel
   * @param outModel
   * @return
   */
  public static String formatTime(String time, String inModel, String outModel) {
    SimpleDateFormat formatterTime = new SimpleDateFormat(inModel);
    SimpleDateFormat formatter = new SimpleDateFormat(outModel);
    String timeFormatter = "";
    try {
      timeFormatter = formatter.format(formatterTime.parse(time));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return timeFormatter;
  }

  /**
   * 字符转Date
   * 
   * @param strDate 时间字符串
   * @param model 格式
   * @return
   */
  public static Date strToDate(String strDate, String model) {
    SimpleDateFormat formatter = new SimpleDateFormat(model);
    formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }

  /**
   * 获取月天序号（1-31）
   * 
   * @param date yyyy-MM-dd
   * @return
   */
  public static String getDayOfMonth(String date) {
    if (ToolUtil.notEmpty(date)) {
      return String.valueOf(LocalDate.parse(date).getDayOfMonth());
    } else {
      return "";
    }

  }

  /**
   * 获取星期序号（MONDAY/TUESDAY/WEDNESDAY/THURSDAY/FRIDAY/SATURDAY/SUNDAY）
   * 
   * @param date
   * @return
   */
  public static String getDayOfWeek(String date) {
    if (ToolUtil.notEmpty(date)) {
      return LocalDate.parse(date).getDayOfWeek().toString();
    } else {
      return "";
    }
  }


  /**
   * 星期序号转化为中文星期
   * 
   * @param days
   * @return
   */
  public static String getWeekDays(String days) {
    String dayNames = "";
    if (days != null && days.length() > 0) {
      for (int i = 0; i < weekDays.length; i++) {
        dayNames = days.replaceAll(weekDays[i], weekDayNames[i]);
      }
    }
    return dayNames;
  }

  /**
   * @desc 取当前时间
   * @param modle:yyyyMM;yyyyMMdd;yyyy-MM-dd HH:mm:ss;
   * @return date
   */
  public static String getCurrDate(String model) {
    String date = "";
    try {
      Date dt = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat(model);
      formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
      date = formatter.format(dt);
    } catch (Exception e) {
      e.printStackTrace();
      date = "";
    }
    return date;
  }

  /**
   * @desc 根据数值取相对于当前月份的月份数据
   * @param i:0:当月，-1：上月，1：下个月
   * @param modle:yyyyMM;yyyyMMdd;yyyy-MM-dd HH:mm:ss;
   * @return date
   */
  public static String getMonthByNum(String model, int i) {
    String strMonth = "";
    try {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.MONTH, i);
      SimpleDateFormat df = new SimpleDateFormat(model);
      df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
      strMonth = df.format(calendar.getTime());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return strMonth;
  }

  /**
   * @desc 根据数值取相对于当前日期的日期数据
   * @param i:0:当天，-1：昨天，1：明天
   * @param modle:yyyyMM;yyyyMMdd;yyyy-MM-dd
   *            HH:mm:ss;
   * @return date
   */
  public static String getDayByNum(String model, int i) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, i);
    SimpleDateFormat df = new SimpleDateFormat(model);
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    String s = df.format(calendar.getTime());
    return s;
  }

  // 获取当前时间是第几周
  public static int getWeekByYear(String dateQuey) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    Date date = new Date();
    try {
      date = df.parse(dateQuey);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    int weekCount = 0;
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.setTime(date);
    weekCount = cal.get(Calendar.WEEK_OF_YEAR);
    return weekCount;
  }

  // 获取当前时间是第几周-1
  public static int getBeforeWeekByYear() {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    Date date = new Date();
    int weekCount = 0;
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.setTime(date);
    weekCount = cal.get(Calendar.WEEK_OF_YEAR);
    if (weekCount > 1) {
      weekCount = weekCount - 1;
    }
    return weekCount;
  }


  // 查询周数的开始时间
  public static String getBegTimeByWeekCount(String year, int weekCount) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.set(Calendar.YEAR, Integer.parseInt(year));
    cal.set(Calendar.WEEK_OF_YEAR, weekCount);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    String beginTime = df.format(cal.getTime());
    return beginTime + " 00:00:00";
  }

  // 查询周数的开始日期
  public static String getBegDateByWeekCount(String year, int weekCount) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.set(Calendar.YEAR, Integer.parseInt(year));
    cal.set(Calendar.WEEK_OF_YEAR, weekCount);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    String beginDate = df.format(cal.getTime());
    return beginDate;
  }

  // 查询周数的结束时间
  public static String getEndTimeByWeekCount(String year, int weekCount) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.set(Calendar.YEAR, Integer.parseInt(year));
    cal.set(Calendar.WEEK_OF_YEAR, weekCount);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    String endTime = df.format(cal.getTime());
    return endTime + " 23:59:59";
  }

  // 查询周数的结束日期
  public static String getEndDateByWeekCount(String year, int weekCount) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.set(Calendar.YEAR, Integer.parseInt(year));
    cal.set(Calendar.WEEK_OF_YEAR, weekCount);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    String endDate = df.format(cal.getTime());
    return endDate;
  }

  // 获取当前时间是第几周(哪个月的第几周)
  public static int getWeekCountOfMonthByCurDate(String dateQuey) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    Date date = new Date();
    try {
      date = df.parse(dateQuey);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    int weekCount = 0;
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.setTime(date);
    weekCount = cal.get(Calendar.WEEK_OF_MONTH);
    return weekCount;
  }

  // 查询周数的开始日期
  public static String getBegDateByWeekCountOfMonth(String year, String month, int weekCount) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.set(Calendar.YEAR, Integer.parseInt(year));
    cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
    cal.set(Calendar.WEEK_OF_MONTH, weekCount);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    String beginDate = df.format(cal.getTime());
    if (Integer.valueOf(df.format(cal.getTime()).substring(5, 7)).intValue() != Integer.valueOf(month).intValue()) {// 最后一天不能跨月
      beginDate = year + "-" + month + "-01";
    } else {
      beginDate = df.format(cal.getTime());
    }
    return beginDate;
  }

  // 查询周数的结束日期
  public static String getEndDateByWeekCountOfMonth(String year, String month, int weekCount) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    Calendar cal = Calendar.getInstance(Locale.CHINA);
    // 获取该月份共多少天
    cal.set(Calendar.YEAR, Integer.valueOf(year));
    cal.set(Calendar.MONTH, Integer.valueOf(month) - 1);//
    int daycount = cal.getActualMaximum(Calendar.DATE);
    // 获取该月最后一天日期
    cal.set(Calendar.DATE, daycount);
    String lastDate = df.format(cal.getTime());
    // 处理其他
    cal.setFirstDayOfWeek(Calendar.MONDAY);
    cal.set(Calendar.YEAR, Integer.parseInt(year));
    cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
    cal.set(Calendar.WEEK_OF_MONTH, weekCount);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    String endDate = df.format(cal.getTime());
    if (Integer.valueOf(df.format(cal.getTime()).substring(5, 7)).intValue() != Integer.valueOf(month).intValue()) {// 最后一天不能跨月
      endDate = lastDate;
    } else {
      endDate = df.format(cal.getTime());
    }
    return endDate;
  }

  /*
   * 两日期相减差多少天
   */
  public static int daysBetween(String smdate, String bdate) {
    long between_days = 0l;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Calendar cal = Calendar.getInstance();
      cal.setTime(sdf.parse(smdate));
      long time1 = cal.getTimeInMillis();
      cal.setTime(sdf.parse(bdate));
      long time2 = cal.getTimeInMillis();
      between_days = (time2 - time1) / (1000 * 3600 * 24);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Integer.parseInt(String.valueOf(between_days));
  }

  /*
   * 格式化传入的日期/时间
   */
  public static Date parseTime(String time, String model) {
    SimpleDateFormat formatter = new SimpleDateFormat(model);
    Date dateResult = null;
    try {
      dateResult = formatter.parse(time);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dateResult;
  }

  /*
   * 两个时间相减，剩余多少秒
   */
  public static int getSecondBetweenTimes(String begTime, String endTime) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    int count = 0;
    try {
      long from = df.parse(begTime).getTime();
      long to = df.parse(endTime).getTime();
      long l = (to - from) / 1000;
      count = Long.valueOf(l).intValue();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return count;
  }


  // 获取传入日期的i个月前的日期
  public static String getDateBeforeCountMonth(String day, int i) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    Date date = new Date();
    try {
      date = df.parse(day);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    String dayBeforeCountMonth = "";
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MONTH, -i);
    dayBeforeCountMonth = df.format(cal.getTime());
    return dayBeforeCountMonth;
  }

  /**
   * 
   * @Title: Date2LocalDateTime
   * @Description: Date转localDatetime
   * @param @param date
   * @param @return    参数
   * @return LocalDateTime    返回类型
   * @throws
   */
  public static LocalDateTime Date2LocalDateTime(Date date) {
	  Instant instant = date.toInstant();
	  ZoneId zoneId = ZoneId.systemDefault();
	  return instant.atZone(zoneId).toLocalDateTime();
  }
  
  /**
	 * 根据传入时间字符串获得需要格式的三种加减时间串
	 * @param strDate 时间字符串
	 * @param model 格式
	 * @param temp 加减项
	 * @param i 加减量
	 * @return
	 */
	public static String getDateByStrNum(String strDate, String model, int temp, int i) {
		SimpleDateFormat formatter = new SimpleDateFormat(model);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		Calendar cal = Calendar.getInstance();
		cal.setTime(strtodate);
		cal.add(temp, i);
		return formatter.format(cal.getTime());
	}
	
	/**
	 * 查询日期的所属月月尾日期
	 * @param strDate 时间字符串
	 * @param model 格式
	 * @return
	 */
	public static String getDateMonthEnd(String strDate, String model) {
		SimpleDateFormat formatter = new SimpleDateFormat(model);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		Calendar cal = Calendar.getInstance();
		cal.setTime(strtodate);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return formatter.format(cal.getTime());
	}

}
