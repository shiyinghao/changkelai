package com.icss.newretail.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class StringUtils {

	/**
	 * 生成唯一编号：时间戳+五位随机数
	 * @return
	 */
	public static synchronized String getUniqueNo() {
		String str = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		Random random = new Random();
		return str + random.nextInt(100000);
	}
	
	@SuppressWarnings("unchecked")
	public static String listToString(List list) {
		if (list == null || list.size() == 0) {
			return "";
		}
		StringBuffer stringBuffer = new StringBuffer();
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			String area = list.get(i).toString();
			stringBuffer.append(area).append(",");
		}
		str = stringBuffer.toString();
		if (str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	public static String formatNumber(double d) {
		DecimalFormat df = new DecimalFormat("0.######");
		return df.format(d);
	}

	public static String formatNumber(double d, int scale) {
		String str = "0";
		if (scale > 0) {
			str += ".";
		}
		for (int i = 0; i < scale; i++) {
			str += "#";
		}
		DecimalFormat df = new DecimalFormat(str);
		return df.format(d);
	}
	
	public static boolean isEmpty(String str){
		if(str == null){
			return true;
		}
		if("".equals(str)){
			return true ; 
		}
		return false;
	}
	
	public static String formatNumber(String dStr) {
		if (isEmpty(dStr)) {
			return "0";
		}
		Double d = Double.parseDouble(dStr);
		return formatNumber(d);
	}
	
	public static String formatNumber(String dStr, int scale) {
		if (isEmpty(dStr)) {
			return "0";
		}
		Double d = Double.parseDouble(dStr);
		return formatNumber(d, scale);
	}
	
}
