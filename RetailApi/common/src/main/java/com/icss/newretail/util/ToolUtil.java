/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.icss.newretail.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * 高频方法集合类
 */
public class ToolUtil {

  /**
   * 获取异常的具体信息
   *
   * @author fengshuonan
   * @Date 2017/3/30 9:21
   * @version 2.0
   */
  public static String getExceptionMsg(Exception e) {
    StringWriter sw = new StringWriter();
    try {
      e.printStackTrace(new PrintWriter(sw));
    } finally {
      try {
        sw.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    return sw.getBuffer().toString().replaceAll("\\$", "T");
  }

  /**
   * @Description 主键id
   * @author fengshuonan
   */
  public static String getUid() {
    return getRandomNum();
  }

  /**
   * @Description 随机数字
   * @author fengshuonan
   */
  public static String getRandomNum() {
    return Calendar.getInstance().getTimeInMillis() + generateCellPhoneValNum();
  }

  /**
   * @Description 获取电话号码
   * @author fengshuonan
   */
  public static String generateCellPhoneValNum() {
    String[] beforeShuffle = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    List<String> list = Arrays.asList(beforeShuffle);
    Collections.shuffle(list);
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      buffer.append(list.get(i));
    }
    String afterShuffle = buffer.toString();
    String result = afterShuffle.substring(3, 9);
    return result;
  }
  /**
   * 生成随机码 
   *
   * @param length 位数
   * @return rev 
   */
  public static String getRandString(int length)
  {
      String charList = "0123456789";
      String rev = "";
      Random f = new Random();
      for(int i=0;i<length;i++)
      {
         rev += charList.charAt(Math.abs(f.nextInt())%charList.length());
      }
      return rev;
  }
  
  /**
   * 比较两个对象是否相等。<br>
   * 相同的条件有两个，满足其一即可：<br>
   * 1. obj1 == null && obj2 == null; 2. obj1.equals(obj2)
   *
   * @param obj1
   * @param obj2
   * @return 是否相等
   */
  public static boolean equals(Object obj1, Object obj2) {
    return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
  }

  /**
   * 计算对象长度，如果是字符串调用其length函数，集合类调用其size函数，数组调用其length属性，其他可遍历对象遍历计算长度
   *
   * @param obj被计算长度的对象
   * 
   * @return 长度
   */
  public static int length(Object obj) {
    if (obj == null) {
      return 0;
    }
    if (obj instanceof CharSequence) {
      return ((CharSequence) obj).length();
    }
    if (obj instanceof Collection) {
      return ((Collection<?>) obj).size();
    }
    if (obj instanceof Map) {
      return ((Map<?, ?>) obj).size();
    }

    int count;
    if (obj instanceof Iterator) {
      Iterator<?> iter = (Iterator<?>) obj;
      count = 0;
      while (iter.hasNext()) {
        count++;
        iter.next();
      }
      return count;
    }
    if (obj instanceof Enumeration) {
      Enumeration<?> enumeration = (Enumeration<?>) obj;
      count = 0;
      while (enumeration.hasMoreElements()) {
        count++;
        enumeration.nextElement();
      }
      return count;
    }
    if (obj.getClass().isArray() == true) {
      return Array.getLength(obj);
    }
    return -1;
  }

  /**
   * 对象中是否包含元素
   *
   * @param obj对象
   * @param element元素
   * 
   * @return 是否包含
   */
  public static boolean contains(Object obj, Object element) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof String) {
      if (element == null) {
        return false;
      }
      return ((String) obj).contains(element.toString());
    }
    if (obj instanceof Collection) {
      return ((Collection<?>) obj).contains(element);
    }
    if (obj instanceof Map) {
      return ((Map<?, ?>) obj).values().contains(element);
    }

