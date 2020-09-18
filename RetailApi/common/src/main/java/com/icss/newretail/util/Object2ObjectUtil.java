package com.icss.newretail.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 对象转化工具类
 * 
 * @author shenjian
 * @date May 17, 2019
 */
public class Object2ObjectUtil {

  /**
   * 对象转化（自动构造新对象，并复制相同属性的数据）
   * 
   * @param o
   * @param targetC
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> T parseObject(Object o, Class<T> targetC) {
    Object targetO = null;
    if (o == null) {
      return (T) targetO;
    }
    try {
      // 1.构造新对象
      targetO = targetC.newInstance();
      Field[] targetFields = targetC.getDeclaredFields();
      // 2.向新对象写数据（循环读取老对象中数据写入新对象）
      for (Field f : o.getClass().getDeclaredFields()) { // 循环处理类属性
        if (!Modifier.isStatic(f.getModifiers())) {// 非静态变量才做处理
          try {
            // 2.1.读数据
            Method rm = getMethod(f.getName(), o.getClass());// 获得读方法
            Object val = rm.invoke(o);// 调用读方法
            // 2.2.写数据
            for (Field targetF : targetFields) {
              if (f.getName().equals(targetF.getName())) {// 属性名相同
                try {
                  Method wm = setMethod(targetF.getName(), targetC);// 获得写方法
                  wm.invoke(targetO, val);
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (T) targetO;
  }

  /**
   * 集合对象转化（数据复制）
   * 
   * @param oList
   * @param targetC
   * @return
   */
  public static <T> List<T> parseList(List<? extends Object> oList, Class<T> targetC) {
    List<T> targetList = new ArrayList<T>();
    if (oList == null) {
      return targetList;
    }
    for (int i = 0; i < oList.size(); i++) {
      T targetO = parseObject(oList.get(i), targetC);
      targetList.add(targetO);
    }
    return targetList;
  }

  // 获取类属性的get方法
  private static Method getMethod(String methodName, Class<?> c) {
    Method[] methods = c.getDeclaredMethods();
    String getString = "get" + firstBig(methodName);
    Method methodYes = null;
    for (Method method : methods) {
      if (getString.equals(method.getName())) {
        methodYes = method;
      }
    }
    return methodYes;
  }

  // 获取类属性的set方法
  private static Method setMethod(String methodName, Class<?> c) {
    Method[] methods = c.getDeclaredMethods();
    String getString = "set" + firstBig(methodName);
    Method methodYes = null;
    for (Method method : methods) {
      if (getString.equals(method.getName())) {
        methodYes = method;
      }
    }
    return methodYes;
  }

  // 首字母变大写
  private static String firstBig(String param) {
    if (param == null || "".equals(param.trim())) {
      return "";
    } else {
      param = param.substring(0, 1).toUpperCase() + param.substring(1);
    }
    return param;
  }
}
