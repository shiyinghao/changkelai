package com.icss.newretail.service.file;

import com.icss.newretail.model.ShareAppletRequest;

import java.util.Map;

/**
 * @author jc
 * @date 2020/3/23 18:07
 */
public interface ShareAppletCodeService {

	/**
	 * 小程序分享
	 *
	 * @param shareApplet
	 * @return
	 */
	Map<String, Object> appletCode(ShareAppletRequest shareApplet);

	/**
	 * 生成二维码图片
	 *
	 * @param parm
	 * @return
	 */
	Map<String, Object> matrixToImageWriter(String parm);

	/**
	 *
	 */
	Map<String, Object> download(Integer tempType, Integer type);


//	ResponseBase insertAll(ShareAppletRequest shareApplet);

}
