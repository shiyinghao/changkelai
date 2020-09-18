package com.icss.newretail.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class DeliverQueryUtil {
	/**
	 * 实时查询请求地址
	 */
	private static final String SYNQUERY_URL = "http://poll.kuaidi100.com/poll/query.do";
	
	private static final String KEY = "fzoCtXnD8375";			//授权key
	private static final String CUSTOMER = "56D5B6E651C0FA330FA8B8FAB4E28A50";	//实时查询公司编号
	
	/**
	 * 实时查询快递单号
	 * @param com			快递公司编码
	 * @param num			快递单号
	 * @param phone			手机号
	 * @param from			出发地城市
	 * @param to			目的地城市
	 * @param resultv2		开通区域解析功能：0-关闭；1-开通
	 * @return
	 */
	public static String queryDeliver(String com, String num, String phone, String from, String to, int resultv2) {

		StringBuilder param = new StringBuilder("{");
		param.append("\"com\":\"").append(com).append("\"");
		param.append(",\"num\":\"").append(num).append("\"");
		param.append(",\"phone\":\"").append(phone).append("\"");
//		param.append(",\"from\":\"").append(from).append("\"");
//		param.append(",\"to\":\"").append(to).append("\"");
		if(1 == resultv2) {
			param.append(",\"resultv2\":1");
		} else {
			param.append(",\"resultv2\":0");
		}
		param.append("}");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("customer", CUSTOMER);
		String sign = MD5Util.encode(param + KEY + CUSTOMER);
		params.put("sign", sign);
		params.put("param", param.toString());
		
		return post(params);
	}
	
	/**
	 * 发送post请求
	 */
	public static String post(Map<String, String> params) {
		StringBuffer response = new StringBuffer("");
		
		BufferedReader reader = null;
		try {
			StringBuilder builder = new StringBuilder();
			for (Map.Entry<String, String> param : params.entrySet()) {
				if (builder.length() > 0) {
					builder.append('&');
				}
				builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				builder.append('=');
				builder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] bytes = builder.toString().getBytes("UTF-8");

			URL url = new URL(SYNQUERY_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(bytes);

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			
			String line = "";
            while ((line = reader.readLine()) != null) {
            	response.append(line);
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return response.toString();
	}
	
	public static void main(String[] args) {
		String result = queryDeliver("zhongtong", "77120702536843", "", null, null, 1);
		System.out.println(result);
	}
	
}