    if (obj instanceof Iterator) {
      Iterator<?> iter = (Iterator<?>) obj;
      while (iter.hasNext()) {
        Object o = iter.next();
        if (equals(o, element)) {
          return true;
        }
      }
      return false;
    }
    if (obj instanceof Enumeration) {
      Enumeration<?> enumeration = (Enumeration<?>) obj;
      while (enumeration.hasMoreElements()) {
        Object o = enumeration.nextElement();
        if (equals(o, element)) {
          return true;
        }
      }
      return false;
    }
    if (obj.getClass().isArray() == true) {
      int len = Array.getLength(obj);
      for (int i = 0; i < len; i++) {
        Object o = Array.get(obj, i);
        if (equals(o, element)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 对象是否不为空(新增)
   *
   * @param obj String,List,Map,Object[],int[],long[]
   * @return
   */
  public static boolean notEmpty(Object o) {
    return !isEmpty(o);
  }

  /**
   * 对象是否为空
   *
   * @param obj String,List,Map,Object[],int[],long[]
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static boolean isEmpty(Object o) {
    if (o == null) {
      return true;
    }
    if (o instanceof String) {
      if (o.toString().trim().equals("")) {
        return true;
      }
    } else if (o instanceof List) {
      if (((List) o).size() == 0) {
        return true;
      }
    } else if (o instanceof Map) {
      if (((Map) o).size() == 0) {
        return true;
      }
    } else if (o instanceof Set) {
      if (((Set) o).size() == 0) {
        return true;
      }
    } else if (o instanceof Object[]) {
      if (((Object[]) o).length == 0) {
        return true;
      }
    } else if (o instanceof int[]) {
      if (((int[]) o).length == 0) {
        return true;
      }
    } else if (o instanceof long[]) {
      if (((long[]) o).length == 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * 对象组中是否存在 Empty Object
   *
   * @param os
   *               对象组
   * @return
   */
  public static boolean isOneEmpty(Object... os) {
    for (Object o : os) {
      if (isEmpty(o)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 对象组中是否全是 Empty Object
   *
   * @param os
   * @return
   */
  public static boolean isAllEmpty(Object... os) {
    for (Object o : os) {
      if (!isEmpty(o)) {
        return false;
      }
    }
    return true;
  }


  /**
   * 是否为数字
   *
   * @param obj
   * @return
   */
  public static boolean isNum(Object obj) {
    try {
      Integer.parseInt(obj.toString());
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * 如果为空, 则调用默认值
   *
   * @param str
   * @return
   */
  public static Object getValue(Object str, Object defaultValue) {
    if (isEmpty(str)) {
      return defaultValue;
    }
    return str;
  }

  /**
   * 强转->string,并去掉多余空格
   *
   * @param str
   * @return
   */
  public static String toStr(Object str) {
    return toStr(str, "");
  }

  /**
   * 强转->string,并去掉多余空格
   *
   * @param str
   * @param defaultValue
   * @return
   */
  public static String toStr(Object str, String defaultValue) {
    if (null == str) {
      return defaultValue;
    }
    return str.toString().trim();
  }

  /**
   * 字符串为空或null、NULL 缺省null
   *
   * @param str
   * @param defaultValue
   * @return
   */

  public static String valueNull(String str) {
    if (str != null && !"".equals(str.trim()) && !"null".equals(str.toLowerCase())) {
      return str;
    } else {
      return null;
    }
  }

  /**
   * 获取map中第一个数据值
   *
   * @param <K>Key的类型
   * @param <V>Value的类型
   * @param map数据源
   * @return 返回的值
   */
  public static <K, V> V getFirstOrNull(Map<K, V> map) {
    V obj = null;
    for (Entry<K, V> entry : map.entrySet()) {
      obj = entry.getValue();
      if (obj != null) {
        break;
      }
    }
    return obj;
  }

  /**
   * 创建StringBuilder对象
   *
   * @return StringBuilder对象
   */
  public static StringBuilder builder(String... strs) {
    final StringBuilder sb = new StringBuilder();
    for (String str : strs) {
      sb.append(str);
    }
    return sb;
  }

  /**
   * 创建StringBuilder对象
   *
   * @return StringBuilder对象
   */
  public static void builder(StringBuilder sb, String... strs) {
    for (String str : strs) {
      sb.append(str);
    }
  }

  /**
   * 去掉指定后缀
   *
   * @param str字符串
   * @param suffix后缀
   * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
   */
  public static String removeSuffix(String str, String suffix) {
    if (isEmpty(str) || isEmpty(suffix)) {
      return str;
    }

    if (str.endsWith(suffix)) {
      return str.substring(0, str.length() - suffix.length());
    }
    return str;
  }

  /**
   * 判断是否是windows操作系统
   *
   * @author stylefeng
   * @Date 2017/5/24 22:34
   */
  public static Boolean isWinOs() {
    String os = System.getProperty("os.name");
    if (os.toLowerCase().startsWith("win")) {
      return true;
    } else {
      return false;
    }
  }

  // 字符串 +1 (禁止非数字字符)
  public static String strPlus(String num) {
    if (num != null) {
      num = num.trim();
      char[] chars = num.toCharArray();
      for (int k = chars.length - 1; k >= 0; k--) {
        if (Character.isDigit(chars[k])) {
          if ('9' == chars[k]) {
            chars[k] = '0';// +1导致进位 则本位置0继续循环
            if (k == 0) { // 若到最后一位没法进位 则前面+1
              return "1" + String.valueOf(chars);
            }
          } else {
            chars[k] = (char) (((int) chars[k]) + 1);// +1不进位则循环结束
            return String.valueOf(chars);
          }
        } else {
          break;
        }
      }
    }
    return "";
  }

  /**
   * 从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip
   * 
   * @param request
   * @return ip
   */
  public static String getLocalIp(HttpServletRequest request) {
    String remoteAddr = request.getRemoteAddr();
    String forwarded = request.getHeader("X-Forwarded-For");
    String realIp = request.getHeader("X-Real-IP");

    String ip = null;
    if (realIp == null) {
      if (forwarded == null) {
        ip = remoteAddr;
      } else {
        ip = remoteAddr + "/" + forwarded.split(",")[0];
      }
    } else {
      if (realIp.equals(forwarded)) {
        ip = realIp;
      } else {
        if (forwarded != null) {
          forwarded = forwarded.split(",")[0];
        }
        ip = realIp + "/" + forwarded;
      }
    }
    return ip;
  }

  public static String getIpAddr(HttpServletRequest request) {
    String ip = null;
    try {
      ip = request.getHeader("x-forwarded-for");
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
      }
      if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_CLIENT_IP");
      }
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
      }
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

//        //使用代理，则获取第一个IP地址
//        if(StringUtils.isEmpty(ip) && ip.length() > 15) {
//			if(ip.indexOf(",") > 0) {
//				ip = ip.substring(0, ip.indexOf(","));
//			}
//		}

    return ip;
  }

  /**
   * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
   * 例如：HELLO_WORLD->HelloWorld
   * 
   * @param name转换前的下划线大写方式命名的字符串
   * @return 转换后的驼峰式命名的字符串
   */
  public static String camelName(String name) {
    StringBuilder result = new StringBuilder();
    // 快速检查
    if (name == null || name.isEmpty()) {
      // 没必要转换
      return "";
    } else if (!name.contains("_")) {
      // 字母小写
      return name.toLowerCase();
    }
    // 用下划线将原始字符串分割
    String camels[] = name.split("_");
    for (String camel : camels) {
      // 跳过原始字符串中开头、结尾的下换线或双重下划线
      if (camel.isEmpty()) {
        continue;
      }
      // 处理真正的驼峰片段
      if (result.length() == 0) {
        // 第一个驼峰片段，全部字母都小写
        result.append(camel.toLowerCase());
      } else {
        // 其他的驼峰片段，首字母大写
        result.append(camel.substring(0, 1).toUpperCase());
        result.append(camel.substring(1).toLowerCase());
      }
    }
    return result.toString();
  }

  /**
   * Object转Double
   * 
   * @param obj
   * @return
   */
  public static Double toDouble(Object obj) {
    if (notEmpty(obj)) {
      BigDecimal amt = new BigDecimal(toStr(obj));
      return amt.doubleValue();
    } else {
      return null;
    }

  }

  /**
   * 金额转换：分转元（小数点后保留2位有效数字）
   * 
   * @param obj
   * @return
   */
  public static String fenToYuan(Object obj) {
    Double money = toDouble(obj);
    if (notEmpty(money)) {
      return new DecimalFormat("0.00").format(money / 100);
    } else {
      return "0.00";
    }
  }

  /**
   * 金额转换：元转分（小数点后保留0位有效数字）
   * 
   * @param obj
   * @return
   */
  public static String yuanToFen(Object obj) {
    Double money = toDouble(obj);
    if (notEmpty(money)) {
      return new DecimalFormat("0").format(money * 100);
    } else {
      return "0";
    }
  }

  public static Object convert(Class<?> type, Object obj) {
    JSONObject inJson = (JSONObject) JSON.toJSON(obj);
    return mapToObj(type, inJson);
  }

  /**
   * JSONObject转对象
   * 
   * @param Class<?>
   * @param JSONObject
   * 
   * @return Object
   */
  public static Object mapToObj(Class<?> type, JSONObject inJson) {
    try {
      Object obj = type.newInstance(); // 创建 JavaBean 对象
      return mapToObj(obj, inJson);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * JSONObject转对象
   * 
   * @param Object
   * @param JSONObject
   * 
   * @return Object
   */
  public static Object mapToObj(Object obj, JSONObject inJson) {
    try {
      if (obj != null) {
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        // 给 JavaBean 对象的属性赋值
        for (int i = 0; i < propertyDescriptors.length; i++) {
          PropertyDescriptor descriptor = propertyDescriptors[i];
          if (inJson.containsKey(descriptor.getName())) {
            try {
              Object value = inJson.get(descriptor.getName());
              Object[] args = new Object[1];
              args[0] = value;
              descriptor.getWriteMethod().invoke(obj, args);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return obj;
  }

  public static void main(String[] args) {
    System.out.println(camelName("INSHOPFACE"));
  }
}
