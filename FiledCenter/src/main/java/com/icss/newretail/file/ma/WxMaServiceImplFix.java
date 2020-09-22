package com.icss.newretail.file.ma;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * <pre>
 * 默认接口实现类，使用apache httpclient实现
 * Created by Binary Wang on 2017-5-27.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxMaServiceImplFix extends WxMaServiceImpl {
	@Override
	public String getAccessToken(boolean forceRefresh) throws WxErrorException {
		if (!this.getWxMaConfig().isAccessTokenExpired() && !forceRefresh) {
			return this.getWxMaConfig().getAccessToken();
		}

		Lock lock = this.getWxMaConfig().getAccessTokenLock();
		lock.lock();
		try {
			if (!this.getWxMaConfig().isAccessTokenExpired() && !forceRefresh) {
				return this.getWxMaConfig().getAccessToken();
			}
			String url = String.format(WxMaService.GET_ACCESS_TOKEN_URL,
					this.getWxMaConfig().getAppid(),
					this.getWxMaConfig().getSecret());
			try {
				HttpGet httpGet = new HttpGet(url);
				if (this.getRequestHttpProxy() != null) {
					RequestConfig config = RequestConfig.custom()
							.setProxy(this.getRequestHttpProxy()).build();
					httpGet.setConfig(config);
				}
				try (CloseableHttpResponse response = getRequestHttpClient()
						.execute(httpGet)) {
					String resultContent = new BasicResponseHandler()
							.handleResponse(response);
					WxError error = WxError.fromJson(resultContent);
					if (error.getErrorCode() != 0) {
						throw new WxErrorException(error);
					}
					WxAccessToken accessToken = WxAccessToken
							.fromJson(resultContent);
					this.getWxMaConfig().updateAccessToken(
							accessToken.getAccessToken(),
							accessToken.getExpiresIn());

					return this.getWxMaConfig().getAccessToken();
				} finally {
					httpGet.releaseConnection();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} finally {
			lock.unlock();
		}
	}
}
