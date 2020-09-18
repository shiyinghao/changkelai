package com.icss.newretail.api.user;

import java.awt.Image;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ShareAppletRequest;
import com.icss.newretail.service.user.ReviewService;
import com.icss.newretail.service.user.ShareAppletCodeService;

/**
* @author 作者：	叶稻田
* @version 创建时间：2020年4月2日 下午2:52:08
* @ClassName 类名称
* @Description 类描述
*/
@RestSchema(schemaId = "share")
@RequestMapping(path = "/v1/share")
public class ShareAppletCodeApi {
	
	@Autowired
	private ShareAppletCodeService shareAppletCodeService;
	/**
	 * 小程序分享码
	 *
	 * @param authCode
	 * @return
	 */
	@PostMapping("appletCode")
	public Map<String,Object> appletCode(HttpServletRequest request,@RequestBody ShareAppletRequest shareApplet) {
		return shareAppletCodeService.appletCode(request,shareApplet);
	}
}
