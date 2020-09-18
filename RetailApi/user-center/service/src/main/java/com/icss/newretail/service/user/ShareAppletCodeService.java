package com.icss.newretail.service.user;

import java.awt.Image;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.icss.newretail.model.*;

/**
 * @author jc
 * @date 2020/3/23 18:07
 */
public interface ShareAppletCodeService {

	/**
	 * 小程序分享
	 * @param uuid
	 * @return
	 */
	Map<String,Object> appletCode(HttpServletRequest request,ShareAppletRequest shareApplet);
	 
}
