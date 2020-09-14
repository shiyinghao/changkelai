package com.icss.newretail.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;



public class HttpClientUtil {
	//static Logger logger = LogManager.getLogger(HttpClientUtil.class);
	private static String DEFAULT_CHARSET = "UTF-8";
	private static int DEFAULT_CONNECTION_TIMEOUT = 10 * 1000;
	private static int DEFAULT_SO_TIMEOUT = 30 * 1000;
	public static String addUrl(String head, String tail) {
		if (head.endsWith("/")) {
			if (tail.startsWith("/")) {
				return head.substring(0, head.length() - 1) + tail;
			} else {
				return head + tail;
			}
		} else {
			if (tail.startsWith("/")) {
				return head + tail;
			} else {
				return head + "/" + tail;
			}
		}
	}

	/**
	 * post请求数据  格式是json
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public synchronized static String postData(String url, JSONObject json, String charset) throws Exception {
		//构造HttpClient的实例 
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(DEFAULT_SO_TIMEOUT );  
		//创建post方法的实例 
		PostMethod method = new PostMethod(url);
		charset=StringUtils.isEmpty(charset)? DEFAULT_CHARSET:charset;
		
		((PostMethod) method).addParameter("json", json.toString());  
		HttpMethodParams httpMethodParam = method.getParams();  
		httpMethodParam.setContentCharset("UTF-8");  
		httpMethodParam.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
		String result = "";
		try {
			httpClient.executeMethod(method);// 执行postMethod 
			result=new String(method.getResponseBody(),charset);
		} catch (Exception e) {
			throw e;
		}finally{
			//释放连接 
			method.releaseConnection();
		}
		return result;
	}
	
	/**
	 * post  请求  但是加header  头信息
	 * @param url
	 * @param header
	 * @param json
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public synchronized static String postData(String url, Map<String,String> header,JSONObject json, String charset) throws Exception {
		//构造HttpClient的实例 
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(DEFAULT_SO_TIMEOUT );  
		//创建post方法的实例 
		PostMethod method = new PostMethod(url);
		charset=StringUtils.isEmpty(charset)? "utf-8":charset;
		
//		((PostMethod) method).addParameter("cdata", json.toString());  
		HttpMethodParams httpMethodParam = method.getParams();  
	//	method.setRequestBody(new ByteArrayInputStream(json.toString().getBytes("UTF-8")));
		//参数中文无法请求的问题
		RequestEntity requestEntity = new ByteArrayRequestEntity(json.toString().getBytes("UTF-8"));
		method.setRequestEntity(requestEntity);
		
		httpMethodParam.setContentCharset("UTF-8");  
		httpMethodParam.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
		// 添加请求头
		Iterator<String> iter = header.keySet().iterator();
		String key = null;
		while(iter.hasNext()) {
			key = iter.next();
			method.addRequestHeader(key,header.get(key));
		}
		String result = "";
		try {
			httpClient.executeMethod(method);// 执行postMethod 
			result=new String(method.getResponseBody(),charset);
		} catch (Exception e) {
			throw e;
		}finally{
			//释放连接 
			method.releaseConnection();
		}
		return result;
	}
	
	/**
	 *  get请求数据,参数直接拼接在url后面
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public synchronized static String getData(String url, Map<String, String> params, String charset) throws Exception {
		final  HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(DEFAULT_SO_TIMEOUT);
		final GetMethod method = new GetMethod(url);
		// 添加头信息
//		method.addRequestHeader("token", params.get("token"));
		String result = "";
		try {
			httpClient.executeMethod(method);
			charset=StringUtils.isEmpty(charset)? DEFAULT_CHARSET:charset;
			result=new String(method.getResponseBody(),charset);
		} catch (Exception e) {
			throw e;
		}finally{
			method.releaseConnection();
		}
		return result;
		}
	
	
	/**
	 * post请求，但是参数 是param形式
	 * @param url
	 * @param parameters
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public synchronized static String postDataByKey(String url,String token ,List<NameValuePair> parameters, String charset) throws Exception {
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		String result = "";
		// 设置2个post参数，一个是scope、一个是q
		/*
		 * List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
		 * parameters.add(new BasicNameValuePair("authId", authId));
		 * parameters.add(new BasicNameValuePair("password", password));
		 * parameters.add(new BasicNameValuePair("authType", "1"));
		 * parameters.add(new BasicNameValuePair("tenantId", "01"));
		 */
		// 构造一个form表单式的实体
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
		// 将请求实体设置到httpPost对象中
		httpPost.setEntity(formEntity);
		httpPost.addHeader("token",token);
		httpPost.addHeader("User-Agent","");
		/*httpPost.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");*/
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpclient.execute(httpPost);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(),"UTF-8");
				//System.out.println(result);
			}
		} finally {
			if (response != null) {
				response.close();
			}
			httpclient.close();
		}

		return result;
	}
	
	
	
	
}
